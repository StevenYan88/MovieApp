package com.steven.movieapp.adapter

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.model.Actor
import com.steven.movieapp.widget.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.widget.recyclerview.BaseViewHolder

/**
 * Description:
 * Data：2/21/2019-5:43 PM
 * @author yanzhiwen
 */
class ActorsAdapter(context: Context, layoutId: Int, data: List<Actor>) :
    BaseRecyclerAdapter<Actor>(context, layoutId, data) {


    override fun convert(holder: BaseViewHolder, position: Int, item: Actor) {
        holder.setImage(R.id.iv_actor, item.avatars.large)
            .setText(R.id.name, item.name)
    }

}