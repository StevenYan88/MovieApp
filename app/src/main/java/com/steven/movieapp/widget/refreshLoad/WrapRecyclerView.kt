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
    private var mEmptyView: View? = null
    private var mLoadingView: View? = null
    private lateinit var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private val mDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyDataSetChanged()
            }
            dataChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemRemoved(positionStart)
            }
            dataChanged()

        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemMoved(fromPosition, toPosition)
            }
            dataChanged()

        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemChanged(positionStart)
            }
            dataChanged()

        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemChanged(positionStart, payload)
            }
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemInserted(positionStart)

            }
            dataChanged()

        }
    }

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
        mAdapter.registerAdapterDataObserver(mDataObserver)
        //解决GridLayout添加头部和底部要占据一行
        mWrapRecyclerAdapter.adjustSpanSize(this)

        // 加载数据页面
        mLoadingView ?: return
        if (mLoadingView!!.visibility == View.VISIBLE) {
            mLoadingView!!.visibility = View.GONE
        }

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

    fun addLoadingView(loadView: View) {
        this.mLoadingView = loadView
        mLoadingView!!.visibility = View.VISIBLE
    }

    fun addEmptyView(emptyView: View) {
        this.mEmptyView = emptyView
    }

    /**
     * Adapter数据改变的方法
     */
    private fun dataChanged() {
        mEmptyView ?: return
        if (mAdapter.itemCount == 0) {
            // 没有数据
            mEmptyView!!.visibility = VISIBLE
        } else {
            mEmptyView!!.visibility = GONE
        }
    }
}