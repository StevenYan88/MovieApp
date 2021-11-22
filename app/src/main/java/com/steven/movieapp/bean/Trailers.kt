package com.steven.movieapp.bean

import com.google.gson.annotations.SerializedName

/**
 * Description:预告片
 * Data：2/22/2019-11:37 AM
 * @author yanzhiwen
 */
data class Trailers(
    @SerializedName("medium")
    val medium: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("subject_id")
    val subjectId: String,
    @SerializedName("alt")
    val alt: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("resource_url")
    val resourceUrl: String,
    @SerializedName("id")
    val id: String
)