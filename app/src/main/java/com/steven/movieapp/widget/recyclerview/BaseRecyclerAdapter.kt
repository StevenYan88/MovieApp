package com.steven.movieapp.widget.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder> {

    //点击事件
    private var mOnItemClickListener: OnItemClickListener<T>? = null
    //支持多种布局
    private var mMultiTypeSupport: MultiTypeSupport<T>? = null
    //mInflater
    private var mInflater: LayoutInflater
    private var context: Context
    private var layoutId: Int
    private var data: List<T>

    constructor(context: Context, layoutId: Int, data: List<T>) {
        this.context = context
        this.layoutId = layoutId
        this.data = data
        this.mInflater = LayoutInflater.from(context)
    }


    constructor(context: Context, data: List<T>, multiTypeSupport: MultiTypeSupport<T>)
            : this(context, -1, data) {
        this.mMultiTypeSupport = multiTypeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        mMultiTypeSupport?.also {
            this.layoutId = viewType
        }
        val itemView = mInflater.inflate(layoutId, parent, false)
        return BaseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener {
                mOnItemClickListener?.apply { onItemClick(holder.itemView,position, data[position]) }
            }
        }

        convert(holder, position, data[position])
    }


    override fun getItemViewType(position: Int): Int {
        //多布局
        return if (mMultiTypeSupport != null) {
            mMultiTypeSupport!!.getLayoutId(data[position], position)
        } else super.getItemViewType(position)
    }

    abstract fun convert(holder: BaseViewHolder, position: Int, item: T)

    /**
     * 设置itemView的点击事件
     */
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.mOnItemClickListener = onItemClickListener
    }

    fun setLayoutId(layoutId: Int) {
        this.layoutId = layoutId
    }


}


