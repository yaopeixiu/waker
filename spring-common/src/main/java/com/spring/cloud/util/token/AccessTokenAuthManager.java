package com.spring.cloud.util.token;


import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.text.ParseException;
import java.util.Date;

public class AccessTokenAuthManager {

    static {
        key = generateKey("d1465f91-88p1-42c1-9e58-432105fd7d2c");
    }

    /**
     * 加密key，jws使用时会复制一份，不会发生并发问题
     */
    private static Key key;


    /**
     * 生成token
     *
     * @param target 需要保存到token的对象信息，一般为User对象或map对象
     * @return token
     * @throws ParseException
     */
    public static String generateToken(Object target) {
        long validTime = 86400000L;
        return Jwts.builder()
                .setSubject(JSONObject.toJSONString(target))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                .signWith(SignatureAlgorithm.HS512, key).compact();
    }

    /**
     * 获取token上记录的信息
     *
     * @param token
     * @return token上记录的信息
     */
    public static String parseToken(String token) throws ExpiredJwtException {
        Claims claim = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claim.getSubject();
    }


    /**
     * 根据字符串key获取Key对象
     *
     * @param key
     * @return
     */
    public static Key generateKey(String key) {
        return new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }


    public static void main(String[] args) {
        Key signingKey = new SecretKeySpec("ke324234234y".getBytes(), SignatureAlgorithm.HS512.getJcaName());
        String token = Jwts.builder()
                .setSubject(JSONObject.toJSONString(new Auth("123123123123", 1,new Date())))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 123123123))
                .signWith(SignatureAlgorithm.HS512, signingKey).compact();
        System.out.println(token);
        Claims claim = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token + "2").getBody();
        Auth auth = claim.get("sub", Auth.class);
        System.out.println(claim.getSubject());
    }
}