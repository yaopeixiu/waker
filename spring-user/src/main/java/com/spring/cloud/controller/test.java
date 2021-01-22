package com.spring.cloud.controller;

import com.spring.cloud.message.MqMessageConsumer;
import com.spring.cloud.model.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@Slf4j
@RestController
@RequestMapping("test")
public class test {


    @Autowired
    private MqMessageConsumer mqMessageConsumer;


    @Autowired
    private JedisCluster jedisCluster;


    @RequestMapping("ip")
    public ApiResult test() {
        return ApiResult.ok("请求成功");
    }

}
