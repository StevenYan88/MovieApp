package com.steven.movieapp.http.net

import com.steven.movieapp.base.support.SingleLiveData


/**
 * @author: yanzhiwen
 * @create: 2021/12/15 16:30
 * @description: MutableLiveData,用于将请求状态分发给UI
 */

class StateLiveData<T> : SingleLiveData<BaseResp<T>>()