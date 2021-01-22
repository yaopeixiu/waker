package com.spring.cloud.fallback;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GatewayFallbackController {


    @RequestMapping(value = "/fallback")
    public Map<String, String> defaultfallback() {
        Map<String, String> map = new HashMap<>();
        map.put("status", "500");
        map.put("msg", "服务繁忙,请稍后再试！");
        return map;
    }

}
