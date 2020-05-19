package com.abhishek.news.utils;

import androidx.lifecycle.MutableLiveData;

public class MutableLiveDataUtils {
    public static int getInteger(MutableLiveData<Integer> value){
        return  value != null && value.getValue() != null ? value.getValue() : 0;
    }

    public static float getFloat(MutableLiveData<Float> value){
        return  value != null && value.getValue() != null ? value.getValue() : 0f;
    }

    public static String getString(MutableLiveData<String> value){
        return  value != null && value.getValue() != null ? value.getValue() : "";
    }

    public static double getDouble(MutableLiveData<Double> value){
        return  value != null && value.getValue() != null ? value.getValue() : 0.0;
    }

    public static boolean getBoolean(MutableLiveData<Boolean> value){
        return  value != null && value.getValue() != null ? value.getValue() : false;
    }
}
