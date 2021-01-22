
package com.spring.cloud.filter;

import com.alibaba.fastjson.JSONObject;
import com.spring.cloud.constant.RedisConstant;
import com.spring.cloud.exception.GatewayErrorType;
import com.spring.cloud.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import redis.clients.jedis.JedisCluster;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class IpMaxFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(IpMaxFilter.class);

    public static final String WARNING_MSG = "请求过于频繁，请稍后再试";

    @Autowired
    private JedisCluster jedisCluster;

    // 当日最大IP访问次数
    public static final long maxReq = 500;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String ip = getIpAddress(request);
        if (StringUtils.isNotBlank(ip)) {
            // 是否在黑名单
            if (jedisCluster.hexists(RedisConstant.BLACKLIST, ip)) {
                return unauthorized(exchange);
            }
            //当日访问次数
            String today = DateUtil.formatYYYYMMdd(new Date());
            Long count = jedisCluster.hincrBy(today + RedisConstant.TOD_REQ_COUNT, ip, 1);
            if (count.compareTo(maxReq) >= 0) {
                log.info("IP:{} 当日访问次数超限制，加入黑名单 ", ip);
                jedisCluster.hset(RedisConstant.BLACKLIST, ip, "1");
                return unauthorized(exchange);
            }
            //单位时间访问次数
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
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



    /*
     * 网关拒绝，返回401
     *
     * @param
     */

    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        JSONObject errorJson = new JSONObject();
        errorJson.put("code", GatewayErrorType.PARAM_ERR_REQUEST.getValue());
        errorJson.put("msg", GatewayErrorType.PARAM_ERR_REQUEST.getReasonPhrase());
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(errorJson.toJSONString().getBytes(StandardCharsets.UTF_8));
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Flux.just(buffer));
    }
}

