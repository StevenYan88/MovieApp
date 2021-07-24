package com.steven.movieapp.adapter

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.bean.Trailers
import com.steven.movieapp.widget.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.widget.recyclerview.BaseViewHolder

/**
 * Description:预告片
 * Data：2/21/2019-5:43 PM
 * @author yanzhiwen
 */
class TrailersAdapter(context: Context, layoutId: Int, data: List<Trailers>) :
    BaseRecyclerAdapter<Trailers>(context, layoutId, data) {


    override fun convert(holder: BaseViewHolder, position: Int, item: Trailers) {
        holder.setImage(R.id.iv_trailers, item.medium)
            .setText(R.id.title, item.title)
    }
}