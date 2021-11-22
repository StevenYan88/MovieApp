package com.steven.movieapp.bean

import com.google.gson.annotations.SerializedName

/**
 * Description:
 * Data：2019/2/26
 * Author:Steven
 */
data class ActorInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("name_en")
    val nameEn: String,
    @SerializedName("gender")
    val gender: String,
    //最受好评的5部作品
    @SerializedName("works")
    val works: List<Works>,
    @SerializedName("professions")
    val professions: List<String>,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("avatars")
    val avatars: Images,
    //影人图片
    @SerializedName("photos")
    val photos: List<Photo>,
    //出生日期
    @SerializedName("birthday")
    val birthday: String,
    //出生地
    @SerializedName("born_place")
    val bornPlace: String,
    //星座
    @SerializedName("constellation")
    val constellation: String,
    //影人id
    @SerializedName("id")
    val id: String


)