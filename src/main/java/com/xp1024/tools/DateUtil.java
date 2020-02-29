package com.xp1024.tools;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.tools
 * @date 2020/2/28 15:03
 */
public class DateUtil {

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd) 到分
     */
    public final static String DATE_PATTERN_MIN = "yyyy-MM-dd HH:mm";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Date toDate(String stringDate) throws ParseException {
        if (StringUtils.isEmpty(stringDate)) {
            return null;
        }
        Date date = toDate(stringDate, DATE_TIME_PATTERN);
        return date;
    }

    public static Date toDate(String stringDate, String patter) throws ParseException {
        if (StringUtils.isEmpty(stringDate)) {
            return null;
        }
        if (StringUtils.isEmpty(patter)) {
            patter = DATE_TIME_PATTERN;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patter);
        Date parse = simpleDateFormat.parse(stringDate);
        return parse;
    }

    public static String dateToString(Date date) {
        return dateToString(date, DATE_TIME_PATTERN);
    }

    public static String dateToString(Date date, String patter) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isEmpty(patter)) {
            patter = DATE_TIME_PATTERN;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patter);
        return simpleDateFormat.format(date);
    }
}
