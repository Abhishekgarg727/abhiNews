package com.abhishek.news.api

import androidx.room.TypeConverter
import com.abhishek.news.model.Source
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * Created by Abhishek Garg on 20/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */
object Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayLisr(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}


object TypeConverterClass {
    @TypeConverter
    fun getMedia(longId: String?): Source? {
        return if (longId == null) null else Source()
    }
}