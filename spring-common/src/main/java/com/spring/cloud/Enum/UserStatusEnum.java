package com.spring.cloud.Enum;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * 用户状态
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum implements BaseEnum {

    ABNORMAL("异常"),

    NORMAL("正常");

    @Setter
    private final String value;

    @JsonValue
    @Override
    public int getOrdinal() {
        return this.ordinal();
    }
}
