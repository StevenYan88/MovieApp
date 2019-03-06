package com.steven.movieapp.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Description:
 * Data：2019/2/26
 * Author:Steven
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class Photo(
    val thumb: String,
    val image: String,
    val cover: String,
    val alt: String,
    val id: String,
    val icon: String
) : Parcelable {
    constructor(source: Parcel) : this(

        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(thumb)
        writeString(image)
        writeString(cover)
        writeString(alt)
        writeString(id)
        writeString(icon)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Photo> = object : Parcelable.Creator<Photo> {
            override fun createFromParcel(source: Parcel): Photo = Photo(source)
            override fun newArray(size: Int): Array<Photo?> = arrayOfNulls(size)
        }
    }
}