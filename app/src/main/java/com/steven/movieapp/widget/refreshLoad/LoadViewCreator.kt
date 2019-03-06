package com.steven.movieapp.widget.refreshLoad

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Description: 下拉刷新的辅助类，为了匹配所有的效果
 * Data：2019/2/20
 * Actor:Steven
 */
open abstract class LoadViewCreator {


    /**
     * 获取上拉刷新的View
     *
     * @param context Context
     * @param parent recyclerView
     *
     */
    abstract fun getLoadView(context: Context, parent: ViewGroup): View


    /**
     * 正在下拉
     *
     * @param currentDragHeight  当前拖动的高度
     * @param refreshHeight  总的刷新高度
     * @param currentRefreshStatus 当前状态
     *
     */
    abstract fun onPull(currentDragHeight: Int, refreshHeight: Int, currentRefreshStatus: Int)


    /**
     * 正在加载
     */
    abstract fun onLoading()


    /**
     * 停止加载
     */
    abstract fun onStopLoad()


}