package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.base.BaseResultRefreshFragment

/**
 * 即将上映
 */
class ComingMovieFragment : BaseResultRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return ComingMovieFragment()
        }
    }

    override fun onRequestData() {
        movieViewModel.getComingSoon().observe(this, observer)
    }
}
