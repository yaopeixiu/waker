package com.spring.cloud.filter;

import com.alibaba.fastjson.JSONObject;
import com.spring.cloud.exception.GatewayErrorType;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.github.bucket4j.Bucket;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IpLimitFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(IpLimitFilter.class);

    public static final String WARNING_MSG = "请求过于频繁，请稍后再试";

    public static final Map<String, Bucket> LOCAL_CACHE = new ConcurrentHashMap<>();

    // 令牌桶初始容量
    public static final long capacity = 10;

    // 补充桶的时间间隔，即10秒补充一次
    public static final long seconds = 10;

    // 每次补充token的个数
    public static final long refillTokens = 10;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String ip = getIpAddress(request);

        //log.info("访问IP为:{}", ip);

        Bucket bucket = LOCAL_CACHE.computeIfAbsent(ip, k -> createNewBucket(ip));

        //log.info("IP:{} ,令牌通可用的Token数量:{} ", ip, bucket.getAvailableTokens());

        if (bucket.tryConsume(1)) {
            // 直接放行
            return chain.filter(exchange);
        } else {
            return  unauthorized(exchange);
           /* byte[] bits = WARNING_MSG.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            //指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));*/
        }

    }

    @Override
    public int getOrder() {
        return 1;
    }


    private Bucket createNewBucket(String ip) {
        Duration refillDuration = Duration.ofSeconds(seconds);
        Refill refill = Refill.of(refillTokens, refillDuration);
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(limit).build();
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


    /**
     * 网关拒绝，返回401
     *
     * @param
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        JSONObject errorJson = new JSONObject();
        errorJson.put("code", GatewayErrorType.PARAM_ERR_SIGN.getValue());
        errorJson.put("msg", GatewayErrorType.PARAM_ERR_SIGN.getReasonPhrase());
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(errorJson.toJSONString().getBytes(StandardCharsets.UTF_8));
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Flux.just(buffer));
    }
}
