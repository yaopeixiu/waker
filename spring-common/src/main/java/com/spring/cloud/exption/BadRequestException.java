package com.spring.cloud.exption;

import com.spring.cloud.Enum.StatusEnum;

/**
 * 错误请求异常
 *
 * @author sunsx
 * @since 1.0.0
 */
public class BadRequestException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(StatusEnum statusEnum) {
        super(statusEnum.getMessage());
    }
}
