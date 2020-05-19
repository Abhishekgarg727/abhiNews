package com.abhishek.news.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Abhishek Garg on 16/05/20 - https://www.linkedin.com/in/abhishekgarg727/

 */


class TopHeadlinesModel : Serializable, Parcelable {
    @SerializedName("status")
    @Expose
    private var status: String = ""

    @SerializedName("totalResults")
    @Expose
    private var totalResults: Int = 0

    @SerializedName("articles")
    @Expose
    private var articles: List<Article> = arrayListOf()

    protected constructor(`in`: Parcel) {
        status =
            `in`.readValue(String::class.java.classLoader) as String
        totalResults =
            `in`.readValue(Int::class.javaPrimitiveType!!.classLoader) as Int
        `in`.readList(articles, Article::class.java.classLoader)
    }

    constructor() {}

    fun getStatus(): String {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getTotalResults(): Int {
        return totalResults ?: 0
    }

    fun setTotalResults(totalResults: Int) {
        this.totalResults = totalResults
    }

    fun getArticles(): List<Article> {
        return articles
    }

    fun setArticles(articles: List<Article>) {
        this.articles = articles
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(status)
        dest.writeValue(totalResults)
        dest.writeList(articles)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<TopHeadlinesModel?> =
            object : Parcelable.Creator<TopHeadlinesModel?> {
                override fun createFromParcel(`in`: Parcel): TopHeadlinesModel? {
                    return TopHeadlinesModel(`in`)
                }

                override fun newArray(size: Int): Array<TopHeadlinesModel?> {
                    return arrayOfNulls(size)
                }
            }
        private const val serialVersionUID = 2386978497844015612L
    }
}