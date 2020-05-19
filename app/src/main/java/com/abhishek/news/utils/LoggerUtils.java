package com.abhishek.news.utils;

import android.util.Log;

import com.abhishek.news.BuildConfig;

public class LoggerUtils {

    private static final boolean loggingEnabled = BuildConfig.LOGGING;

    public static void debug(String tag, String data){
        if(loggingEnabled  && data != null) Log.d(tag,data);
    }

    public static void error(String tag, String data){
        if(loggingEnabled && data != null) Log.e(tag,data);
    }

    public static void info(String tag, String data){
        if(loggingEnabled  && data != null) Log.i(tag,data);
    }

    public static void warn(String tag, String data){
        if(loggingEnabled  && data != null) Log.w(tag,data);
    }

    public static void verbose(String tag, String data){
        if(loggingEnabled  && data != null) Log.d(tag,data);
    }



}
