package com.steven.movieapp.widget.refreshLoad

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Description:
 * Data：2019/2/20
 * Actor:Steven
 */

class WrapRecyclerAdapter(private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mHeaderViews: SparseArray<View> = SparseArray()

    private val mFooterViews: SparseArray<View> = SparseArray()


    companion object {
        private var BASE_ITEM_TYPE_HEADER: Int = 100
        private var BASE_ITEM_TYPE_FOOTER: Int = 200
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (isHeaderViewType(viewType)) {

            val headerView = mHeaderViews.get(viewType)
            return createHeaderFooterViewHolder(headerView)
        }
        if (isFooterViewType(viewType)) {
            val footerView = mFooterViews.get(viewType)
            return createHeaderFooterViewHolder(footerView)
        }
        return adapter.onCreateViewHolder(parent, viewType)
    }


    override fun getItemCount(): Int = adapter.itemCount + mHeaderViews.size() + mFooterViews.size()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return
        }
        val adapterPosition = position - mHeaderViews.size()
        adapter.onBindViewHolder(holder, adapterPosition)
    }

    override fun getItemViewType(position: Int): Int {
        if (isHeaderPosition(position)) {
            return mHeaderViews.keyAt(position)
        }
        if (isFooterPosition(position)) {
            val index = position - mHeaderViews.size() - adapter.itemCount
            return mFooterViews.keyAt(index)
        }
        val index = position - mHeaderViews.size()

        return adapter.getItemViewType(index)
    }

    private fun createHeaderFooterViewHolder(view: View): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(view) {
        }
    }

    private fun isHeaderViewType(viewType: Int): Boolean = mHeaderViews.indexOfKey(viewType) >= 0

    private fun isFooterViewType(viewType: Int): Boolean = mFooterViews.indexOfKey(viewType) >= 0

    private fun isHeaderPosition(position: Int): Boolean = position < mHeaderViews.size()

    private fun isFooterPosition(position: Int): Boolean =
        position >= mHeaderViews.size() + adapter.itemCount


    fun addHeaderView(view: View?) {
        if (view == null) return
        val position = mHeaderViews.indexOfValue(view)
        if (position < 0) {
            mHeaderViews.put(BASE_ITEM_TYPE_HEADER++, view)
        }
        notifyDataSetChanged()
    }

    fun addFooterView(view: View) {
        val position = mFooterViews.indexOfValue(view)
        if (position < 0) {
            mFooterViews.put(BASE_ITEM_TYPE_FOOTER++, view)
        }
        notifyDataSetChanged()
    }

    fun removeHeaderView(view: View) {
        val index = mHeaderViews.indexOfValue(view)
        if (index < 0) {
            return
        }
        mHeaderViews.removeAt(index)
        notifyDataSetChanged()
    }

    fun removeFooterView(view: View) {
        val index = mFooterViews.indexOfValue(view)
        if (index < 0) {
            return
        }
        mFooterViews.removeAt(index)
        notifyDataSetChanged()
    }

    /**
     * 解决GridLayoutManager header和footer显示整行
     */
    fun adjustSpanSize(recyclerView: RecyclerView) {
        if (recyclerView.layoutManager is GridLayoutManager) {
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val isHeaderOrFooter: Boolean =
                        isHeaderPosition(position) || isFooterPosition(position)
                    return if (isHeaderOrFooter) layoutManager.spanCount else 1
                }
            }
        }
    }

    /**
     * 当RecyclerView在windows活动时获取StaggeredGridLayoutManager布局管理器，修正header和footer显示整行
     */
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutParams = holder.itemView.layoutParams
        if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            if (isHeaderPosition(holder.layoutPosition) || isFooterPosition(holder.layoutPosition))
                layoutParams.isFullSpan = true
        }

    }

}