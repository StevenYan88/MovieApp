package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.base.BaseResultRefreshFragment

/**
 * 口碑榜
 */
class Top250MovieFragment : BaseResultRefreshFragment() {
    private var start: Int = 0
    private var count: Int = 10

    companion object {
        fun newInstance(): Fragment {
            return Top250MovieFragment()
        }
    }

    override fun onRequestData() {
        movieViewModel.getTop250Movie(start, count).observe(this, mBaseResultObserver)
    }

    override fun onLoad() {
        super.onLoad()
        start += 10
        movieViewModel.getTop250Movie(start, count).observe(this, mBaseResultObserver)
    }
}
