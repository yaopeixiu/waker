package com.spring.cloud.util.token;


import com.alibaba.fastjson.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.UUID;

public class TokenUtils {

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    private static final char[] uphexCode = "0123456789ABCDEF".toCharArray();

    public static String generate() {
        return generateValue(UUID.randomUUID().toString());
    }


    public static String generateValue(String param) {
        try {
            MessageDigest algorithm;
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("生成token异常");
        }

    }

    private static String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        byte[] arrayOfByte = data;
        int j = data.length;
        for (int i = 0; i < j; i++) {
            byte b = arrayOfByte[i];
            r.append(hexCode[(b >> 4 & 0xF)]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    /**
     * 解密token
     *
     * @return token
     * @throws ParseException
     */
    public static Auth decodeToken(String token) {
        try {
            return JSONObject.toJavaObject(JSONObject.parseObject(AccessTokenAuthManager.parseToken(token)), Auth.class);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 生成加密信息
     *
     * @param auth
     * @return
     * @throws Exception
     */
    public static String encodeToken(Auth auth) {
        return AccessTokenAuthManager.generateToken(auth);
    }
}
