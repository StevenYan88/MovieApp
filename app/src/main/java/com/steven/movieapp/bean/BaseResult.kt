package com.steven.movieapp.bean

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
data class BaseResult<T>(
    val count: Int,
    val start: Int,
    val total: Int,
    val subjects: T?
)