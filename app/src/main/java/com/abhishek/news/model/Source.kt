package com.abhishek.news.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Abhishek Garg on 16/05/20 - https://www.linkedin.com/in/abhishekgarg727/

 */

@Entity
class Source : Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "source_id")
    var sourceId: Long? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    protected constructor(`in`: Parcel) {
        id = `in`.readValue(String::class.java.classLoader) as String?
        name = `in`.readValue(String::class.java.classLoader) as String?
    }

    constructor() {}

    fun getId(): String {
        return id ?: ""
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getName(): String {
        return name ?: ""
    }

    fun setName(name: String?) {
        this.name = name
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(id)
        dest.writeValue(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Source?> = object : Parcelable.Creator<Source?> {
            override fun createFromParcel(`in`: Parcel): Source? {
                return Source(`in`)
            }

            override fun newArray(size: Int): Array<Source?> {
                return arrayOfNulls(size)
            }
        }
        private const val serialVersionUID = 4081062702749010171L
    }
}