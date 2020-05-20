package com.abhishek.news.presistance.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhishek.news.model.Article
import com.abhishek.news.presistance.database.dao.FeedsDao

/**
 * Created by Abhishek Garg on 20/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */
@Database(entities = arrayOf(Article::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedsDao(): FeedsDao
}