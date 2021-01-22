package com.spring.cloud.Enum;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * 活跃度
 */
@Getter
@AllArgsConstructor
public enum SmsEnum implements BaseEnum {
    ZERO("SMS_204215149", "身份验证验证码"),
    ONE("SMS_204215148", "登录确认验证码"),
    TWO("SMS_204215148", "登录异常验证码"),
    THREE("SMS_204215148", "用户注册验证码"),
    FOUR("SMS_204215148", "修改密码验证码"),
    FIVE("SMS_204215148", "信息变更验证码");

    @Setter
    private final String value;

    private final String message;




    @JsonValue
    @Override
    public int getOrdinal() {
        return this.ordinal();
    }
}
