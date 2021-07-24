package com.steven.movieapp.base

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.steven.movieapp.R
import com.steven.movieapp.adapter.WeeklyAdapter
import com.steven.movieapp.bean.BaseSubjects
import com.steven.movieapp.bean.Weekly
import com.steven.movieapp.ui.MovieInfoActivity
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.viewmodel.MovieViewModelFactory
import com.steven.movieapp.widget.StatusView
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.fragment_base_refresh.*

/**
 * Description:把这个抽离出来是因为后台返回的json数据格式不一致
 * Data：2/19/2019-3:14 PM
 * @author yanzhiwen
 */
abstract class BaseSubjectsRefreshFragment : LazyFragment(), OnItemClickListener<Weekly>,
        RefreshRecyclerView.OnRefreshListener {

    private var movies = arrayListOf<Weekly>()

    private val adapter: WeeklyAdapter by lazy {
        WeeklyAdapter(requireContext(), R.layout.movie_list_item, movies)
    }
    protected val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, MovieViewModelFactory()).get(MovieViewModel::class.java)
    }

    protected val mObserver: Observer<BaseSubjects<Weekly>> by lazy {
        Observer<BaseSubjects<Weekly>> {
            if (it == null && movies.isEmpty()) {
                sv.showErrorView()
                return@Observer
            }
            sv.removeAllViews()
            if (movies.isEmpty()) {
                this.movies = it.subjects as ArrayList<Weekly>
                rv_movies.adapter = adapter
            } else {
                rv_movies.onStopRefresh()
                adapter.notifyDataSetChanged()
            }
            adapter.setOnItemClickListener(this)
        }
    }

    override fun getLayoutId() = R.layout.fragment_base_refresh

    override fun initView() {
        sv.showLoadView()
        sv.setOnClickListener(object : StatusView.OnClickListener {
            override fun onClick() {
                sv.showLoadView()
                onRefresh()
            }
        })
        rv_movies.itemAnimator = DefaultItemAnimator()
        rv_movies.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_movies.setOnRefreshListener(this)
    }


    override fun onItemClick(view: View, position: Int, item: Weekly) {
        val intent = Intent(context, MovieInfoActivity::class.java)
        intent.putExtra("movie_id", item.subject.id)
        val v = view.findViewById<ImageView>(R.id.iv_movie)
        val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        requireActivity(),
                        v, getString(R.string.transition_movie_image)
                )
        startActivity(intent, options.toBundle())
    }

    override fun onRefresh() {
        onRequestData()
    }
}