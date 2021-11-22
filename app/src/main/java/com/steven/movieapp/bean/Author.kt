package com.steven.movieapp.bean

import com.google.gson.annotations.SerializedName

/**
 * Description:
 * Data：2/21/2019-5:39 PM
 * @author yanzhiwen
 */
data class Author(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("signature")
    val signature: String,
    @SerializedName("alt")
    val alt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)