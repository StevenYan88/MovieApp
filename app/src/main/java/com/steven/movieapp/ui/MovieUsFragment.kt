package com.steven.movieapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.steven.movieapp.R
import com.steven.movieapp.adapter.WeeklyAdapter
import com.steven.movieapp.bean.Movie
import com.steven.movieapp.bean.Weekly
import com.steven.movieapp.repository.MovieRepository
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.viewmodel.MovieViewModelFactory
import com.steven.movieapp.widget.LoopTextView
import com.steven.movieapp.widget.StatusView
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.fragment_movie_us.*


/**
 * @author: yanzhiwen
 * @create: 2022/4/12 11:23
 * @description:北美票房榜
 *
 * */

class MovieUsFragment : BaseFragment(), OnItemClickListener<Weekly>,
    RefreshRecyclerView.OnRefreshListener {

    private val movieViewModel: MovieViewModel by viewModels { MovieViewModelFactory(MovieRepository.getInstance()) }

    private var movies = arrayListOf<Weekly>()

    private val adapter: WeeklyAdapter by lazy {
        WeeklyAdapter(requireContext(), R.layout.movie_list_item, movies)
    }
    companion object {
        fun newInstance(): Fragment {
            return MovieUsFragment()
        }
    }

    override fun getLayoutId() = R.layout.fragment_movie_us


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sv_state.showLoadView()
        movieViewModel.getUsMovieBox()
        sv_state.setOnClickListener(object : StatusView.OnClickListener {
            override fun onClick() {
                sv_state.showLoadView()
            }
        })
        rv_movies.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_movies.adapter = adapter
        rv_movies.setOnRefreshListener(this)
        adapter.setOnItemClickListener(this)

        movieViewModel.weeklyLiveData.observe(this, Observer {
            if (!it.subjects.isNullOrEmpty()) {
                sv_state.removeAllViews()
                rv_movies.onStopRefresh()
                movies.addAll(it.subjects)
                adapter.notifyDataSetChanged()
                setupLoopMovieName(it.subjects)
            } else {

            }
        })
    }

    override fun onItemClick(view: View, position: Int, item: Weekly) {
    }

    override fun onRefresh() {
        movieViewModel.getUsMovieBox()
    }


    private fun setupLoopMovieName(movies: List<Weekly>) {
        val textList = arrayListOf<String>()
        movies.forEach { textList.add(it.subject.title + " | " + it.subject.originalTitle) }
        activity?.apply {
            findViewById<LoopTextView>(R.id.loop_movie_name).setTextList(textList)
        }
    }
}