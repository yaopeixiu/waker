package com.spring.cloud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

public class JsonUtil {

	private JsonUtil(){}

	public static <T> T parseObject(String json,Class<T> clazz){
		return JSON.parseObject(json, clazz);
	}
	public static <T> List<T> parseObjectList(String json, Class<T> clazz){
		return JSON.parseArray(json, clazz);
	}
	public static String toJSONString(Object object){
		return JSON.toJSONString(object,SerializerFeature.WriteMapNullValue);
	}
}
