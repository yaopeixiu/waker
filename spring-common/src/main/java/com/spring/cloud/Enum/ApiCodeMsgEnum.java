package com.spring.cloud.Enum;

/**
 * api消息枚举
 */
public enum ApiCodeMsgEnum {

    E200(200, "200"),
    E400(400, "参数错误"),
    E1000(1000, "下单失败"),
    E10001(10001, "服务器繁忙"),
    E10005(10005, "账户锁定"),
    E10007(10007, "资产异常, 请联系客服");


    private Integer code;
    private Object value;

    private ApiCodeMsgEnum(Integer code, Object value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
