package com.abhishek.news.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhishek.news.utils.ClassUtility
import com.abhishek.news.utils.DateUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Abhishek Garg on 16/05/20 - https://www.linkedin.com/in/abhishekgarg727/

 */


@Keep
@Entity(tableName = "Article")
class Article : Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    var id: Long? = null

    @Embedded(prefix = "source")
    @SerializedName("source")
    @Expose
    private var source: Source? = null

    @SerializedName("author")
    @Expose
    private var author: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("url")
    @Expose
    private var url: String? = null

    @SerializedName("urlToImage")
    @Expose
    private var urlToImage: String? = null

    @SerializedName("publishedAt")
    @Expose
    private var publishedAt: String? = null

    @SerializedName("content")
    @Expose
    private var content: String? = null

    protected constructor(`in`: Parcel) {
        source = `in`.readValue(Source::class.java.getClassLoader()) as Source?
        author =
            `in`.readValue(String::class.java.classLoader) as String?
        title =
            `in`.readValue(String::class.java.classLoader) as String?
        description =
            `in`.readValue(String::class.java.classLoader) as String?
        url = `in`.readValue(String::class.java.classLoader) as String?
        urlToImage =
            `in`.readValue(String::class.java.classLoader) as String?
        publishedAt =
            `in`.readValue(String::class.java.classLoader) as String?
        content =
            `in`.readValue(String::class.java.classLoader) as String?
    }

    constructor() {}

    fun getSource(): Source {
        return source ?: Source()
    }

    fun setSource(source: Source?) {
        this.source = source
    }

    fun getAuthor(): String {
        return author ?: getSource().getName()
    }

    fun setAuthor(author: String?) {
        this.author = author
    }

    fun getTitle(): String {
        return title ?: ""
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getDescription(): String {
        return description ?: ""
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getUrl(): String {
        return url ?: ""
    }

    fun setUrl(url: String?) {
        this.url = url
    }

    fun getUrlToImage(): String {
        return urlToImage ?: ClassUtility.getRandomImageUrl()
    }

    fun setUrlToImage(urlToImage: String?) {
        this.urlToImage = urlToImage
    }

    fun getPublishedAt(): String {
        return DateUtils.getDateInMMMddEEEUsingZFormatTime(publishedAt)
    }

    fun setPublishedAt(publishedAt: String?) {
        this.publishedAt = publishedAt
    }

    fun getContent(): String {
        return content ?: getDescription()
    }

    fun setContent(content: String?) {
        this.content = content
    }



    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(source)
        dest.writeValue(author)
        dest.writeValue(title)
        dest.writeValue(description)
        dest.writeValue(url)
        dest.writeValue(urlToImage)
        dest.writeValue(publishedAt)
        dest.writeValue(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Article?> = object : Parcelable.Creator<Article?> {
            override fun createFromParcel(`in`: Parcel): Article? {
                return Article(`in`)
            }

            override fun newArray(size: Int): Array<Article?> {
                return arrayOfNulls<Article>(size)
            }
        }
        @JvmStatic
        @BindingAdapter("articleImage")
        fun loadImage(view: ImageView, imageUrl: String?) {
            ClassUtility.loadImageFromUrl(imageUrl, view, view.context)
        }
    }
}