package com.steven.movieapp.model

/**
 * Description: 评分
 * Data：2019/1/28
 * Actor:Steven
 */
data class Rate(
    //评分最大值
    val max: Int,
    //平均值
    val average: Double,
    //期待指数
    val stars: Int,
    //评分最小值
    val min: Int
)