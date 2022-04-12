package com.steven.movieapp.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.steven.movieapp.R
import com.steven.movieapp.adapter.MovieAdapter
import com.steven.movieapp.adapter.WeeklyAdapter
import com.steven.movieapp.bean.Movie
import com.steven.movieapp.bean.Weekly
import com.steven.movieapp.repository.MovieRepository
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.viewmodel.MovieViewModelFactory
import com.steven.movieapp.widget.StatusView
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.fragment_new_movie.*
import kotlinx.android.synthetic.main.fragment_new_movie.rv_movies
import kotlinx.android.synthetic.main.fragment_new_movie.sv_state
import kotlinx.android.synthetic.main.fragment_top_movie.*

/**
 * @author: yanzhiwen
 * @create: 2022/4/12 11:27
 * @description:
 */

class NewMoviesFragment : BaseFragment(), OnItemClickListener<Movie>,
    RefreshRecyclerView.OnRefreshListener {

    private val movieViewModel: MovieViewModel by viewModels { MovieViewModelFactory(MovieRepository.getInstance()) }

    private var movies = arrayListOf<Movie>()

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(requireContext(), R.layout.movie_list_item, movies)
    }


    companion object {
        fun newInstance(): Fragment {
            return NewMoviesFragment()
        }
    }

    override fun getLayoutId() = R.layout.fragment_new_movie

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sv_state.showLoadView()
        movieViewModel.getMovieNewMovies()
        sv_state.setOnClickListener(object : StatusView.OnClickListener {
            override fun onClick() {
                sv_state.showLoadView()
            }
        })
        rv_movies.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_movies.adapter = adapter
        rv_movies.setOnRefreshListener(this)
        adapter.setOnItemClickListener(this)

        movieViewModel.movieLiveData.observe(this, Observer {
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
        movieViewModel.getMovieNewMovies()
    }
}



