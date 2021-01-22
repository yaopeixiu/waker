package com.spring.cloud.exption;

import com.spring.cloud.Enum.StatusEnum;

/**
 * 接口权限异常
 */
public class ForbiddenException extends RuntimeException {

	public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(StatusEnum statusEnum) {
        super(statusEnum.getMessage());
    }
}
