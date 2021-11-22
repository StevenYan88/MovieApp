package com.steven.movieapp.bean

import com.google.gson.annotations.SerializedName

/**
 * Description:
 * Data：2/21/2019-2:15 PM
 * @author yanzhiwen
 */
data class MovieInfo(
    //评分
    @SerializedName("rating")
    val rating: Rate,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("year")
    val year: String,
    //图片链接
    @SerializedName("images")
    var images: Images,
    //剧情简介
    @SerializedName("summary")
    val summary: String,
    //评论
    @SerializedName("popular_comments")
    val popularComments: List<Comment>,
    @SerializedName("id")
    val id: String,
    //影片名
    @SerializedName("title")
    val title: String,
    @SerializedName("pubdates")
    //发布日期
    val pubdates: List<String>,
    @SerializedName("tags")
    val tags: List<String>,
    //类型
    @SerializedName("genres")
    val genres: List<String>,
    //片长
    @SerializedName("durations")
    val durations: List<String>,
    //制片国家
    @SerializedName("countries")
    val countries: List<String>,
    //演员
    @SerializedName("casts")
    val casts: List<Actor>,
    //导演
    @SerializedName("directors")
    val directors: List<Actor>,
    //预告片
    @SerializedName("trailers")
    val trailers: List<Trailers>,
    //预告片
    @SerializedName("bloopers")
    val bloopers: List<Bloopers>


)