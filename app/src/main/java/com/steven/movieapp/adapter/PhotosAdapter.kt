package com.steven.movieapp.adapter

import android.content.Context
import android.util.SparseArray
import android.widget.ImageView
import com.steven.movieapp.R
import com.steven.movieapp.bean.Photo
import com.steven.movieapp.widget.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.widget.recyclerview.BaseViewHolder

/**
 * Description: 瀑布流显示演员图片，图册
 * Data：3/8/2019-10:43 PM
 * @author yanzhiwen
 */
class PhotosAdapter(context: Context, layoutId: Int, data: List<Photo>) :
    BaseRecyclerAdapter<Photo>(context, layoutId, data) {
    private val mPhotoHeights: SparseArray<Int> = SparseArray()
    override fun convert(holder: BaseViewHolder, position: Int, item: Photo) {
        val iv = holder.getView<ImageView>(R.id.iv_actor)
        val layoutParams = iv.layoutParams
        val height: Int
        if (mPhotoHeights.indexOfValue(position) <= -1) {
            height = (360 + Math.random() * 200).toInt()
            mPhotoHeights.put(position, height)
        } else {
            height = mPhotoHeights.get(position)
        }
        layoutParams.height = height
        iv.layoutParams = layoutParams
        holder.setImage(R.id.iv_actor, item.image)
    }

}