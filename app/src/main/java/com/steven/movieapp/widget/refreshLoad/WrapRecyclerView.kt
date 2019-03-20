package com.steven.movieapp.widget.refreshLoad

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Description:
 * Data：2019/2/20
 * Actor:Steven
 */

open class WrapRecyclerView : RecyclerView {

    private lateinit var mWrapRecyclerAdapter: WrapRecyclerAdapter
    private lateinit var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    override fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) {

        this.mAdapter = adapter!!
        mWrapRecyclerAdapter = if (adapter is WrapRecyclerAdapter) {
            adapter
        } else {
            WrapRecyclerAdapter(adapter)
        }
        super.setAdapter(mWrapRecyclerAdapter)
        //解决GridLayout添加头部和底部要占据一行
        mWrapRecyclerAdapter.adjustSpanSize(this)

    }

    fun addHeaderView(view: View) {
        mWrapRecyclerAdapter.addHeaderView(view)
    }

    fun addFooterView(view: View) {
        mWrapRecyclerAdapter.addFooterView(view)
    }

    fun removeHeaderView(view: View) {
        mWrapRecyclerAdapter.removeHeaderView(view)
    }

    fun removeFooterView(view: View) {
        mWrapRecyclerAdapter.removeFooterView(view)
    }
}