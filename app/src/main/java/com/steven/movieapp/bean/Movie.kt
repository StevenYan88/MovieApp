package com.steven.movieapp.bean

import com.google.gson.annotations.SerializedName

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
data class Movie(
    //评分
    @SerializedName("rating")
    val rating: Rate,
    //类型
    @SerializedName("genres")
    val genres: List<String>,
    //电影名称
    @SerializedName("title")
    val title: String?,
    //演员简介
    @SerializedName("casts")
    val casts: List<Avaters>,
    //片长
    @SerializedName("durations")
    val durations: List<String>,
    //收藏数
    @SerializedName("collect_count")
    val collectCount: Int,
    //大陆上映日期
    @SerializedName("mainland_pubdate")
    val mainlandPubdate: String,
    //原名
    @SerializedName("original_title")
    val originalTitle: String,
    //导演
    @SerializedName("directors")
    val directors: List<Directors>,
    //上映时间
    @SerializedName("pubdates")
    val pubdates: List<String>,
    //上映年份
    @SerializedName("year")
    val year: String,
    //剧照
    @SerializedName("images")
    val images: Images,
    //影片h5链接
    @SerializedName("alt")
    val alt: String,
    //影片id
    @SerializedName("id")
    val id: String
)