package com.steven.movieapp.bean

/**
 * Description:
 * Data：2/21/2019-2:27 PM
 * @author yanzhiwen
 */
data class Actor(
    //演员图片
    val avatars: Images,
    //英文名
    val name_en: String,
    //中文名
    val name: String,
    //影人简介
    val alt: String,
    //演员id
    val id: String
)