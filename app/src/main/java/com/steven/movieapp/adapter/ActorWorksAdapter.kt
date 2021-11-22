package com.steven.movieapp.adapter

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.bean.Works
import com.steven.movieapp.widget.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.widget.recyclerview.BaseViewHolder

/**
 * Description:演员的作品
 * Data：2/21/2019-5:43 PM
 * @author yanzhiwen
 */
class ActorWorksAdapter(context: Context, layoutId: Int, data: List<Works>) :
    BaseRecyclerAdapter<Works>(context, layoutId, data) {


    override fun convert(holder: BaseViewHolder, position: Int, item: Works) {
        holder.setImage(R.id.iv_actor, item.subject.images.large)
            .setText(R.id.name, item.subject.title?:"")
            .setText(R.id.year, item.subject.year)
    }

}