package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.base.BaseSubjectsRefreshFragment

/**
 *  北美票房榜
 */
class UsMovieFragment : BaseSubjectsRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return UsMovieFragment()
        }
    }

    override fun onRequestData() {
        movieViewModel.getMovieUsBox().observe(this, mBaseSubjectsObserver)
    }
}
