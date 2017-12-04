package com.steam.util;

import com.sencloud.entity.RepaymentDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommonWayUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonWayUtil.class);

    /**
     * 计算还款详情列表
     *
     * @param period 期数
     * @param amt    总金额
     * @param payDay 还款日
     * @return list
     * @author 罗航
     */
    public static List repaymentDetail(int period, BigDecimal amt, int payDay) {
        List<Object> list = new ArrayList<Object>();
        Date date = new Date();
        String time06 = "";
        Calendar time = Calendar.getInstance();
        // 当前日期：天数
        int day = time.get(Calendar.DAY_OF_MONTH);
        int j = 1;
        for (int i = 0; i < period; i++) {
            RepaymentDetail repay = new RepaymentDetail();
            repay.setI(i + 1);
            // 还款日的计算，当前天数+1小于本月还款日本月还，否则次月还
            if (day + 1 <= payDay) {
                if (time.get(Calendar.MONTH) + 1 + i > 12) {
                    time06 = String.valueOf(time.get(Calendar.YEAR) + 1) + "-"
                            + String.valueOf(j++) + "-"
                            + String.valueOf(payDay);
                } else {
                    time06 = String.valueOf(time.get(Calendar.YEAR)) + "-"
                            + String.valueOf(time.get(Calendar.MONTH) + 1 + i)
                            + "-" + String.valueOf(payDay);
                }

            } else {
                if (time.get(Calendar.MONTH) + 1 + i > 12) {
                    time06 = String.valueOf(time.get(Calendar.YEAR) + 1) + "-"
                            + String.valueOf(j++) + "-"
                            + String.valueOf(payDay);
                } else {
                    time06 = String.valueOf(time.get(Calendar.YEAR)) + "-"
                            + String.valueOf(time.get(Calendar.MONTH) + 2 + i)
                            + "-" + String.valueOf(payDay);
                }

            }
            repay.setDate(time06);
            BigDecimal monthPrice = amt.divide(new BigDecimal(period), 2,
                    RoundingMode.HALF_UP);
            // 判断是不是最后一期还款，最后一期还款计算方式为：总金额-每期分期金额*（期数-1）
            if (i == period - 1) {
                repay.setPayback(amt.subtract(monthPrice
                        .multiply(new BigDecimal(period - 1))));
            } else {
                repay.setPayback(monthPrice);
            }
            list.add(repay);
        }
        return list;
    }

    /**
     * 获取给定日期的周数
     *
     * @param str （格式yyyy-MM-dd HH:MM:ss）
     * @return
     */
    public static String getWeek(String str) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date;
        try {
            date = df.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int number = calendar.get(Calendar.DAY_OF_WEEK);
            String[] strs = {"", "周日", "周一", "周二", "周三", "周四", "周五", "周六",};
            return strs[number];
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串日期转换
     *
     * @param dateStr
     * @return
     */
    public static String dateStrconvertInt(String dateStr) {
        if (dateStr.equals("10天")) {
            dateStr = String.valueOf(10);
        } else if (dateStr.equals("15天")) {
            dateStr = String.valueOf(15);
        } else if (dateStr.equals("25天")) {
            dateStr = String.valueOf(25);
        } else if (dateStr.equals("1个月")) {
            dateStr = String.valueOf(30);
        } else if (dateStr.equals("2个月")) {
            dateStr = String.valueOf(60);
        } else if (dateStr.equals("3个月")) {
            dateStr = String.valueOf(90);
        } else {
            dateStr = "";
        }
        return dateStr;
    }
}
