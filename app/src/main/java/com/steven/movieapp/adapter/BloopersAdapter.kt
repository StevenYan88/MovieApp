package com.steven.movieapp.adapter

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.bean.Bloopers
import com.steven.movieapp.widget.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.widget.recyclerview.BaseViewHolder

/**
 * Description:
 * Data：2/21/2019-5:43 PM
 * @author yanzhiwen
 */
class BloopersAdapter(context: Context, layoutId: Int, data: List<Bloopers>) :
    BaseRecyclerAdapter<Bloopers>(context, layoutId, data) {


    override fun convert(holder: BaseViewHolder, position: Int, item: Bloopers) {
        holder.setImage(R.id.iv_trailers, item.medium)
            .setText(R.id.title, item.title)
    }
}