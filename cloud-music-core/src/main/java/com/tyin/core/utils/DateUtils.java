package com.tyin.core.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tyin
 * @date 2022/3/26 2:45
 * @description ...
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils{

    public final static String YYYY = "yyyy";
    public final static String YYYY_MM = "yyyy-MM";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date parseStrToDate(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
