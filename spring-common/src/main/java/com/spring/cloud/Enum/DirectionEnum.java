package com.spring.cloud.Enum;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * 方向
 */
@Getter
@AllArgsConstructor
public enum DirectionEnum implements BaseEnum {

    ADD("增加"),

    SUB("减少");

    @Setter
    private final String value;

    @JsonValue
    @Override
    public int getOrdinal() {
        return this.ordinal();
    }
}
