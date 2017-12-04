/*
 * Copyright 2012-2014 sencloud.com.cn. All rights reserved.
 * Support: http://www.sencloud.com.cn
 * License: http://www.sencloud.com.cn/license
 */
package com.steam.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Utils - 时间工具类
 *
 * @author tianlongxu
 * @version 3.0
 */
public class DateUtil {

    private static SimpleDateFormat year_format = new SimpleDateFormat("yyyy");

    private static SimpleDateFormat month_format = new SimpleDateFormat("yyyyMM");

    private static SimpleDateFormat default_format = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat time_format = new SimpleDateFormat("yyyyMMddHHmmss");

    private static SimpleDateFormat time_format_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat chinese_format = new SimpleDateFormat("yyyy年MM月dd日");

    private static SimpleDateFormat hour_minute_format = new SimpleDateFormat("HH:mm");

    /**
     * 返回下一年_格式yyyy
     *
     * @return int
     */
    public static int getNextYear() {
        return new GregorianCalendar().get(Calendar.YEAR) + 1;
    }

    /**
     * 返回当前的年
     *
     * @return int
     */
    public static int getCurrYear() {
        return new GregorianCalendar().get(Calendar.YEAR);
    }

    /**
     * 获取某年第一天日期_格式yyyy-MM-dd
     *
     * @param year 年份
     * @return String
     */
    public static String getCurrYearFirstDay(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return default_format.format(currYearFirst);
    }

    /**
     * 根据一个日期，返回是星期几的数字_星期天:1,星期一:2....星期六:7
     *
     * @param dateString String
     * @return int
     */
    public static int getWeekByDate(String dateString) {
        Date date = string2Date(dateString);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }


    public static String date2String(Date date, int _iType) {
        String strTemp = time_format.format(date);
        SimpleDateFormat formatter = null;
        switch (_iType) {
            case 0: // yyyymmdd
                strTemp = strTemp.substring(0, 8);
                break;
            case 4:// yyyy
                strTemp = strTemp.substring(0, 4);
                break;
            case 5:// yyyymm
                strTemp = strTemp.substring(0, 6);
                break;
            case 6: // yymmdd
                strTemp = strTemp.substring(2, 8);
                break;
            case 8: // yyyymmdd
                strTemp = strTemp.substring(0, 8);
                break;
            case 10: // yyyy-mm-dd
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                strTemp = formatter.format(date);
                break;
            case -6: // HHmmss
                strTemp = strTemp.substring(8);
                break;
            case -8: // HH:mm:ss
                formatter = new SimpleDateFormat("HH:mm:ss");
                strTemp = formatter.format(date);
                break;
            default:
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                strTemp = formatter.format(date);
                break;
        }
        return strTemp;
    }

    /**
     * 获得指定日期前后的日期，返回日期型值
     *
     * @param inDate 指定的日期
     * @param days   加减天数
     * @return Date
     */
    public static Date getDateByAddDays(Date inDate, int days) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(inDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获得指定日期前后的日期，返回String型值
     *
     * @param inDate 指定的日期
     * @param days   加减天数
     * @param _iType 加减天数
     * @return Date
     */
    public static String getDateByAddDays(String inDate, int days, int _iType) {
        Date dateStr = string2Date(inDate);
        Date tempDate = getDateByAddDays(dateStr, days);
        return date2String(tempDate, _iType);
    }

    /**
     * 获得指定日期前后的日期，返回日期型值
     *
     * @param inDate 指定的日期
     * @param month  加减天数
     * @return Date
     */
    public static Date getDateByAddMonth(Date inDate, int month) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(inDate);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 得到当前时间,格式为yyyyMMddhhmmss
     *
     * @return String
     */
    public static String generateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }

