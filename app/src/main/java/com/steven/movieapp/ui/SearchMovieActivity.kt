package com.steven.movieapp.ui

import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.movieapp.R
import com.steven.movieapp.adapter.MovieAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.db.History
import com.steven.movieapp.bean.Movie
import com.steven.movieapp.utils.InjectorUtils
import com.steven.movieapp.viewmodel.HistoryViewModel
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultLoadViewCreator
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.LoadRefreshRecyclerView
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import com.steven.movieapp.widget.tag.BaseAdapter
import kotlinx.android.synthetic.main.activity_search_movie.*
import kotlinx.android.synthetic.main.load_view.*

class SearchMovieActivity : BaseActivity(), RefreshRecyclerView.OnRefreshListener,
    LoadRefreshRecyclerView.OnLoadListener {

    private var start: Int = 0
    private lateinit var name: String
    private var movies = arrayListOf<Movie>()
    private val adapter: MovieAdapter by lazy {
        MovieAdapter(this, R.layout.movie_list_item, this.movies)
    }


    private val historyViewModel: HistoryViewModel by lazy {
        ViewModelProviders.of(this, InjectorUtils.providerHistoryViewModelFactory(this))
            .get(HistoryViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.activity_search_movie


    override fun initView() {
        load_view.visibility = View.GONE
        rv_movies.layoutManager = LinearLayoutManager(this)
        rv_movies.itemAnimator = DefaultItemAnimator()
        rv_movies.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_movies.addLoadViewCreator(DefaultLoadViewCreator())
        rv_movies.setOnRefreshListener(this)
        rv_movies.setOnLoadListener(this)

        iv_back.setOnClickListener {
            finish()
        }
        search.setOnClickListener {
            if (TextUtils.isEmpty(search_name.text)) {
                Toast.makeText(this, "请输入电影名称", Toast.LENGTH_SHORT).show()
            } else {
                searchByName(search_name.text.toString())
                saveName(search_name.text.toString())
            }
        }
        setupHistory()
    }

    private fun setupHistory() {
        historyViewModel.getSearchHistory().observe(this, Observer {
            val items = it
            tag.setAdapter(object : BaseAdapter() {
                override fun getCount(): Int {
                    return items.size
                }

                override fun getView(position: Int, parent: ViewGroup?): View {
                    val tv = LayoutInflater.from(this@SearchMovieActivity)
                        .inflate(R.layout.tag_item, parent, false) as TextView
                    tv.text = items[position].name
                    tv.setOnClickListener {
                        search_name.setText(items[position].name)
                    }
                    return tv
                }
            })
        })
    }


    private fun searchByName(name: String) {
        this.name = name
        if (this.movies.size > 0) {
            this.movies.clear()
            adapter.notifyDataSetChanged()
        }
        rv_movies.visibility = View.GONE
        load_view.visibility = View.VISIBLE
        movieViewModel.getMovieSearchByTag(name, 0, 20).observe(this, Observer {
            showMovie(it.subjects)
        })
    }

    private fun showMovie(movies: List<Movie>) {
        if (load_view.visibility == View.VISIBLE) {
            load_view.visibility = View.GONE
        }
        if (rv_movies.visibility == View.GONE) {
            rv_movies.visibility = View.VISIBLE
        }
        this.movies.addAll(movies)
        if (rv_movies.adapter == null) {
            rv_movies.adapter = adapter
        } else {
            rv_movies.onStopRefresh()
            if (rv_movies.isLoading()) {
                rv_movies.onStopLoad()
            }
            adapter.notifyDataSetChanged()
        }
        adapter.setOnItemClickListener(
            object : OnItemClickListener<Movie> {
                override fun onItemClick(view: View, position: Int, item: Movie) {
                    val intent = Intent(this@SearchMovieActivity, MovieInfoActivity::class.java)
                    intent.putExtra("movie_id", item.id)
                    startActivity(intent)
                }
            })
    }

    override fun onRefresh() {
        rv_movies.onStopRefresh()
    }

    override fun onLoad() {
        start += 20
        movieViewModel.getMovieSearchByTag(name, start, 20).observe(this, Observer {
            if (it.subjects.isNotEmpty()) {
                showMovie(it.subjects)
            }
        })
    }

    private fun saveName(name: String) {
        historyViewModel.getSearchHistoryByName(name).observe(this, Observer {
            if (it == null) {
                InjectorUtils.saveHistory(this@SearchMovieActivity, History(name))
            }
        })

    }
}
