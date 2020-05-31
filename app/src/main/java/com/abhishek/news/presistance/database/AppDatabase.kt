package com.abhishek.news.presistance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhishek.news.api.Converters
import com.abhishek.news.model.Article
import com.abhishek.news.model.Source
import com.abhishek.news.presistance.database.dao.FeedsDao

/**
 * Created by Abhishek Garg on 20/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */
//@Database(entities = arrayOf(Article::class), version = 1)
@Database(entities = [Article::class, Source::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedsDao(): FeedsDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        @JvmStatic
        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "abhiNews").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}