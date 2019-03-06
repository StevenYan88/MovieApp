package com.steven.movieapp.widget.recyclerview

import android.view.View

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
interface OnItemClickListener<T> {
    fun onItemClick(view: View, position: Int, item: T)
}