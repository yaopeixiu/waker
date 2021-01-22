package com.spring.cloud.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 返回数据
 * @author: Lyle
 * @create:2020-10-05 15:20
 **/
@Component
public class ApiResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private static ApplicationContext applicationContext;

    @Autowired
    ApplicationContext context;

    @Bean
    public String init() {
        ApiResult.applicationContext = context;
        return "";
    }

    public ApiResult() {
        put("status", 0);
        put("msg", "success");
    }

    public static ApiResult error() {
        return error(500, "操作失败");
    }

    public static ApiResult error(String msg) {
        return error(500, msg);
    }

    public static ApiResult error(BindingResult bindingResult) {
        return error(500, bindingResult.getAllErrors().get(0).getDefaultMessage());
    }


    public static ApiResult error(int code, String msg) {
        ApiResult r = new ApiResult();
        r.put("status", code);
        r.put("msg", msg);
        return r;
    }

    public static ApiResult ok(String msg) {
        ApiResult r = new ApiResult();
        r.put("msg", msg);
        return r;
    }

    public static ApiResult ok(Map<String, Object> map) {
        ApiResult r = new ApiResult();
        r.putAll(map);
        return r;
    }

    public static ApiResult ok() {
        return new ApiResult();
    }

    @Override
    public ApiResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
