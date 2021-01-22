package com.spring.cloud.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.jsonwebtoken.*;

import javax.xml.bind.DatatypeConverter;
import java.util.*;

/**
 * @author pibigstar
 * @create 2018-12-13 15:32
 * @desc token工具类
 **/
public class JwtHelper {

    private static final String SECRET = "f65abe68821addefcb9724cac106cf32";//私密key
    private static final Long TTL_EXPIRATION = 1000L * 60; //过期时间30分钟

    /**
     * 加密信息，生成token
     */
    public static String addJwt(Map<String, Object> params) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Map<String, Object> map = new HashMap<String, Object>();
            Date nowDate = new Date();
            // 过期时间：2小时
            Date expireDate = getAfterDate(nowDate,0,0,10,0,0,0);
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            String token = JWT.create()
                    // 设置头部信息 Header
                    .withHeader(map)
                    .withClaim("session_id", params.get("session_id").toString())
                    .withClaim("user_id", params.get("user_id").toString())
                    .withClaim("scene", params.get("scene").toString())
                    .withClaim("attr", (Map) params.get("attr"))
                    // 生成签名的时间
                    .withIssuedAt(nowDate)
                    // 签名过期的时间
                    .withExpiresAt(expireDate)
                    // 签名 Signature
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            exception.printStackTrace();
        }
        return null;
    }

    public  static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second){
        if(date == null){
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if(year != 0){
            cal.add(Calendar.YEAR, year);
        }
        if(month != 0){
            cal.add(Calendar.MONTH, month);
        }
        if(day != 0){
            cal.add(Calendar.DATE, day);
        }
        if(hour != 0){
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if(minute != 0){
            cal.add(Calendar.MINUTE, minute);
        }
        if(second != 0){
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }
}

