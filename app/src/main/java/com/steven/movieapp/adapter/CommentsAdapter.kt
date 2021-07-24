package com.steven.movieapp.adapter

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.bean.Comment
import com.steven.movieapp.widget.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.widget.recyclerview.BaseViewHolder

/**
 * Description:评论item
 * Data：2/21/2019-5:43 PM
 * @author yanzhiwen
 */
class CommentsAdapter(context: Context, layoutId: Int, data: List<Comment>) :
    BaseRecyclerAdapter<Comment>(context, layoutId, data) {

    override fun convert(holder: BaseViewHolder, position: Int, item: Comment) {
        holder.setText(R.id.name, item.author.name)
            .setText(R.id.pubdates, item.created_at)
            .setText(R.id.useful_count, item.useful_count.toString())
            .setText(R.id.content, item.content)
            .setImage(R.id.avatar, item.author.avatar)

    }

}