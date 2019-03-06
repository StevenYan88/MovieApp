package com.steven.movieapp.adapter

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.model.Weekly
import com.steven.movieapp.utils.StringFormat
import com.steven.movieapp.widget.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.widget.recyclerview.BaseViewHolder

/**
 * Description:口碑
 * Data：1/29/2019-2:20 PM
 * @author yanzhiwen
 *
 */
class WeeklyAdapter(context: Context, layoutId: Int, data: List<Weekly>) :
    BaseRecyclerAdapter<Weekly>(context, layoutId, data) {

    override fun convert(holder: BaseViewHolder, position: Int, item: Weekly) {
        holder.setText(R.id.name, item.subject.title)
            .setText(R.id.genres, String.format("类型：%s", StringFormat.formatGenres(item.subject.genres)))
            .setText(R.id.pubdates, String.format("上映日期：%s", item.subject.mainland_pubdate))
            .setText(R.id.durations, String.format("片长：%s", StringFormat.formatDurations(item.subject.durations)))
            .setImage(R.id.iv_movie, item.subject.images.large)
    }
}