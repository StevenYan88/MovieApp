package com.steven.movieapp.model

/**
 * Description:
 * Data：2/21/2019-2:24 PM
 * @author yanzhiwen
 */
data class Comment(
    val rating: Rate,
    val useful_count: Int,
    val author: Author,
    val subject_id: String,
    val content: String,
    val created_at: String,
    val id: String

)