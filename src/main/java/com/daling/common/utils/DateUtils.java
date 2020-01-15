package com.daling.common.utils;


import com.sun.istack.internal.Nullable;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author liuyi
 * @since 2020/1/14
 */
public class DateUtils {


    public static  final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_CHINESE = "yyyy年M月d日";

    public static final String FORMAT_HH_MM = "HH:mm";

    public static final String FORMAT_YMDHMS = "yyyyMMdd hh:mm:ss";

    public static final String START_TIME = "00:00:00";

    public static final String END_TIME = "23:59:59";

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(FORMAT_YYYY_MM_DD_HH_MM_SS);


    /**
     * 获取系统当前时间 返回 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime() {
        DateTime dt = new DateTime();
        String time = dt.toString(FORMAT_YYYY_MM_DD_HH_MM_SS);
        return time;
    }

    /**
     * 获取系统当前时间按照指定格式返回
     * @param pattern
     * @return
     */
    public static String getCurrentTimePattern(String pattern) {
        DateTime dt = new DateTime();
        String time = dt.toString(pattern);
        return time;
    }

    /**
     * 获取当前日期
     * @return yyyy-MM-dd
     */
    public static String getCurrentDate() {
        DateTime dt = new DateTime();
        String date = dt.toString(FORMAT_YYYY_MM_DD);
        return date;
    }

    /**
     * 获取当前日期按照指定格式
     * @param pattern
     * @return
     */
    public static String getCurrentDatePattern(String pattern) {
        DateTime dt = new DateTime();
        String date = dt.toString(pattern);
        return date;
    }

    /**
     * 按照时区转换时间
     * @param date
     * @param timeZone 时区
     * @param pattern
     * @return
     */
    public static String format(Date date, TimeZone timeZone, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

    /**
     * 获取指定时间
     * @param year 年
     * @param month 月
     * @param day 天
     * @param hour 小时
     * @param minute 分钟
     * @param seconds 秒
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getPointTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer seconds) {
        DateTime dt = new DateTime(year, month, day, hour, minute, seconds);
        String date = dt.toString(FORMAT_YYYY_MM_DD_HH_MM_SS);
        return date;
    }

    /**
     * 获取指定时间 指定返回格式
     * @param year 年
     * @param month 月
     * @param day 天
     * @param hour 小时
     * @param minute 分钟
     * @param seconds 秒
     * @param pattern 自定义格式
     * @return pattern
     */
    public static String getPointTimePattern(Integer year, Integer month, Integer day, Integer hour,
                                             Integer minute, Integer seconds, String pattern) {
        DateTime dt = new DateTime(year, month, day, hour, minute, seconds);
        String date = dt.toString(pattern);
        return date;
    }

    /**
     * 获取指定日期
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getPointDate(Integer year, Integer month, Integer day) {
        LocalDate dt = new LocalDate(year, month, day);
        String date = dt.toString(FORMAT_YYYY_MM_DD);
        return date;
    }

    /**
     * 获取指定日期 返回指定格式
     * @param year
     * @param month
     * @param day
     * @param pattern
     * @return
     */
    public static String getPointDatPattern(Integer year, Integer month, Integer day, String pattern) {
        LocalDate dt = new LocalDate(year, month, day);
        String date = dt.toString(pattern);
        return date;
    }


    /**
     * 获取当前是一周星期几
     * @return
     */
    public static String getWeek() {
        DateTime dts = new DateTime();
        return DayWeekEnum.getNameByCode(dts.getDayOfWeek());
    }


    /**
     * 获取指定时间是一周的星期几
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getWeekPoint(Integer year, Integer month, Integer day) {
        LocalDate dts = new LocalDate(year, month, day);
        return DayWeekEnum.getNameByCode(dts.getDayOfWeek());
    }


    /**
     * 格式化日期 日期转为字符串
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS);
        return format.format(date);
    }

    /**
     * 日期转为字符串 指定格式
     * @param date 日期
     * @param pattern 日期格式
     * @return
     */

    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 字符串转为日期 指定格式
     * @param date 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String date, String pattern) {
        if (date == null) {
            return null;
        }
        Date resultDate = null;
        try {
            resultDate = new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            throw new GenericBusinessException(e);
        }
        return resultDate;
    }

    /**
     * 字符串转为日期
     * @param date 日期字符串
     * @return
     */
    public static Date parse(String date) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS).parse(date);
        } catch (ParseException e) {
            throw new GenericBusinessException(e);
        }
    }

    /**
     * 毫秒数转为字符串 按照指定格式转换
     * @param timestamp
     * @return
     */
    public static String format(Long timestamp, String pattern) {
        String dateStr = "";
        if (null == timestamp || timestamp.longValue() < 0) {
            return dateStr;
        }
        try {
            Date date = new Date(timestamp);
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            dateStr = format.format(date);
        } catch (Exception e) {
            throw new GenericBusinessException(e);
        }
        return dateStr;
    }

    /**
     *获取当前时间前几天时间,按指定格式返回
     * @param days
     * @return
     */
    public static String forwardDay(Integer days, String format) {
        DateTime dt = new DateTime();
        DateTime y = dt.minusDays(days);
        return y.toString(format);
    }

    /**
     * 获取当前时间前几天时间
     * @param days
     * @return
     */
    public static Date forwardDay(Integer days) {
        DateTime dt = new DateTime();
        DateTime y = dt.minusDays(days);
        return y.toDate();
    }

    /**
     * 计算两个时间相差多少天
     * @param startDate
     * @param endDate
     * @return
     */
    @Nullable
    public static Integer diffDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        DateTime dt1 = new DateTime(startDate);
        DateTime dt2 = new DateTime(endDate);
        int day = Days.daysBetween(dt1, dt2).getDays();
        return Math.abs(day);
    }

    /**
     * 获取指定间隔天数的日期
     * @param date
     * @param offset
     * @return
     */
    public static Date addDay(Date date, int offset) {
        DateTime dt1;
        if (date == null) {
            dt1 = new DateTime().plusDays(offset);
            return dt1.toDate();
        }
        dt1 = new DateTime(date).plusDays(offset);
        return dt1.toDate();
    }

    /**
     * 获取日期的开始时间
     * @param dateStr
     * @return
     */
    public static Date getDateStart(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YMDHMS);
        String subStr = dateStr.substring(0,8);
        try {
            Date date = simpleDateFormat.parse(subStr + " " + START_TIME);
            return date;
        } catch (ParseException e) {
            throw new GenericBusinessException(e);
        }
    }

    /**
     * 获取日期的结束时间
     * @param dateStr
     * @return
     */
    public static Date getDateEnd(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YMDHMS);
        String subStr = dateStr.substring(0,8);
        try {
            Date date = simpleDateFormat.parse(subStr + " " + END_TIME);
            return date;
        } catch (ParseException e) {
            throw new GenericBusinessException(e);
        }
    }

}
