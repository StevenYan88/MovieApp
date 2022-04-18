package com.steven.movieapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steven.movieapp.base.support.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author: yanzhiwen
 * @create: 2021/12/15 16:48
 * @description:
 */

open class BaseViewModel : ViewModel() {
     val loadingLiveData = SingleLiveData<Boolean>()

     val errorLiveData = SingleLiveData<Throwable>()

     /**
      * 利用协程处理网络请求，处理异步任务。
      * block() 处理网络请求（Retrofit中对应的api接口方法）
      * error() 处理网络请求发生错误
      * complete() 网络请求完成之后调用
      */
     fun launch(
          block: suspend () -> Unit,
          error: suspend (Throwable) -> Unit,
          complete: suspend () -> Unit
     ) {
          loadingLiveData.postValue(true)
          viewModelScope.launch(Dispatchers.IO) {
               try {
                    block()
               } catch (e: Exception) {
                    error(e)
               } finally {
                    complete()
               }
          }
     }
}