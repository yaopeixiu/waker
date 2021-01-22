package com.spring.cloud.config;

import com.spring.cloud.util.RedisDistributeLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

@Configuration
public class RedisDistributeLockConfig {


    @Bean
    public RedisDistributeLock redisDistributeLock(JedisCluster jedisCluster) {
        return new RedisDistributeLock(jedisCluster);
    }

}
