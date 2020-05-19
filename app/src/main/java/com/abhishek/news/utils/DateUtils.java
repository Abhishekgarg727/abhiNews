package com.abhishek.news.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {


    public static final String SIMPLE_DATE = "dd-MM-yyyy";
    public static final String SIMPLE_DATE_WITH_TIME = "dd-MM-yyyy h:mm a"; // 10-08-2019 12:08 PM


    public static final String[] POSSIBLE_DATE_FORMATS = {
            "yyyy.MM.dd G 'at' HH:mm:ss z",
            "EEE, MMM d, ''yy",
            "h:mm a",
            "hh 'o''clock' a, zzzz",
            "K:mm a, z",
            "yyyyy.MMMMM.dd GGG hh:mm aaa",
            "EEE, d MMM yyyy HH:mm:ss Z",
            "yyMMddHHmmssZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "YYYY-'W'ww-u",
            "EEE, dd MMM yyyy HH:mm:ss z",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "EEE, dd MMM yyyy HH:mm zzzz",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm'Z'",
            "yyyy-MM-dd'T'HH:mmZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSzzzz",
            "yyyy-MM-dd'T'HH:mm:sszzzz",
            "yyyy-MM-dd'T'HH:mm:ss z",
            "yyyy-MM-dd'T'HH:mm:ssz",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HHmmss.SSSz",
            "yyyy-MM-dd",
            "yyyyMMdd",
            "dd/MM/yy",
            "dd/MM/yyyy"
    };

    @SuppressLint("SimpleDateFormat")
    public static Date parse(String pattern, String date) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    public static int[] getIntegerArrayUsingServerFormattedDate(String date) {
        // 2019/11/26
        String[] dateData = date.split("/");
        int year = Integer.valueOf(dateData[0]);
        int month = Integer.valueOf(dateData[1]);
        // because of bug in library
        month -= 1;
        int day = Integer.valueOf(dateData[2]);
        return new int[]{year, month, day};
    }


    public static String getServerDateWithTimeUsingTimeStamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat resultFormat = new SimpleDateFormat("yyyy-MM-dd");
        return resultFormat.format(date);
    }


    public static String getServerDateWithTimeUsingTimeStampInSlashFormat(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat resultFormat = new SimpleDateFormat("yyyy/MM/dd");
        return resultFormat.format(date);
    }


    public static String getDateWithDD_MM_YYYYFormatUsingServerDate(String serverDate) {
        String resultDate = "";
        //2019/10/27
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat resultFormat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date date = dateFormat.parse(serverDate);
            resultDate = resultFormat.format(date);
        } catch (ParseException e) {
        }
        return resultDate;
    }


    public static long getTimeStampUsing24HourAndMinuteOnly(int hour, int min) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        return cal.getTimeInMillis();
    }

    public static String getHourAndMinUsingTimeStamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat resultFormat = new SimpleDateFormat("H:m");
        return resultFormat.format(date);
    }


    public static String getDateWithTimeUsingTimeStamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat resultFormat = new SimpleDateFormat("dd-MMM, hh:mm");
        return resultFormat.format(date);
    }


    public static String getHourUsingTimeStamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat resultFormat = new SimpleDateFormat("H");
        return resultFormat.format(date);
    }

    public static String getMinUsingTimeStamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat resultFormat = new SimpleDateFormat("m");
        return resultFormat.format(date);
    }

    public static String getDateWithMonth(String serverDate) {
        String resultDate = "";
        //2019/10/27
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat resultFormat = new SimpleDateFormat("dd\nMMM");
        try {
            Date date = dateFormat.parse(serverDate);
            resultDate = resultFormat.format(date);
        } catch (ParseException e) {
        }
        return resultDate;
    }

    public static String getDateUsingZFormatTime(String serverDate) {
        String resultDate = "";
        //2019/10/27
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat resultFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = dateFormat.parse(serverDate);
            resultDate = resultFormat.format(date);
        } catch (ParseException e) {
        }
        return resultDate;
    }

    public static String getDateInMMMddEEEUsingZFormatTime(String serverDate) {
        String resultDate = "";
        // "2019-12-17T10:22:29.45Z
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat resultFormat = new SimpleDateFormat("MMM dd, EEE");
        try {
            Date date = dateFormat.parse(serverDate);
            resultDate = resultFormat.format(date);
        } catch (Exception e) {
        }
        return resultDate;
    }

    public static Date getDateObjectUsingServerDateString(String serverDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return dateFormat.parse(serverDate);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date getTodayDateObjectForCalenderView() {
        Calendar cal = GregorianCalendar.getInstance();
        return cal.getTime();
    }

    public static String getDate(String serverDate) {
        String resultDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat resultFormat = new SimpleDateFormat("dd");
        try {
            Date date = dateFormat.parse(serverDate);
            resultDate = resultFormat.format(date);
        } catch (ParseException e) {
        }
        return resultDate;
    }

    public static Date getDateObjUsingServerDate(String serverDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = dateFormat.parse(serverDate);
            return date;
        } catch (ParseException e) {
        }
        return null;
    }

    public static String getMonth(String serverDate) {
        String resultDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat resultFormat = new SimpleDateFormat("MMM");
        try {
            Date date = dateFormat.parse(serverDate);
            resultDate = resultFormat.format(date);
        } catch (ParseException e) {
        }
        return resultDate;
    }


}
