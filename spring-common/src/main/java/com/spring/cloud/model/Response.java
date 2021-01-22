package com.spring.cloud.model;

import com.spring.cloud.Enum.StatusEnum;
import lombok.Data;

/**
 *@ClassName Response
 *@Description TODO
 *@Author sunshaoxian
 *@Date 2019/11/22
 *@Version 1.0
 */
@Data
public class Response {

	private boolean success = false;
	private Integer code;
	private String msg;
	private Object data;

	private Response(Throwable e) {
		this.success = false;
		this.msg = e.toString();
		this.code = StatusEnum.FAIL.value();
	}

	private Response(StatusEnum statusEnum, boolean success, Object data) {
		this.success = success;
		this.code = statusEnum.value();
		this.msg = statusEnum.getMessage();
		this.data = data;
	}

	private Response(Object data, String msg, Integer code) {
		this.success = true;
		this.msg = msg;
		this.code = code;
		this.data = data;
	}

	private Response(String errorMsg) {
		this.success = false;
		this.msg = errorMsg;
		this.code = StatusEnum.FAIL.value();
	}

	public static Response success(Object data,String msg,Integer code) {
		return new Response(data,msg,code);
	}
	public static Response success(String msg,Integer code) {
		return new Response(null,msg,code);
	}
	public static Response success() {
		return new Response(StatusEnum.SUCCESS,true,null);
	}

	public  static Response success(Object data) {
		return new Response(StatusEnum.SUCCESS,true,data);
	}


	public static Response fail(String errorMsg) {
		return new Response(errorMsg);
	}

	public static Response fail(StatusEnum statusEnum) {
		return new Response(statusEnum,false,null);
	}

	public static Response fail(Throwable exception) {
		return new Response(exception);
	}

}
