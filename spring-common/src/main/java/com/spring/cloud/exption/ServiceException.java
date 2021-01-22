package com.spring.cloud.exption;

/**
 * 服务运行异常
 *
 * @author sunsx
 * @since 1.0.0
 */
public class ServiceException extends IllegalArgumentException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
        super(message);
    }
}
