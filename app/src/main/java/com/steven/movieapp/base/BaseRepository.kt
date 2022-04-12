package com.steven.movieapp.base

import android.util.Log
import com.steven.movieapp.http.net.BaseResp
import com.steven.movieapp.http.net.DataState
import com.steven.movieapp.http.net.ResState
import com.steven.movieapp.http.net.StateLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import java.io.IOException

/**
 * @description:
 * @create: 2021-12-15
 * @author: yanzhiwen
 */
open class BaseRepository {

    companion object {
        private const val TAG = "BaseRepository"
    }

    /**
     * 方式一
     * repo 请求数据的公共方法，
     * 在不同状态下先设置 baseResp.dataState的值，最后将dataState 的状态通知给UI
     * @param block api的请求方法
     * @param stateLiveData 每个请求传入相应的LiveData，主要负责网络状态的监听
     */
    suspend fun <T : Any> executeResp(block: suspend () -> BaseResp<T>, stateLiveData: StateLiveData<T>) {
        var baseResp = BaseResp<T>()
        try {
            baseResp.dataState = DataState.STATE_LOADING
            //开始请求数据
            val invoke = block.invoke()
            //将结果复制给baseResp
            baseResp = invoke
            if (baseResp.status == 200) {
                //请求成功，判断数据是否为空，
                //因为数据有多种类型，需要自己设置类型进行判断
                if (baseResp.data != null && baseResp.data is List<*> && (baseResp.data as List<*>).size == 0) {
                    //数据为空,结构变化时需要修改判空条件
                    baseResp.dataState = DataState.STATE_EMPTY
                } else {
                    //请求成功并且数据为空的情况下，为STATE_SUCCESS
                    baseResp.dataState = DataState.STATE_SUCCESS
                }
            } else {
                //服务器请求错误
                baseResp.dataState = DataState.STATE_FAILED
            }
        } catch (e: Exception) {
            //非后台返回错误，捕获到的异常
            baseResp.dataState = DataState.STATE_ERROR
            baseResp.error = e
        } finally {
            stateLiveData.postValue(baseResp)
            Log.d(TAG, "baseResp ${baseResp.dataState!!.name}")
        }
    }


    suspend fun <T : Any> executeResp(
        resp: BaseResp<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): ResState<T> {
        return coroutineScope {
            if (resp.status != 200) {
                successBlock?.let { it() }
                ResState.Success(resp.data!!)
            } else {
                Log.d(TAG, "executeResp: error")
                errorBlock?.let { it() }
                ResState.Error(IOException(resp.errorMsg))
            }
        }
    }
}