package com.steven.movieapp.bean

import com.google.gson.annotations.SerializedName

/**
 * Description:
 * Data：2/27/2019-3:50 PM
 * @author yanzhiwen
 */
data class Bloopers(
    @SerializedName("medium")
    val medium: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("subject_id")
    val subject_id: String,
    @SerializedName("alt")
    val alt: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("resource_url")
    val resourceUrl: String,
    @SerializedName("id")
    val id: String
)