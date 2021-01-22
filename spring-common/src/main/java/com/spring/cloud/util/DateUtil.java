package com.spring.cloud.util;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static void main(String[] abc) {
        int y, m, d, h, mi, s;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE);
        h = cal.get(Calendar.HOUR_OF_DAY);
        mi = cal.get(Calendar.MINUTE);
        s = cal.get(Calendar.SECOND);
        System.out.println("现在时刻是" + y + "年" + m + "月" + d + "日" + h + "时" + mi + "分" + s + "秒");
        System.out.println(new Date());
    }

    public static SimpleDateFormat SDF_YYYY_MM_DD_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat SDF_YYYY_MM_DD_ = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat MMdd = new SimpleDateFormat("MMdd");
    public static SimpleDateFormat YYYYMM = new SimpleDateFormat("yyyyMM");
    public static SimpleDateFormat YYYYMMdd = new SimpleDateFormat("yyyyMMdd");

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formatMMdd(Date date) {
        return MMdd.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formatYYYYMM(Date date) {
        return YYYYMM.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formatYYYYMMdd(Date date) {
        return YYYYMMdd.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formatYYYDDHHHHmmss(Date date) {
        return SDF_YYYY_MM_DD_HH_mm_ss.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formatSDF_YYYY_MM_DD_(Date date) {
        return SDF_YYYY_MM_DD_.format(date);
    }

    /**
     * <B>获得当前月</B><br>
     * 注意月份是从0开始的,比如当前7月，获得的month为6
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        int m = cal.get(Calendar.MONTH);
        return m + 1;
    }

    /*
     * 得到下一年的时间
     */
    public static Date getNextYearByTime(Date ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);//设置起时间
        cal.add(Calendar.YEAR, 1);//增加一年
        cal.add(Calendar.DATE, 0); //得到前一天
        //cd.add(Calendar.DATE, n);//增加一天  
        //cd.add(Calendar.DATE, -10);//减10天  
        //cd.add(Calendar.MONTH, n);//增加一个月   

        return cal.getTime();
    }

    /*
     *Timestamp 转Date
     **/
    public static Date testTimestampToDate(Timestamp ts) {
        Date date = ts;
        System.out.println(date);  // 2017-01-15 21:31:47.801
//        很简单，但是此刻date对象指向的实体却是一个Timestamp，即date拥有Date类的方法，但被覆盖的方法的执行实体在Timestamp中。

        date = new Date(ts.getTime());
        System.out.println(date); // Sun Jan 15 21:31:47 CST 2017
        return date;
    }

    /**
     * Date转 Timestamp
     */
    public static Timestamp testDateToTimestamp(Date date) {
        Timestamp ts = new Timestamp(date.getTime());
        System.out.println(ts); // 2017-01-15 21:33:32.203
        return ts;
    }

    /**
     * 获取day天之后的日期
     *
     * @param day 天数
     * @return
     */
    public static Date getDate(int day) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return calendar1.getTime();
    }

    /**
     * 获取当前时间到凌晨12点剩余秒数
     *
     * @return
     */
    public static int getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        int a = (int) ((cal.getTimeInMillis() - System.currentTimeMillis()) / 1000);
        return a;
    }

    /**
     * 获得往上某月月1号零时零分零秒
     *
     * @return
     */
    public static Date initDateByMonth(Integer OFMONTH) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (OFMONTH > 0) {
            calendar.set(Calendar.DAY_OF_MONTH, OFMONTH);
        } else {
            calendar.set(Calendar.MONTH, OFMONTH);
            calendar.set(Calendar.DATE, 1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


}
