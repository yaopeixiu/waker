package com.spring.cloud.exception;

/**
 * 异常信息
 */
public enum GatewayErrorType {

    SYSTEM_ERROR(102, "系统异常"),
    SYSTEM_BUSY(103, "系统繁忙,请稍候再试"),
    GATEWAY_NOT_FOUND_SERVICE(104, "服务未找到"),
    GATEWAY_ERROR(105, "网关异常"),
    GATEWAY_CONNECT_TIME_OUT(106, "网关超时"),
    PARAM_NOT_VALID(107, "请求参数有误"),
    PARAM_NOT_APPID(108, "APPID不能为空"),
    PARAM_NOT_ROKEN(109, "登录失效"),
    PARAM_NOT_KEY(110, "登录失效"),
    PARAM_ERR_SIGN(111, "签名有误"),
    PARAM_NOT_TIMESTAMP(112, "时间戳不能为空"),
    PARAM_ERR_REQUEST(113, "请求异常，请联系客服");

    public final Integer value;
    public final String reasonPhrase;

    public Integer getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    GatewayErrorType(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public static String getGatewayErrorTypeByCode(Integer code) {
        for (GatewayErrorType entrustType : GatewayErrorType.values()) {
            if (entrustType.getValue().equals(code)) {
                return entrustType.getReasonPhrase().toString();
            }
        }
        return null;
    }
}
