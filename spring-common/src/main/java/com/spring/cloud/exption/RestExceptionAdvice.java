package com.spring.cloud.exption;


import com.spring.cloud.Enum.StatusEnum;
import com.spring.cloud.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


/**
 * 用于处理RestController所有的异常情况
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class RestExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(RestExceptionAdvice.class);


    @ExceptionHandler(ServiceException.class)
    public Response serviceException(ServiceException e) {
        logger.error("运行时异常", e);
        return Response.fail(e.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);
        return Response.fail("缺少请求参数");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        return Response.fail("参数解析失败");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数验证失败", e.getMessage());
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return Response.fail(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Response handleBindException(BindException e) {
        logger.error("参数绑定失败", e.getMessage());
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return Response.fail(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public Response handleBadRequestException(BadRequestException e) {
        return Response.fail(e.getMessage());
    }


    /**
     * 401 - Unauthorized
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TokenException.class)
    public Response handleTokenException(TokenException e) {
        return Response.fail(StatusEnum.TOKEN_ERROE);
    }

    /**
     * 403 - Forbidden
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public Response forbiddenException(ForbiddenException e) {
        return Response.fail(e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return Response.fail("不支持当前请求方法");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        return Response.fail("不支持当前媒体类型");
    }

    /**
     * 500 - Internal Server Error
     */
   /* @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServiceException.class)
    public Response handleServiceException(ServiceException e) {
        return Response.fail(e.getMessage());
    }*/

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public Response sqlException(SQLException e) {
        logger.error("SQL Exception: ", e);
        return Response.fail("数据库操作异常");
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        logger.error("通用异常", e);
        String message = String.format("exception:%s", e.getMessage());
        return Response.fail(message);
    }

}
