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