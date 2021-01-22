package com.spring.cloud.util;

import java.math.BigDecimal;

/**
 * 
 * <B>功能简述</B><br>
 * BigDecimal计算帮助类
 * 
 * @date 2015年3月27日 下午3:26:31
 * @author DaiLu
 * @since [weiyuan/utils v1]
 */
public class AmountUtils {

	/**
	 * 提供默认精度(数字2表示保留小数点后两位)
	 */
	private static final int scale = 2;   
	/**金额为分的格式 */    
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";  

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 构造函数不可被实例化
	 *
	 * @date 2015年3月27日下午3:38:35
	 * @author DaiLu 参数说明
	 */
	private AmountUtils() {
	}

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 两个double类型变量相加
	 * 
	 * @date 2015年3月27日 下午3:28:17
	 * @author DaiLu
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal add(double v1, double v2) {
		// 按照JDK帮助文档说明,如果采用double类型的话有可能丢失精度,推荐采用String类型.
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2);
	}

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 两个double类型变量相减
	 * 
	 * @date 2015年3月27日 下午3:28:22
	 * @author DaiLu
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal subtract(double v1, double v2) {
		// 按照JDK帮助文档说明,如果采用double类型的话有可能丢失精度,推荐采用String类型.
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2);
	}

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 两个double类型变量相乘
	 * 
	 * @date 2015年3月27日 下午4:52:04
	 * @author DaiLu
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal multiply(double v1, double v2) {
		// 按照JDK帮助文档说明,如果采用double类型的话有可能丢失精度,推荐采用String类型.
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2);
	}

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 两个double类型变量相除
	 * 
	 * @date 2015年3月27日 下午4:18:00
	 * @author DaiLu
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal divide(double v1, double v2) {
		// 按照JDK帮助文档说明,如果采用double类型的话有可能丢失精度,推荐采用String类型.
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		// ROUND_HALF_UP表示遇到.5的情况时往上近似,例:1.5 --> 2
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 两个String类型变量进行相加
	 * 
	 * @date 2015年3月27日 下午3:28:27
	 * @author DaiLu
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2);
	}

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 两个String类型变量相减
	 * 
	 * @date 2015年3月27日 下午3:28:50
	 * @author DaiLu
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal subtract(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2);
	}

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 两个String类型变量相乘
	 * 
	 * @date 2015年3月27日 下午3:28:43
	 * @author DaiLu
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal multiply(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2);
	}

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 两个String类型变量相除
	 * 
	 * @date 2015年3月27日 下午3:28:33
	 * @author DaiLu
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal divide(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		// ROUND_HALF_UP表示遇到.5的情况时往上近似,例:1.5 --> 2
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 初始化计算数据
	 * 
	 * @date 2015年3月27日 下午6:08:11
	 * @author DaiLu
	 * @param obj
	 * @return
	 */
	public static BigDecimal initBigDecimal(Object obj) {
		if ("".equals(obj) || obj == null) {
			// 抛出不合法或不正确的参数异常
			throw new IllegalArgumentException("初始化计算数据时传入了空的参数!");
		}
		return new BigDecimal(String.valueOf(obj));
	}

	/**
	 * 小数位精确方式
	 */
	public enum RoundType {

		/** 四舍五入 */
		roundHalfUp,

		/** 向上取整 */
		roundUp,

		/** 向下取整 */
		roundDown
	}

	/**
	 * 设置精度
	 * 
	 * @param amount
	 *            数值
	 * @return 数值
	 */
	public static BigDecimal setScale(int priceScale, int roundingMode, BigDecimal amount) {
		if (amount == null) {
			return null;
		}
		return amount.setScale(priceScale, roundingMode);
	}
	
	public static BigDecimal multiply(BigDecimal b1, int i2) {
		// 按照JDK帮助文档说明,如果采用double类型的话有可能丢失精度,推荐采用String类型.
		BigDecimal b2 = new BigDecimal(Double.toString(i2));
		return b1.multiply(b2);
	}

	/**   
     * 将分为单位的转换为元并返回金额格式的字符串 （除100）  
     *   
     * @param amount  
     * @return  
     * @throws Exception   
     */    
    public static String changeF2Y(Long amount) throws Exception{    
        if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {    
            throw new Exception("金额格式有误");    
        }    
            
        int flag = 0;    
        String amString = amount.toString();    
        if(amString.charAt(0)=='-'){    
            flag = 1;    
            amString = amString.substring(1);    
        }    
        StringBuffer result = new StringBuffer();    
        if(amString.length()==1){    
            result.append("0.0").append(amString);    
        }else if(amString.length() == 2){    
            result.append("0.").append(amString);    
        }else{    
            String intString = amString.substring(0,amString.length()-2);    
            for(int i=1; i<=intString.length();i++){    
                if( (i-1)%3 == 0 && i !=1){    
                    result.append(",");    
                }    
                result.append(intString.substring(intString.length()-i,intString.length()-i+1));    
            }    
            result.reverse().append(".").append(amString.substring(amString.length()-2));    
        }    
        if(flag == 1){    
            return "-"+result.toString();    
        }else{    
            return result.toString();    
        }    
    }    
        
    /**  
     * 将分为单位的转换为元 （除100）  
     *   
     * @param amount  
     * @return  
     * @throws Exception   
     */    
    public static String changeF2Y(String amount) throws Exception{    
        if(!amount.matches(CURRENCY_FEN_REGEX)) {    
            throw new Exception("金额格式有误");    
        }    
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();    
    }    
        
    /**   
     * 将元为单位的转换为分 （乘100）  
     *   
     * @param amount  
     * @return  
     */    
    public static String changeY2F(Long amount){    
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();    
    }    
        
    /**   
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额  
     *   
     * @param amount  
     * @return  
     */    
    public static String changeY2F(String amount){    
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额    
        int index = currency.indexOf(".");    
        int length = currency.length();    
        Long amLong = 0l;    
        if(index == -1){    
            amLong = Long.valueOf(currency+"00");    
        }else if(length - index >= 3){    
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));    
        }else if(length - index == 2){    
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);    
        }else{    
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");    
        }    
        return amLong.toString();    
    }    
        
}
