package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.base.BaseResultRefreshFragment

/**
 *   新片榜
 */
class NewMoviesFragment : BaseResultRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return NewMoviesFragment()
        }
    }

    override fun onRequestData() {
        movieViewModel.getMovieNewMovies().observe(this, observer)
    }
}
