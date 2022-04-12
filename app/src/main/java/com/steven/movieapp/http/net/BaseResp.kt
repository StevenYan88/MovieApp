package com.steven.movieapp.http.net

import com.google.gson.annotations.SerializedName

/**
 * @author: yanzhiwen
 * @create: 2021/12/15 16:26
 * @description:json返回的基本类型
 */

class BaseResp<T> {


    @SerializedName("code")
    var status = -1

    @SerializedName("msg")
    var errorMsg: String? = null

    @SerializedName("data")
    var data: T? = null

    var dataState: DataState? = null

    var error: Throwable? = null
}

enum class DataState {
    STATE_CREATE,//创建
    STATE_LOADING,//加载中
    STATE_SUCCESS,//成功
    STATE_COMPLETED,//完成
    STATE_EMPTY,//数据为null
    STATE_FAILED,//接口请求成功但是服务器返回error
    STATE_ERROR,//请求失败
    STATE_UNKNOWN//未知
}