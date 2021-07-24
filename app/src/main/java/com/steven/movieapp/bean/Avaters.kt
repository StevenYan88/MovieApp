package com.steven.movieapp.bean

/**
 * Description: 演员信息
 * Data：2019/1/28
 * Actor:Steven
 */

data class Avaters(
    //演员图片
    val avatars: Images,
    //英文名
    val name_en: String,
    //中文名
    val name: String,
    //影人简介
    val alt: String,
    //演员id
    val id: Int

)