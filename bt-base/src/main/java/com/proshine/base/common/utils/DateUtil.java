package com.proshine.base.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类
 *
 * @author 高孙琼
 */
public class DateUtil {


    /**
     * yyyy-MM-dd
     */
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String LONG_HOUSE_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * HH:mm:ss
     */
    public static final String SHORT_HMS_FORMAT = "HH:mm:ss";


    @SuppressWarnings("unused")
    private static final SimpleDateFormat SHORT_SDF = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressWarnings("unused")
    private static final SimpleDateFormat LONG_HOUR_SDF = new SimpleDateFormat("yyyy-MM-dd HH");
    @SuppressWarnings("unused")
    private static final SimpleDateFormat LONG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 按照格式获取当前系统时间
     *
     * @param dateFormat 时间格式
     * @return 当前系统时间字符串
     */
    public static String getCurrentTime(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 按照指定格式获取当前系统时间
     *
     * @param dateFormat 日期格式
     * @return 当前日期
     */
    public static Date getCurrentDate(String dateFormat) {
        return converToDate(getCurrentTime(dateFormat), dateFormat);
    }

    /**
     * 判斷兩個日期是否相等.
     *
     * @param oldDate oldDate
     * @param newDate newDate
     * @return 是否相等.
     */
    public static boolean isEquals(Date oldDate, Date newDate) {
        long days = daysOfTwo(oldDate, newDate);

        return days == 0;
    }

    /**
     * 将字符串转换为Date类型日期.
     *
     * @param dateStr 日期字符串
     * @return Date
     */
    public static Date converToDate(String dateStr, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 将字符串转换为Date类型日期.
     *
     * @param dateStr    日期字符串
     * @param strFormat  格式
     * @param dateFormat 格式
     * @return Date
     */
    public static Date converToDate(String dateStr, String strFormat, String dateFormat) {
        try {
            DateFormat strDf = new SimpleDateFormat(strFormat);
            Date tmpDate = strDf.parse(dateStr);

            DateFormat dateDf = new SimpleDateFormat(dateFormat);
            String tmpStr = dateDf.format(tmpDate);

            return dateDf.parse(tmpStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 把日期转为字符串.
     *
     * @param date Date日期
     * @return String
     */
    public static String converToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 计算两个日期相差的天数.
     *
     * @param fDate fDate
     * @param oDate oDate
     * @return 相差的天数
     */
    public static long daysOfTwo(Date fDate, Date oDate) {
        long time1 = fDate.getTime();
        long time2 = oDate.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }

        return diff / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取日期时间加减一年
     *
     * @param date   基准日期
     * @param offset 偏移年数
     * @return Date
     */
    public static Date getDate(Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, offset);
        return cal.getTime();
    }

    /**
     * 获取本星期任意一天时间
     *
     * @param offset 第几天
     * @return Date
     */
    public static Date getThisWeekDay(int offset) {
        Calendar cal = Calendar.getInstance();
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day + offset);
        return cal.getTime();
    }

    /**
     * 返回指定日期是该年中的第几周
     *
     * @param date 时间
     * @return int 第几周
     */
    public static int getWeeksNoInYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取本周周一 的时间
     *
     * @return String
     */
    public static String getThisWeekMonday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 1);
        return SHORT_SDF.format(c.getTime());
    }

    /**
     * Description:获取本周日时间(中国)
     *
     * @return String
     */
    public static String getThisWeekSunday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 7);
        return SHORT_SDF.format(c.getTime());

    }

    /**
     * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
     *
     * @param date "yyyy/MM/dd",或者"yyyy-MM-dd"
     * @return 返回一个代表当期日期是星期几的数字。
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static Map<String, String> getMonAndSunForDate(String date) {
        Map<String, String> map = new HashMap<>(16);
        Calendar cal = Calendar.getInstance();
        cal.setTime(converToDate(date, SHORT_DATE_FORMAT));
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        map.put("mon", SHORT_SDF.format(cal.getTime()));
        cal.add(Calendar.DATE, 6);
        map.put("sun", SHORT_SDF.format(cal.getTime()));
        return map;
    }

    /**
     * 获取指定日期的偏移量后的日期
     *
     * @param date 指定日期
     * @param days 正数向后偏移天数，负数向前偏移天数
     * @return 日期对象
     */
    public static Date getDateObject(String date, int days) {
        Date date1 = converToDate(date, SHORT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DATE, days);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在月份开始的时间戳
     *
     * @param date 指定日期
     * @return 时间戳
     */
    public static Long getMonthBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置为1号，当前日期几位本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定日期所在月份最后一天的时间戳
     *
     * @param date 指定日期
     * @return 指定月份的最后一天时间戳
     */
    public static Long getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置为当月最后一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟设置为59
        calendar.set(Calendar.MINUTE, 59);
        //将秒设置为59
        calendar.set(Calendar.SECOND, 59);
        //将毫秒至999
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTimeInMillis();
    }

    /**
     * 字符串转时间戳
     *
     * @param date 日期字符串
     * @return Long
     */
    public static Long getTimeMinllis(String date) {
        Date date1 = converToDate(date, SHORT_DATE_FORMAT);
        return date1.getTime();
    }

    /**
     * 以当前时间为基准，获取当前周的任意一天的时间戳
     *
     * @param weekDay 周几 0:周日 ~ 6:周六，以此类推
     * @param hour    设置几点
     * @param minute  设置分钟
     * @return 时间戳
     */
    public static Long getTimeMillis(int weekDay, Integer hour, Integer minute) {
        Calendar calendar = Calendar.getInstance();
        //设置周一为当前星期的第一天
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, weekDay + 1);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 将时间字符串转化成整型数组
     *
     * @param timeStr 时间字符串
     * @return Integer
     */
    public static Integer[] getMinuteAndSecond(String timeStr) {
        String[] arr = timeStr.split(":");
        return new Integer[]{Integer.valueOf(arr[0]), Integer.valueOf(arr[1])};
    }

    /**
     * 获取指定日期所在周的开始时间戳
     *
     * @param date 指定的时间日期
     * @return 指定时间周的第一天时间戳
     */
    public static Long getWeekStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //获得当前日期是一个星期的第几天  
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        //设置周一为当前周的第一天
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //获得当前日期是一个星期的第几天 
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定日期所在周的结束时间戳
     *
     * @param date 指定的时间日期
     * @return 指定时间周的最后一天时间戳
     */
    public static Long getWeekEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //获得当前日期是一个星期的第几天  
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        //设置周一为当前周的第一天
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //获得当前日期是一个星期的第几天 
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        calendar.add(Calendar.DATE, 6);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据字符串年月日转化成时间戳年月日
     *
     * @param dateStr 日期时间戳
     * @return Long
     */
    public static Long getDateTimeInMillis(String dateStr) {
        Date date = converToDate(dateStr, DateUtil.SHORT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 将某一时刻的时间戳转化为日期时间戳
     *
     * @param time 某一时刻的时间戳
     * @return Long日期时间戳
     */
    public static Long getDateTimeInMillis(Long time) {
        Date date = getDate(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 指定毫秒数表示的日期
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日期
     */
    public static Date getDate(long millis) {
        return new Date(millis);
    }
}
