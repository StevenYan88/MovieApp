package com.steven.movieapp.model

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
data class Movie(
    //评分
    val rating: Rate,
    //类型
    val genres: List<String>,
    //电影名称
    val title: String,
    //演员简介
    val casts: List<Avaters>,
    //片长
    val durations: List<String>,
    //收藏数
    val collect_count: Int,
    //大陆上映日期
    val mainland_pubdate: String,
    //原名
    val original_title: String,
    //导演
    val directors: List<Directors>,
    //上映时间
    val pubdates: List<String>,
    //上映年份
    val year: String,
    //剧照
    val images: Images,
    //影片h5链接
    val alt: String,
    //影片id
    val id: String


)