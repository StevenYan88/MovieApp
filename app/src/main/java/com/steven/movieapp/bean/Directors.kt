package com.steven.movieapp.bean

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
data class Directors(
    val avatars: Avaters,
    //英文名
    val name_en: String,
    //中文名
    val name: String,
    //影人简介
    val alt: String,
    //演员id
    val id: Int

)
