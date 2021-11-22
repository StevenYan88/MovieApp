package com.steven.movieapp.bean

import com.google.gson.annotations.SerializedName

/**
 * Description:
 * Data：2/21/2019-2:24 PM
 * @author yanzhiwen
 */
data class Comment(
    @SerializedName("rating")
    val rating: Rate,
    @SerializedName("usefulCount")
    val usefulCount: Int,
    @SerializedName("author")
    val author: Author,
    @SerializedName("subject_id")
    val subjectId: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String

)