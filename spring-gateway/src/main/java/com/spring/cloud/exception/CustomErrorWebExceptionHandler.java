package com.spring.cloud.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关自定义异常处理
 */
public class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(CustomErrorWebExceptionHandler.class);

    public CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        // 这里其实可以根据异常类型进行定制化逻辑
        HttpStatus httpStatus;
        String body;
        Throwable error = super.getError(request);
        if (error instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;//404
            logger.error("Service Not Found");
        }else if (error instanceof ResponseStatusException){
            ResponseStatusException responseStatusException = (ResponseStatusException) error;
            httpStatus = responseStatusException.getStatus();
            body = responseStatusException.getMessage();
            logger.error("Error:"+responseStatusException.getMessage());
        }else{
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;//500
            logger.error("Internal Server Error");
        }
        return response(httpStatus.value(), this.buildMessage(request, error),request);
    }

    public static Map<String, Object> response(int status, String errorMessage, ServerRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", status);
        map.put("msg", errorMessage);
        return map;
    }

    /**
     * 构建异常信息
     * @param request
     * @param ex
     * @return
     */
    private String buildMessage(ServerRequest request, Throwable ex) {
        StringBuilder message = new StringBuilder("Failed to handle request [");
        message.append(request.methodName());
        message.append(" ");
        message.append(request.uri());
        message.append("]");
        if (ex != null) {
            message.append(": ");
            message.append(ex.getMessage());
        }
        return message.toString();
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        // 这里其实可以根据errorAttributes里面的属性定制HTTP响应码
        int statusCode = (int) errorAttributes.get("code");
        return HttpStatus.valueOf(statusCode);
    }
}
