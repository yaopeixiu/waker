package com.spring.cloud.exption;

import com.spring.cloud.Enum.StatusEnum;

/**
 * 令牌异常
 */
public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 585562537287074104L;

	public TokenException(String message) {
        super(message);
    }

    public TokenException(StatusEnum statusEnum) {
        super(statusEnum.getMessage());
    }
}
