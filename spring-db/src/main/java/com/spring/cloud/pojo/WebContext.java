package com.spring.cloud.pojo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于封装 Request 与 Response 对象
 */
public class WebContext {

    private static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();

    /**
     * 初始化
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        requestHolder.set(request);
        responseHolder.set(response);
    }

    /**
     * 销毁
     */
    public static void destroy() {
        requestHolder.remove();
        responseHolder.remove();
    }

    /**
     * 获取 Request 对象
     */
    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    /**
     * 获取 Response 对象
     */
    public static HttpServletResponse getResponse() {
        return responseHolder.get();
    }

    public static Integer getRequestClientId() {
        return (Integer) getRequest().getAttribute("clientId");
    }


    public static String getRequestToken() {
        return getRequest().getHeader("TOKEN");
    }

    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
