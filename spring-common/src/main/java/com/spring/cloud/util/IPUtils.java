package com.spring.cloud.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {
    //获取当前请求ip
    public static String getIP(HttpServletRequest request){
        String ip = request.getRemoteAddr();
        System.out.println("ip:"+ip);
        String headerIP = request.getHeader("x-real-ip");
        if(headerIP == null || "".equals(headerIP) || "null".equals(headerIP)){
            headerIP = request.getHeader("x-forwarded-for");
        }
        System.out.println("headerIP:"+headerIP);
        if(headerIP !=null && !"".equals(headerIP) && !"null".equals(headerIP)){
            ip = headerIP;
        }
        return ip;
    }
    public static String getIpFromRequest2(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
