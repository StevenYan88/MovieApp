package com.steven.movieapp.repository

import com.steven.movieapp.bean.BaseResult

/**
 * @description:
 * @create: 2021-07-24
 * @author: yanzhiwen
 */
interface OnApiCallback{
    fun <T> onSuccess(body: BaseResult<List<T>>)

    fun onFail(error:String)
}
class MoiveRepository {

    fun getInTheaters(apiCallBack: OnApiCallback){

    }

    companion object {
        @Volatile
        private var instance: MoiveRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MoiveRepository().also { instance = it }
            }
    }
}