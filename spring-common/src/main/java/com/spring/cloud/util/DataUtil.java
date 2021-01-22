package com.spring.cloud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据处理工具
 * @auther Jeremy Zhang
 * 2019/4/11 下午4:54
 */
public class DataUtil {

    private static Logger logger = LoggerFactory.getLogger(DataUtil.class);
    /**
     * @return
     * @Description: 随机生成6位数密码
     * @author Jie.Zhao
     * @date 2017年2月20日 下午2:46:05
     */
    public static String randomPassword() {
        int min = 100000;
        int max = 999999;
        int randNum = min + (int) (Math.random() * ((max - min) + 1));
        return randNum + "";
    }
    public static String initShareCode(String regCode){
        String shareCode = generateShortUuid(6);
        if(shareCode.equals(regCode)){
            initShareCode(regCode);
        }
        return shareCode;
    }
    /**
     * 10位不重复随机数
     */
    public static String generateShortUuid(int num) {
        String[] chars = new String[] {
                /*
                 * "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                 * "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
                 * "y", "z", "0", "1",
                 */ "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", /* "I", */
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < num; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            // shortBuffer.append(chars[x % 0x3E]);0x3E=62
            shortBuffer.append(chars[x % 33]);
        }
        return shortBuffer.toString();
    }
    public static boolean isEmpty(Object... objs){
        if(objs == null)
            return true;
        for (Object obj : objs) {
            if(obj instanceof String)
                obj = ((String) obj).trim();

            if(ObjectUtils.isEmpty(obj))
                return true;
        }
        return false;
    }

    static final int DEFAULT_LENGTH = 3;

    public static String getSequence(long seq) {
        String str = String.valueOf(seq);
        int len = str.length();
        if (len >= DEFAULT_LENGTH) {// 取决于业务规模,应该不会到达3
            return str;
        }
        int rest = DEFAULT_LENGTH - len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rest; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }

//    /**
//     * HHTG+年月+8+0001
//     * 每月从0001开始计数
//     * */
//    public static synchronized String getnumber(String thisCode){
//
//        String id = null;
//        String thisData = thisCode.substring(4, 8);
//        //这个判断就是判断你数据取出来的最后一个业务单号是不是当月的
//        if(!formatter.format(date).equals(thisData)){
//            System.out.println("新的一月");
//            thisData = formatter.format(date);
//            //如果是新的一月的就直接变成0001
//            id = "HHTG" + thisData + "80001";
//        }else{
//            System.out.println("当月");
//            DecimalFormat df = new DecimalFormat("0000");
//
//            //不是新的一月就累加
//            id ="HHTG"+ formatter.format(date)+"8"
//                    + df.format(1 + Integer.parseInt(thisCode.substring(9, 13)));
//        }
//        return id;
//    }

    public static boolean isNotEmpty(Object... objs){
        return !isEmpty(objs);
    }

    public static Double[] arrayToDouble(String[] str){
        Double[] arr = new Double[str.length];
        for (int i = 0; i < str.length; i++) {
            arr[i] = Double.parseDouble(str[i]);
        }
        return arr;
    }
    public static String  formatFanCode(String code){
        if(StringUtils.isEmpty(code)){
            return null;
        }
        Double substring = Double.valueOf(code.substring(code.indexOf("-") + 1, code.lastIndexOf("-")));
         substring=substring*1000;
        code=code.substring(0,code.indexOf("-"))+"/"+substring.intValue()+code.substring(code.lastIndexOf("-"));
        return code;
    }
    public static int getSectionInArray(Double[] arr, Double value){
        int start = 0;
        int end = arr.length - 1;

        if(value <= arr[start] || value > arr[end]){
            while (end-start>1){
                int mid = (end+start)/2;
                if(value>arr[mid]){
                    start = mid;
                } else if (value<arr[mid]){
                    end = mid;
                } else {
                    start = mid - 1;
                    break;
                }
            }
            return start;
        } else {
            return -1;
        }
    }

    /**
     * 深复制对象 // Serializable
     * 2019/04/25 13:24:47
     * @author Jeremy Zhang
     */
    public static <T> T objectClone(T object) {
        if(!(object instanceof Serializable)){
            return null;
        }
        T outer = null;
        ByteArrayOutputStream aos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream ais = null;
        ObjectInputStream ois = null;
        try {
            aos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(aos);
            oos.writeObject(object);
            // 将流序列化成对象
            ais = new ByteArrayInputStream(aos.toByteArray());
            ois = new ObjectInputStream(ais);
            outer = (T) ois.readObject();
        } catch (Exception e) {
            logger.error("Clone Object Error!", e);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                logger.error("IO Close Error!", e);
            }
        }
        return outer;
    }

    /**
     * 反射方法
     * 2019/04/29 14:11:19
     * @author Jeremy Zhang
     */
    public static Method getMethod(Object target, String methodName, Class<?>... classes){
        Method method = null;
        try {
            method = target.getClass().getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        }
        return method;
    }

    /**
     * 组合入参表
     * 2019/05/05 14:24:56
     * @author Jeremy Zhang
     */
    public static Object[] getParam(Object... objects){
        return objects;
    }

    /**
     * 双精度浮点型数据保留两位小数
     * @param sourceData
     * @return Double
     */
    public static Double doubleDataFormat(Double sourceData){
        DecimalFormat df = new DecimalFormat("######0.00");
        Double destinationData = Double.valueOf(df.format(sourceData));
        return destinationData;
    }

    public static Double doubleDataFormat(Double sourceData, String format){
        DecimalFormat df = new DecimalFormat(format);
        Double destinationData = Double.valueOf(df.format(sourceData));
        return destinationData;
    }

    /**
     * Double 转 String 保留小数点位数并去0
     * 2019/08/01 15:13:03
     * @author Jeremy Zhang
     */
    public static String doubleToString(Double num, int w){
        // 处理位数格式
        StringBuilder builder = new StringBuilder(".");
        for(int i=0;i < w;i++){
            builder.append("0");
        }
        String format = builder.length() > 1 ? "#0"+builder.toString() : "#0";
        // Double 转 String
        DecimalFormat df = new DecimalFormat(format);
        return rvZeroDot(df.format(num));
    }

    /**
     * Double 转 String 保留2位小数
     * 2019/09/26 19:21:13
     * @author Jeremy Zhang
     */
    public static String twoDigit(Double num){
        return doubleToString(num,2);
    }

    /**
     * 小数字符串去0
     * 2019/08/01 15:15:28
     * @author Jeremy Zhang
     */
    public static String rvZeroDot(String s){
        if (s.isEmpty()) {
            return null;
        }
        // 判断小数
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 小数转百分比
     * 2019/09/26 19:10:08
     * @author Jeremy Zhang
     */
    public static String toPercent(Double rate) {
        return toPercentDigit(rate,2);
    }

    /**
     * 小数转百分比可设置位数
     * 2019/09/26 19:10:08
     * @author Jeremy Zhang
     */
    public static String toPercentDigit(Double rate, Integer digit) {
        NumberFormat num = NumberFormat.getPercentInstance();
        num.setMaximumFractionDigits(digit);
        return num.format(rate);
    }

    public static boolean isMoblie(String number){
        Pattern p = Pattern.compile("^[1]\\d{10}$");
        Matcher m = p.matcher(number);
        return m.matches();
    }
}
