package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.base.BaseSubjectsRefreshFragment

/**
 * 口碑榜
 */
class WeeklyMovieFragment : BaseSubjectsRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return WeeklyMovieFragment()
        }
    }

    override fun onRequestData() {
        movieViewModel.getMovieWeekly().observe(this, mObserver)

    }
}
