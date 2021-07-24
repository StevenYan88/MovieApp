package com.steven.movieapp.adapter

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.bean.Movie
import com.steven.movieapp.utils.StringFormat
import com.steven.movieapp.widget.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.widget.recyclerview.BaseViewHolder

/**
 * Description:
 * Data：1/29/2019-2:20 PM
 * @author yanzhiwen
 *
 */
class MovieAdapter(context: Context, layoutId: Int, data: List<Movie>) :
    BaseRecyclerAdapter<Movie>(context, layoutId, data) {

    override fun convert(holder: BaseViewHolder, position: Int, item: Movie) {
        holder.setText(R.id.name, item.title)
            .setText(R.id.genres, String.format("类型：%s", StringFormat.formatGenres(item.genres)))
            .setText(R.id.pubdates, String.format("上映日期：%s", item.mainland_pubdate))
            .setText(R.id.durations, String.format("片长：%s", StringFormat.formatDurations(item.durations)))
            .setImage(R.id.iv_movie, item.images.large)
    }
}