package com.steven.movieapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Description: 用于ViewPager中的懒加载Fragment
 * Data：2019/1/28
 * Actor:Steven
 */

abstract class LazyFragment : Fragment() {
    private var isInit = false
    private var isFirstVisible = true

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isInit && isFirstVisible) {
            onRequestData()
            isInit = false
        }
    }

    abstract fun onRequestData()
    abstract fun initView()
    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        isInit = true
        if (userVisibleHint) {
            onRequestData()
            isFirstVisible = false
        }
    }
}