package com.steven.movieapp.base

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.howshea.basemodule.component.fragment.LazyFragment
import com.steven.movieapp.R
import com.steven.movieapp.adapter.WeeklyAdapter
import com.steven.movieapp.model.BaseSubjects
import com.steven.movieapp.model.Weekly
import com.steven.movieapp.ui.MovieInfoActivity
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.viewmodel.MovieViewModelFactory
import com.steven.movieapp.widget.LoopTextView
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.fragment_base_refresh.*
import kotlinx.android.synthetic.main.load_view.*

/**
 * Description:
 * Data：2/19/2019-3:14 PM
 * @author yanzhiwen
 */
abstract class BaseSubjectsRefreshFragment : LazyFragment(), OnItemClickListener<Weekly>,
        RefreshRecyclerView.OnRefreshListener {

    private var movies = arrayListOf<Weekly>()

    private val adapter: WeeklyAdapter by lazy {
        WeeklyAdapter(activity!!, R.layout.movie_list_item, movies)
    }
    protected val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, MovieViewModelFactory()).get(MovieViewModel::class.java)

    }

    protected val mBaseSubjectsObserver: Observer<BaseSubjects<Weekly>> by lazy {
        Observer<BaseSubjects<Weekly>> {
            it ?: return@Observer
            if (movies.isEmpty()) {
                this.movies = it.subjects as ArrayList<Weekly>
                rv_movies.adapter = adapter
            } else {
                rv_movies.onStopRefresh()
                adapter.notifyDataSetChanged()
            }
            adapter.setOnItemClickListener(this)
            setupLoopMovieName(it.subjects)
        }
    }

    override fun getLayoutId() = R.layout.fragment_base_refresh

    override fun initView() {
        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.itemAnimator = DefaultItemAnimator()
        rv_movies.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_movies.setOnRefreshListener(this)
        rv_movies.addLoadingView(load_view)
        rv_movies.addEmptyView(empty_view)
    }


    override fun onItemClick(view: View, position: Int, item: Weekly) {
        val intent = Intent(context, MovieInfoActivity::class.java)
        intent.putExtra("movie_id", item.subject.id)
        val v = view.findViewById<ImageView>(R.id.iv_movie)
        val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity!!,
                        v, getString(R.string.transition_movie_image)
                )
        startActivity(intent, options.toBundle())
    }

    override fun onRefresh() {
        onRequestData()
    }

    private fun setupLoopMovieName(movies: List<Weekly>) {
        val textList = ArrayList<String>()
        movies.forEach { textList.add(it.subject.title + " | " + it.subject.original_title) }
        activity?.apply {
            findViewById<LoopTextView>(R.id.loop_movie_name).setTextList(textList)
        }
    }
}