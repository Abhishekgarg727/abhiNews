package com.abhishek.news.api

import com.abhishek.news.BuildConfig
import com.abhishek.news.model.TopHeadlinesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Abhishek Garg on 16/05/20 - https://www.linkedin.com/in/abhishekgarg727/

 */
interface RestApiService {
    private val NEWS_API_KEY: String
        get() = BuildConfig.NEWS_API_KEY

    // Top Headlines
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("category") category: String = "general",
        @Query("country") country: String = "in",
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = NEWS_API_KEY
    ): Call<TopHeadlinesModel?>?

    @GET("top-headlines")
    fun getTopHeadlinesForQuery(
        @Query("category") category: String = "general",
        @Query("q") query: String,
        @Query("country") country: String = "in",
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = NEWS_API_KEY
    ): Call<TopHeadlinesModel?>?

}