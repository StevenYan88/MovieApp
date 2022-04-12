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
import com.steven.movieapp.widget.StatusView
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.fragment_come_movie.*

/**
 * @author: yanzhiwen
 * @create: 2022/4/12 11:56
 * @description: 即将上映
 */

class ComingMovieFragment : BaseFragment(), OnItemClickListener<Movie>,
    RefreshRecyclerView.OnRefreshListener {
    companion object {
        fun newInstance(): Fragment {
            return ComingMovieFragment()
        }
    }
    private val movieViewModel: MovieViewModel by viewModels { MovieViewModelFactory(MovieRepository.getInstance()) }

    private var movies = arrayListOf<Movie>()

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(requireContext(), R.layout.movie_list_item, movies)
    }

    override fun getLayoutId(): Int = R.layout.fragment_come_movie


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sv_state.showLoadView()
        movieViewModel.getComingSoonMovie()
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

    }

    override fun onItemClick(view: View, position: Int, item: Movie) {
    }

    override fun onRefresh() {
        movieViewModel.getComingSoonMovie()
    }
}
