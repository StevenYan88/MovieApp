package com.steven.movieapp.ui

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.steven.movieapp.R
import com.steven.movieapp.adapter.ActorsAdapter
import com.steven.movieapp.adapter.BloopersAdapter
import com.steven.movieapp.adapter.CommentsAdapter
import com.steven.movieapp.adapter.TrailersAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.model.*
import com.steven.movieapp.utils.ShareUtil
import com.steven.movieapp.utils.StringFormat
import com.steven.movieapp.widget.recyclerview.DividerItemDecoration
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import kotlinx.android.synthetic.main.activity_movie_info.*
import kotlinx.android.synthetic.main.load_view.*

/**
 * Description:
 * Data：2/21/2019-1:53 PM
 * @author yanzhiwen
 */
class MovieInfoActivity : BaseActivity() {

    private lateinit var shareText: String
    private val movieId: String by lazy {
        intent.getStringExtra("movie_id")
    }

    override fun getLayoutId(): Int = R.layout.activity_movie_info

    override fun initView() {
        fab.setOnClickListener {
            ShareUtil.share(this, shareText)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv_movie.transitionName = getString(R.string.transition_movie_image)
            window.enterTransition = android.transition.Fade()
            window.exitTransition = android.transition.Fade()
        }
    }

    override fun onRequestData() {
        movieViewModel.getMovieInfo(movieId).observe(this, Observer {
            showMovieInfo(it)
            showActors(it.casts)
            showMovieTrailers(it.trailers)
            showBloopers(it.bloopers)
            showMovieComments(it.popular_comments)
        })

    }

    /**
     * 电影信息
     *
     * @param movieInfo 电影信息
     *
     */
    private fun showMovieInfo(movieInfo: MovieInfo) {
        load_view.visibility = View.GONE
        container.visibility = View.VISIBLE
        Glide.with(this).load(movieInfo.images.large).into(iv_movie)
        toolbar.title = movieInfo.title
        shareText = movieInfo.title
        rating.text = String.format("评分：%s", movieInfo.rating.average)
        rating_bar.rating = ((movieInfo.rating.average / 2).toFloat())
        director.text = String.format("导演：%s", StringFormat.formatName(movieInfo.directors))
        actor.text = String.format("演员：%s", StringFormat.formatName(movieInfo.casts))
        genres.text = String.format("类型：%s", StringFormat.formatGenres(movieInfo.genres))
        pubdates.text = String.format("上映日期：%s", movieInfo.year)
        country.text = String.format("制片国家/地区：%s", StringFormat.formatCountry(movieInfo.countries))
        movie_summary.text = movieInfo.summary
    }

    /**
     * 演员图片
     *
     * @param actors 演员图片
     *
     */
    private fun showActors(actors: List<Actor>) {
        rv_actors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ActorsAdapter(this, R.layout.actor_image_item, actors)
        rv_actors.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener<Actor> {
            override fun onItemClick(view: View, position: Int, item: Actor) {
                val intent = Intent(this@MovieInfoActivity, ActorInfoActivity::class.java)
                intent.putExtra("actor_id", item.id)
                startActivity(intent)
            }
        })
    }

    /**
     * 花絮
     * @param bloopers 花絮
     *
     */
    private fun showBloopers(bloopers: List<Bloopers>) {
        rv_bloopers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = BloopersAdapter(this, R.layout.video_image_item, bloopers)
        rv_bloopers.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener<Bloopers> {
            override fun onItemClick(view: View, position: Int, item: Bloopers) {
                val intent = Intent(this@MovieInfoActivity, PlayVideoActivity::class.java)
                intent.putExtra("video_url", item.resource_url)
                intent.putExtra("title", item.title)
                startActivity(intent)
            }
        })
    }

    /**
     * 预告片
     *
     * @param trailers 预告片
     *
     */
    private fun showMovieTrailers(trailers: List<Trailers>) {
        rv_trailers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = TrailersAdapter(this, R.layout.video_image_item, trailers)
        rv_trailers.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener<Trailers> {
            override fun onItemClick(view: View, position: Int, item: Trailers) {
                val intent = Intent(this@MovieInfoActivity, PlayVideoActivity::class.java)
                intent.putExtra("video_url", item.resource_url)
                intent.putExtra("title", item.title)
                startActivity(intent)
            }
        })
    }

    /**
     * 评论
     *
     * @param popular_comments 评论
     *
     */
    private fun showMovieComments(popular_comments: List<Comment>) {
        rv_comments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_comments.adapter = CommentsAdapter(this, R.layout.comment_item, popular_comments)
        rv_comments.addItemDecoration(
            DividerItemDecoration(
                this,
                R.drawable.ic_divider_item,
                LinearLayoutManager.VERTICAL
            )
        )
        val moreCommentsView = LayoutInflater.from(this).inflate(
            R.layout.check_more_comments,
            findViewById(R.id.container), false
        )
        rv_comments.addFooterView(moreCommentsView)
        moreCommentsView.findViewById<TextView>(R.id.comments_more).setOnClickListener {
            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra("movie_id", movieId)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        fab.visibility = View.INVISIBLE
        super.onBackPressed()
    }
}