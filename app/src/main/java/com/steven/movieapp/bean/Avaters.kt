package com.steven.movieapp.bean

import com.google.gson.annotations.SerializedName

/**
 * Description: 演员信息
 * Data：2019/1/28
 * Actor:Steven
 */

data class Avaters(
    //演员图片
    @SerializedName("avatars")
    val avatars: Images,
    //英文名
    @SerializedName("name_en")
    val nameEn: String,
    //中文名
    @SerializedName("name")
    val name: String,
    //影人简介
    @SerializedName("alt")
    val alt: String,
    //演员id
    @SerializedName("id")
    val id: Int

)