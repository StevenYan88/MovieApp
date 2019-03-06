package com.steven.movieapp.model

/**
 * Description:
 * Data：2/21/2019-2:15 PM
 * @author yanzhiwen
 */
data class MovieInfo(
    val rating: Rate,
    val original_title: String,
    val year: String,
    var images: Images,
    //剧情简介
    val summary: String,
    //评论
    val popular_comments: List<Comment>,
    val id: String,
    val title: String,
    val pubdates: List<String>,
    val tags: List<String>,
    //类型
    val genres: List<String>,
    //片长
    val durations: List<String>,
    //制片国家
    val countries: List<String>,
    //演员
    val casts: List<Actor>,
    //导演
    val directors: List<Actor>,
    //预告片
    val trailers: List<Trailers>,
    //预告片
    val bloopers: List<Bloopers>


)