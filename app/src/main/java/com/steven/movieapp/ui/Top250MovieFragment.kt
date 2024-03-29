package com.steven.movieapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.steven.movieapp.R
import com.steven.movieapp.adapter.MovieAdapter
import com.steven.movieapp.bean.Movie
import com.steven.movieapp.repository.MovieRepository
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.viewmodel.MovieViewModelFactory
import com.steven.movieapp.widget.LoopTextView
import com.steven.movieapp.widget.StatusView
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultLoadViewCreator
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.LoadRefreshRecyclerView
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.fragment_thread_movie.*
import kotlinx.android.synthetic.main.fragment_top_movie.*
import kotlinx.android.synthetic.main.fragment_top_movie.rv_movies
import kotlinx.android.synthetic.main.fragment_top_movie.sv_state

/**
 * @author: yanzhiwen
 * @create: 2022/4/12 11:45
 * @description: 电影排行top250
 */

class Top250MovieFragment : BaseFragment(), OnItemClickListener<Movie>,
    RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadListener {

    private var start: Int = 0
    private var count: Int = 10

    private val movieViewModel: MovieViewModel by viewModels { MovieViewModelFactory(MovieRepository.getInstance()) }

    private var movies = arrayListOf<Movie>()

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(requireContext(), R.layout.movie_list_item, movies)
    }

    companion object {
        fun newInstance(): Fragment {
            return Top250MovieFragment()
        }
    }

    override fun getLayoutId() = R.layout.fragment_top_movie
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sv_state.showLoadView()
        movieViewModel.getTop250Movie(start, count)
        sv_state.setOnClickListener(object : StatusView.OnClickListener {
            override fun onClick() {
                sv_state.showLoadView()
            }
        })
        rv_movies.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_movies.addLoadViewCreator(DefaultLoadViewCreator())
        rv_movies.adapter = adapter
        rv_movies.setOnRefreshListener(this)
        adapter.setOnItemClickListener(this)

        movieViewModel.topMovieLiveData.observe(this, Observer {
            if (!it.subjects.isNullOrEmpty()) {
                sv_state.removeAllViews()
                rv_movies.onStopRefresh()
                movies.addAll(it.subjects)
                adapter.notifyDataSetChanged()
            } else {

            }
        })

        movieViewModel.errorLiveData.observe(this, Observer {
            sv_state.showErrorView()
        })
    }

    override fun onItemClick(view: View, position: Int, item: Movie) {
    }

    override fun onRefresh() {
        movieViewModel.getTop250Movie(0, 10)
    }

    override fun onLoad() {
        start += 10
    }
}
