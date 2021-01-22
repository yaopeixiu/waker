package com.spring.cloud.filter;


import com.alibaba.fastjson.JSONObject;
import com.spring.cloud.constant.RedisConstant;
import com.spring.cloud.constant.SystemConstant;
import com.spring.cloud.exception.GatewayErrorType;
import com.spring.cloud.model.UserInfo;
import io.netty.buffer.ByteBufAllocator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import redis.clients.jedis.JedisCluster;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * token校验全局过滤器
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private static final String TOKEN = "token";

    /**
     * 过滤的请求路径
     */
    @Value("${auth.skip.urls}")
    private String[] skipAuthUrls;

    @Override
    public int getOrder() {
        return 2;
    }

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put("startTime", System.currentTimeMillis());
        ServerHttpRequest request = exchange.getRequest();
        //获取请求方式post、get
        String method = request.getMethodValue();
        //接口请求路径
        String url = request.getURI().getPath();
        //拿到token
        String token = request.getHeaders().getFirst(TOKEN);
        //跳过不需要验证的路径
        if (Arrays.asList(skipAuthUrls).contains(url)) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            //有些是可带可不带token的接口,判断是否带token转化成功用户id
            if (StringUtils.isNotBlank(token)) {
                String userId = jedisCluster.hget(RedisConstant.TOKEN_USER, token);
                String chaceToken = jedisCluster.hget(RedisConstant.USER_TOKEN, userId);
                if (!StringUtils.equals(chaceToken, token)) {
                    return unauthorized(exchange);
                }
                String srcUserInfo = jedisCluster.hget(RedisConstant.TOKEN_USER_JSON, token);
                UserInfo userInfo = JSONObject.parseObject(srcUserInfo, UserInfo.class);
                if (userInfo != null) {
                    String ipAddress = getIpAddress(request);
                    if (StringUtils.isNotBlank(ipAddress)) {
                        logger.info("ip:{},user:{},path:{},", ipAddress, userInfo.getId(), url);

                    }
                    return returnMono(chain, exchange, userInfo, url, method);
                }
            }
            return chain.filter(exchange);
        }
        //token为空
        if (StringUtils.isBlank(token)) {
            return unauthorized(exchange);
        }
        String userId = jedisCluster.hget(RedisConstant.TOKEN_USER, token);
        if (StringUtils.isBlank(userId)) {
            return unauthorized(exchange);
        }
        String chaceToken = jedisCluster.hget(RedisConstant.USER_TOKEN, userId);
        if (!StringUtils.equals(chaceToken, token)) {
            return unauthorized(exchange);
        }
        String srcUserInfo = jedisCluster.hget(RedisConstant.TOKEN_USER_JSON, token);
        UserInfo userInfo = JSONObject.parseObject(srcUserInfo, UserInfo.class);
        if (null == userInfo) {
            return unauthorized(exchange);
        }
        String ipAddress = getIpAddress(request);
        if (StringUtils.isNotBlank(ipAddress)) {
            logger.info("ip:{},user:{},path:{},", ipAddress, userInfo.getId(), url);

        }
        return returnMono(chain, exchange, userInfo, url, method);
    }

    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     *
     * @return 请求体
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        //获取request body
        return bodyRef.get();
    }

    /**
     * 获取请求数据，转换为String
     *
     * @param value
     * @return
     */
    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    /**
     * 网关拒绝，返回401
     *
     * @param
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        JSONObject errorJson = new JSONObject();
        errorJson.put("code", GatewayErrorType.PARAM_NOT_KEY.getValue());
        errorJson.put("msg", GatewayErrorType.PARAM_NOT_KEY.getReasonPhrase());
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(errorJson.toJSONString().getBytes(StandardCharsets.UTF_8));
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Flux.just(buffer));
    }


    private Mono<Void> returnMono(GatewayFilterChain chain, ServerWebExchange exchange, UserInfo userInfo, String url, String method) {
        //所有校验通过的请求，在请求头存放对象信息
        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set(SystemConstant.USERID, userInfo.getId().toString());
            httpHeader.set(SystemConstant.ACCOUNT, userInfo.getPhone());
            httpHeader.set(SystemConstant.JD_PID, userInfo.getJdPid());
            httpHeader.set(SystemConstant.PDD_PID, userInfo.getPddPid());
            httpHeader.set(SystemConstant.TAOBAO_PID, userInfo.getTaobaoPid());
            httpHeader.set(SystemConstant.SHARE_CODE, userInfo.getShareCode());
        };
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
        ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(build).then(Mono.fromRunnable(() -> {
             /*Long startTime = exchange.getAttribute("startTime");
           if (startTime != null) {
                long executeTime = (System.currentTimeMillis() - startTime);
                logger.info("url:{}, method:{} ,耗时：{}ms", url, method, executeTime);
            }*/
        }));
    }


    /**
     * String转map
     *
     * @param str
     * @return
     */
    public static Map<String, String> getStringToMap(String str) {
        //根据逗号截取字符串数组
        String[] str1 = str.split("&");
        //创建Map对象
        Map<String, String> map = new HashMap<String, String>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split("=");
            if (str2.length <= 1) {
                map.put(str2[0], "");
            } else {
                //str2[0]为KEY,str2[1]为值
                map.put(str2[0], str2[1]);
            }
        }
        return map;
    }

    public static String getIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }
}
