package com.spring.cloud.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private MD5Util() {
    	
    }



    static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

   
    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    
    public static byte[] md5(String data) {
    	try {
			return md5(data.getBytes("UTF-8")); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
       return md5(data.getBytes());
    }

   
    public static String md5Hex(byte[] data) {
        return HexUtil.toHexString(md5(data));
    }

    
    public static String md5Hex(String data) {
        return HexUtil.toHexString(md5(data));
    }

    public static void main(String[] args){
        long time = System.currentTimeMillis();
        System.out.println(time);
        System.out.println(MD5Util.md5Hex(time+""));
    }
}
