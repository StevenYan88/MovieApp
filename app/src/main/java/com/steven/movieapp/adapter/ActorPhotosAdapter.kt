package com.steven.movieapp.adapter

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.bean.Photo
import com.steven.movieapp.widget.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.widget.recyclerview.BaseViewHolder

/**
 * Description: 演员图片，图册
 * Data：2/21/2019-5:43 PM
 * @author yanzhiwen
 */
class ActorPhotosAdapter(context: Context, layoutId: Int, data: List<Photo>) :
    BaseRecyclerAdapter<Photo>(context, layoutId, data) {


    override fun convert(holder: BaseViewHolder, position: Int, item: Photo) {
        holder.setImage(R.id.iv_actor, item.image)
    }

}