package com.steven.movieapp.ui

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.steven.movieapp.R
import com.steven.movieapp.adapter.PhotosAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.bean.Photo
import com.steven.movieapp.repository.MovieRepository
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.viewmodel.MovieViewModelFactory
import com.steven.movieapp.widget.StatusView
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultLoadViewCreator
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.LoadRefreshRecyclerView
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.activity_photos.*

class PhotosActivity : BaseActivity(), RefreshRecyclerView.OnRefreshListener,
    LoadRefreshRecyclerView.OnLoadListener,
    OnItemClickListener<Photo> {

    private val movieViewModel: MovieViewModel by viewModels { MovieViewModelFactory(MovieRepository.getInstance()) }

    private val celebrityId: String by lazy {
        intent.getStringExtra("celebrity_id") ?: ""
    }
    private val name: String by lazy {
        intent.getStringExtra("name") ?: ""
    }
    private var start: Int = 0
    private var count: Int = 20

    private var photos = arrayListOf<Photo>()

    private val adapter: PhotosAdapter by lazy {
        PhotosAdapter(this, R.layout.photo_item, photos)
    }


    override fun getLayoutId(): Int = R.layout.activity_photos

    override fun initView() {
        sv.showLoadView()
        sv.setOnClickListener(object : StatusView.OnClickListener {
            override fun onClick() {
                sv.showLoadView()
                onRefresh()
            }
        })
        supportActionBar?.apply { title = name }
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        //参考博客：https://blog.csdn.net/windows771053651/article/details/51596744
        //解决RecyclerView StaggeredGridLayoutManage布局管理器滑动到顶部时仍会出现移动问题
        //RecyclerView滑动过程中不断请求layout的Request，不断调整item见的间隙，
        // 并且是在item尺寸显示前预处理，因此解决RecyclerView滑动到顶部时仍会出现移动问题
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        rv_photos.layoutManager = layoutManager
        rv_photos.addLoadViewCreator(DefaultLoadViewCreator())
        rv_photos.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_photos.setOnRefreshListener(this)
        rv_photos.setOnLoadListener(this)


    }

    override fun onRequestData() {
        movieViewModel.getCelebrityPhotos(celebrityId, start, count)
        movieViewModel.photosLiveData.observe(this, Observer {
            if (it != null) {
                showPhotos(it.photos)
            }
        })
    }


    private fun showPhotos(photos: List<Photo>) {
        if (this.photos.isEmpty()) {
            this.photos = photos as ArrayList<Photo>
            rv_photos.adapter = adapter
        } else {
            rv_photos.onStopRefresh()
            if (photos.isNotEmpty() && rv_photos.isLoading()) {
                this.photos.addAll(photos)
                rv_photos.onStopLoad()
            }
            adapter.notifyDataSetChanged()
        }
        adapter.setOnItemClickListener(this)

    }


    override fun onLoad() {
        start += 20
        movieViewModel.getCelebrityPhotos(celebrityId, start, count)
    }

    override fun onRefresh() {
        movieViewModel.getCelebrityPhotos(celebrityId, start, count)
    }

    override fun onItemClick(view: View, position: Int, item: Photo) {
        val intent = Intent(this, PreviewPhotoActivity::class.java)
        intent.putExtra("photo_url", item.image)
        intent.putExtra("name", name)
        startActivity(intent)
    }
}
