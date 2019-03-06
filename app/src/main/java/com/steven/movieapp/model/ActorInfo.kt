package com.steven.movieapp.model

/**
 * Description:
 * Data：2019/2/26
 * Author:Steven
 */
data class ActorInfo(
    val name: String,
    val name_en: String,
    val gender: String,
    //最受好评的5部作品
    val works: List<Works>,
    val professions: List<String>,
    val summary: String,
    val avatars: Images,
    //影人图片
    val photos: List<Photo>,
    //出生日期
    val birthday: String,
    //出生地
    val born_place: String,
    //星座
    val constellation: String,
    //影人id
    val id: String


)