    /**
     * 得到当前时间,格式为yyyyMMddhhmmssSSS
     *
     * @return String
     */
    public static String generateTimeHM() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date());
    }

    /**
     * 根据格式生成当前日期时间
     *
     * @param formatStr
     * @return
     */
    public static String generateDateTime(String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date());
    }

    /**
     * 根据yyyy-MM-dd得到月份
     *
     * @param dateString String
     * @return int
     */
    public static int getMonthFromYear(String dateString) {
        Date date = string2Date(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到指定年的所有天数
     *
     * @param year String
     * @return day int
     */
    public static int getDayFromYear(String year) {
        Date date;
        int day = 0;
        try {
            date = year_format.parse(year);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            day = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 将日期字符串转换成日期型，日期格式为"yyyy-MM-dd"
     *
     * @param dateString 指定的日期字符串，格式为yyyyMMdd 或者yyyy-MM-dd
     * @return Date
     */
    public final static Date string2Date(String dateString) {
        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }
        try {
            String strFormat = "";
            switch (dateString.length()) {
                case 4:
                    strFormat = "yyyy";
                    break;
                case 6: // yymmdd
                    strFormat = "yyMMdd";
                    break;
                case 8: // yyyymmdd
                    strFormat = "yyyyMMdd";
                    break;
                case 10: // yyyy-mm-dd
                    strFormat = "yyyy-MM-dd";
                    break;
                case 14:
                    strFormat = "yyyyMMddHHmmss";
                    break;
                case 16:
                    strFormat = "yyyy-MM-dd HH:mm";
                    break;
                default:
                    strFormat = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
            // 这里没有做非法日期的判断，比如：＂2007-05-33＂
            Date timeDate = simpleDateFormat.parse(dateString);
            return timeDate;
        } catch (Exception e) {
            return new Date(0);
        }
    }

    /**
     * 获取时间
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取当前时间按时间
     *
     * @return
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 得到两个时间的差值，单位是小时
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static double getHourBetweenDates(Date beginDate, Date endDate) {

        long l1 = endDate.getTime();
        long l2 = beginDate.getTime();

        double cc = l1 - l2;
        return cc / (60 * 60 * 1000);
    }

    /**
     * 得到两个时间的差值，单位是分钟
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static double getMinuteBetweenDates(Date beginDate, Date endDate) {

        double cc = endDate.getTime() - beginDate.getTime();
        return cc / (60 * 1000);
    }

    /**
     * 得到两个时间的差值，单位是秒
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static double getSecondBetweenDates(Date beginDate, Date endDate) {

        double cc = endDate.getTime() - beginDate.getTime();
        return cc / 1000;
    }

    /**
     * 得到两个时间的差值，单位天
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDateBetweenDates(Date beginDate, Date endDate) {
        String bd = date2String(beginDate, 10);
        String ed = date2String(endDate, 10);
        Date bdd = string2Date(bd);
        Date edd = string2Date(ed);
        double cc = edd.getTime() - bdd.getTime();
        return ((int) cc / (24 * 60 * 60 * 1000));
    }

    public static void main(String[] args) {
        Date hastime2 = string2Date("2007-05-33");
        System.out.println(hastime2);
    }

    public static String obtainMonth(String dateStr, int m) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        try {
            Date d1 = df.parse(dateStr);
            Calendar g = Calendar.getInstance();
            g.setTime(d1);
            g.add(Calendar.MONTH, m);
            Date d2 = g.getTime();
            str = df.format(d2);
            str = str.replace("-", "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    // 获得20130906 这样格式的
    public static String obtainCurrteDate() {
        Calendar cal = Calendar.getInstance();
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);
        String ms = "";
        if (m + 1 < 10) {
            ms = "0" + (m + 1);

        } else {
            ms = String.valueOf(m + 1);
        }

        String ds = "";

        if (d < 10) {
            ds = "0" + d;

        } else {
            ds = String.valueOf(d);
        }

        return String.valueOf(cal.get(Calendar.YEAR)) + ms + ds;
    }

    /**
     * 获取一个人的年龄
     *
     * @param birthDay 生日
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static Integer getAge(Date birthDay) {
        if (birthDay == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else if (monthNow < monthBirth) {
                age--;
            }
        } else {
            // monthNow<monthBirth
            // donothing
        }

        return age;
    }

    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     *
     * @return 以yyyyMMddHHmmss为格式的当前系统时间
     */
    public static String getOrderNum() {
        Date date = new Date();
        return time_format.format(date);
    }

    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     *
     * @return 以yyyyMMddHHmmss为格式的当前系统时间
     */
    public static String getCouponNum() {
        Date date = new Date();
        return month_format.format(date);
    }

    /**
     * 返回当前日期的最小时间(精确到毫秒)
     *
     * @return 以yyyy-MM-dd HH:mm:ss为格式的当前日期的最小时间
     */
    public static String getCurrentDateMinTime() {
        Calendar curDateTime = Calendar.getInstance();
        curDateTime.set(Calendar.HOUR_OF_DAY, 0);
        curDateTime.set(Calendar.MINUTE, 0);
        curDateTime.set(Calendar.SECOND, 0);
        return time_format_2.format(curDateTime.getTime());
    }

    /**
     * 返回当前日期的最大时间(精确到毫秒)
     *
     * @return 以yyyy-MM-dd HH:mm:ss为格式的当前日期的最大时间
     */
    public static String getCurrentDateMaxTime() {
        Calendar curDateTime = Calendar.getInstance();
        curDateTime.set(Calendar.HOUR_OF_DAY, 23);
        curDateTime.set(Calendar.MINUTE, 59);
        curDateTime.set(Calendar.SECOND, 59);
        return time_format_2.format(curDateTime.getTime());
    }

    /**
     * 根据传入的“2017-01-22”日期字符串，返回中文日期格式“2017年1月22日”
     *
     * @return
     */
    public static String getChineseFormatDate(String dateStr) {
        try {
            dateStr = chinese_format.format(default_format.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 获得指定日期前后的日期，返回日期型值
     *
     * @param inDate 指定的日期
     * @param minute 加减分钟
     * @return Date
     */
    public static Date getDateByAddMinutes(Date inDate, int minute) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(inDate);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获得当天系统时间前后的时间，返回小时分钟,如果计算的时间不在同一天，返回“23:59”，否则返回计算之后的时间
     *
     * @param minute 加减分钟
     * @return String
     */
    public static String getCurrentDateStrByAddMinutes(int minute) {
        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        Date caculateDate = calendar.getTime();
        if (default_format.format(date).equals(default_format.format(caculateDate))) {
            return hour_minute_format.format(caculateDate);
        }
        return "23:59";
    }

    /**
     * @param firstDate
     * @param secondDate
     * @return
     * @Title: isSameDay
     * @Description: 比较两个日期是否是同一天，参数可以为日期、字符串（2017-01-24只能这种格式）
     * @return: boolean
     */
    public static boolean isSameDay(Object firstDate, Object secondDate) {
        String first = "";
        String second = "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (firstDate instanceof Date) {
            first = format.format(firstDate);
        } else {
            first = firstDate.toString();
        }
        if (secondDate instanceof Date) {
            second = format.format(secondDate);
        } else {
            second = secondDate.toString();
        }
        return first.equals(second);
    }

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    private static final Object object = new Object();

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern)
            throws RuntimeException {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 获取日期中的某数值。如获取月份
     *
     * @param date     日期
     * @param dateType 日期格式
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期字符串
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期字符串
     */
    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = DateToString(myDate, dateStyle);
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 获取精确的日期
     *
     * @param timestamps 时间long集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i)
                                - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = {timestamps.get(i),
                                timestamps.get(j)};
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
                // 因此不能将minAbsoluteValue取默认值0
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = absoluteValues.get(0);
                    for (int i = 1; i < absoluteValues.size(); i++) {
                        if (minAbsoluteValue > absoluteValues.get(i)) {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);

                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne
                                : dateTwo;
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断字符串是否为日期字符串
     *
     * @param date 日期字符串
     * @return true or false
     */
    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (getDateStyle(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * 获取日期字符串的日期风格。失敗返回null。
     *
     * @param date 日期字符串
     * @return 日期风格
     */
    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
        List<Long> timestamps = new ArrayList<Long>();
        for (DateStyle style : DateStyle.values()) {
            if (style.isShowOnly()) {
                continue;
            }
            Date dateTmp = null;
            if (date != null) {
                try {
                    ParsePosition pos = new ParsePosition(0);
                    dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                    if (pos.getIndex() != date.length()) {
                        dateTmp = null;
                    }
                } catch (Exception e) {
                }
            }
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = map.get(accurateDate.getTime());
        }
        return dateStyle;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static Date StringToDate(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return StringToDate(date, dateStyle);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date      日期字符串
     * @param dateStyle 日期风格
     * @return 日期
     */
    public static Date StringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = StringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String DateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date      日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String DateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date       旧日期字符串
     * @param newPattern 新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, String newPattern) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String StringToString(String date, DateStyle newDateStyle) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newDateStyle);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date        旧日期字符串
     * @param olddPattern 旧日期格式
     * @param newPattern  新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, String olddPattern,
                                        String newPattern) {
        return DateToString(StringToDate(date, olddPattern), newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddDteStyle 旧日期风格
     * @param newParttern  新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, DateStyle olddDteStyle,
                                        String newParttern) {
        String dateString = null;
        if (olddDteStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(),
                    newParttern);
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddPattern  旧日期格式
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String StringToString(String date, String olddPattern,
                                        DateStyle newDateStyle) {
        String dateString = null;
        if (newDateStyle != null) {
            dateString = StringToString(date, olddPattern,
                    newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddDteStyle 旧日期风格
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String StringToString(String date, DateStyle olddDteStyle,
                                        DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle != null && newDateStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(),
                    newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期字符串
     */
    public static String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date        日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期字符串
     */
    public static String addMonth(String date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date        日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期字符串
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期字符串
     */
    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date       日期字符串
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期字符串
     */
    public static String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date       日期
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date         日期字符串
     * @param minuteAmount 增加数量。可为负数
     * @return 增加分钟后的日期字符串
     */
    public static String addMinute(String date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date         日期
     * @param minuteAmount 增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date         日期字符串
     * @param secondAmount 增加数量。可为负数
     * @return 增加秒钟后的日期字符串
     */
    public static String addSecond(String date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date         日期
     * @param secondAmount 增加数量。可为负数
     * @return 增加秒钟后的日期
     */
    public static Date addSecond(Date date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期字符串
     * @return 年份
     */
    public static int getYear(String date) {
        return getYear(StringToDate(date));
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期字符串
     * @return 月份
     */
    public static int getMonth(String date) {
        return getMonth(StringToDate(date));
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期字符串
     * @return 天
     */
    public static int getDay(String date) {
        return getDay(StringToDate(date));
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期
     * @return 天
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期字符串
     * @return 小时
     */
    public static int getHour(String date) {
        return getHour(StringToDate(date));
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 分钟
     */
    public static int getMinute(String date) {
        return getMinute(StringToDate(date));
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 秒钟
     */
    public static int getSecond(String date) {
        return getSecond(StringToDate(date));
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期
     * @return 秒钟
     */
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    /**
     * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static String getDate(String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期
     * @return 日期
     */
    public static String getDate(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 时间
     */
    public static String getTime(String date) {
        return StringToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期
     * @return 时间
     */
    public static String getTime(Date date) {
        return DateToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认yyyy-MM-dd HH:mm:ss格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 时间
     */
    public static String getDateTime(String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认yyyy-MM-dd HH:mm:ss格式。失败返回null。
     *
     * @param date 日期
     * @return 时间
     */
    public static String getDateTime(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期字符串
     * @return 星期
     */
    public static Week getWeek(String date) {
        Week week = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            week = getWeek(myDate);
        }
        return week;
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期
     * @return 星期
     */
    public static Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
            case 0:
                week = Week.SUNDAY;
                break;
            case 1:
                week = Week.MONDAY;
                break;
            case 2:
                week = Week.TUESDAY;
                break;
            case 3:
                week = Week.WEDNESDAY;
                break;
            case 4:
                week = Week.THURSDAY;
                break;
            case 5:
                week = Week.FRIDAY;
                break;
            case 6:
                week = Week.SATURDAY;
                break;
        }
        return week;
    }

    /**
     * 获取两个日期相差的天数
     *
     * @param date      日期字符串
     * @param otherDate 另一个日期字符串
     * @return 相差天数。如果失败则返回-1
     */
    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));
    }

    /**
     * @param date      日期
     * @param otherDate 另一个日期
     * @return 相差天数。如果失败则返回-1
     */
    public static int getIntervalDays(Date date, Date otherDate) {
        int num = -1;
        Date dateTmp = DateUtil.StringToDate(DateUtil.getDate(date),
                DateStyle.YYYY_MM_DD);
        Date otherDateTmp = DateUtil.StringToDate(DateUtil.getDate(otherDate),
                DateStyle.YYYY_MM_DD);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    /**
     * 获取期间的年龄
     *
     * @param date
     * @param otherDate
     * @return String
     */
    public static String getAge(Date date, Date otherDate) {
        int dis = DateUtil.getIntervalDays(new Date(), otherDate);
        int year = dis / 365;
        int month = dis % 365 / 30;
        int day = dis % 365 % 31;
        String age = (year > 0 ? year + "岁" : "")
                + (month > 0 ? month + "个月" : "") + (day + "天");
        return age;
    }
}
