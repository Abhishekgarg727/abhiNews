package com.abhishek.news.presistance.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.abhishek.news.model.Article

/**
 * Created by Abhishek Garg on 20/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */
@Dao
interface FeedsDao {
    @Query("SELECT * FROM Article")
    fun getAll(): List<Article>

    @Insert
    fun insertAll(vararg article : Article)

    @Delete
    fun delete(article: Article)
}