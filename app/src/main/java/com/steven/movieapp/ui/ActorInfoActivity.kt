package com.steven.movieapp.ui

import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.steven.movieapp.R
import com.steven.movieapp.adapter.ActorPhotosAdapter
import com.steven.movieapp.adapter.ActorWorksAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.bean.ActorInfo
import com.steven.movieapp.bean.Photo
import com.steven.movieapp.bean.Works
import com.steven.movieapp.repository.MovieRepository
import com.steven.movieapp.utils.ShareUtil
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.viewmodel.MovieViewModelFactory
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import kotlinx.android.synthetic.main.activity_actor_info.*
import kotlinx.android.synthetic.main.load_view.*
import java.util.*

class ActorInfoActivity : BaseActivity() {
    private lateinit var shareText: String
    private lateinit var name: String
    private val actorId: String by lazy {
        intent.getStringExtra("actor_id") ?: ""
    }
    private val movieViewModel: MovieViewModel by viewModels { MovieViewModelFactory(MovieRepository.getInstance()) }

    override fun getLayoutId() = R.layout.activity_actor_info


    override fun initView() {
        fab.setOnClickListener {
            ShareUtil.share(this, shareText)
        }
    }

    override fun onRequestData() {
        movieViewModel.getCelebrity(actorId)
        movieViewModel.actorInfoLiveData.observe(this, Observer {
            if (it != null) {
                showActorInfo(it)
                showPhotos(it.photos)
                showActorWorks(it.works)
            }
        })
    }

    private fun showActorInfo(actor: ActorInfo) {
        load_view.visibility = View.GONE
        container.visibility = View.VISIBLE
        toolbar.title = actor.name
        this.name = actor.name
        Glide.with(this).load(actor.avatars.large).into(iv_actor)
        gender.text = String.format("性别：%s", actor.gender)
        constellation.text = String.format("星座：%s", actor.constellation)
        birthday.text = String.format("出生日期：%s", actor.birthday)
        born_place.text = String.format("出生地：%s", actor.bornPlace)
        actor_summary.text = actor.summary
        shareText = actor.summary

    }

    /***
     * 影人剧照
     * @param photos
     */
    private fun showPhotos(photos: List<Photo>) {
        rv_photos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ActorPhotosAdapter(this, R.layout.photo_image_item, photos)
        rv_photos.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener<Photo> {
            override fun onItemClick(view: View, position: Int, item: Photo) {
                val intent = Intent(this@ActorInfoActivity, PreviewPhotosActivity::class.java)
                intent.putExtra("position", position)
                intent.putParcelableArrayListExtra("photos", photos as ArrayList<out Parcelable>)
                intent.putExtra("name", this@ActorInfoActivity.name)
                intent.putExtra("summary", this@ActorInfoActivity.shareText)
                startActivity(intent)
            }
        })
        val footerView = LayoutInflater.from(this).inflate(
            R.layout.check_more_view,
            findViewById<View>(R.id.container) as ViewGroup, false
        )
        rv_photos.addFooterView(footerView)
        footerView.setOnClickListener {
            val intent = Intent(this@ActorInfoActivity, PhotosActivity::class.java)
            intent.putExtra("celebrity_id", actorId)
            intent.putExtra("name", this@ActorInfoActivity.name)
            startActivity(intent)
        }
    }

    /**
     * 最近的5部作品
     */
    private fun showActorWorks(works: List<Works>) {
        rv_works.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ActorWorksAdapter(this, R.layout.works_image_item, works)
        rv_works.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener<Works> {
            override fun onItemClick(view: View, position: Int, item: Works) {
                val intent = Intent(this@ActorInfoActivity, MovieInfoActivity::class.java)
                intent.putExtra("movie_id", item.subject.id)
                startActivity(intent)
            }
        })
    }
}
