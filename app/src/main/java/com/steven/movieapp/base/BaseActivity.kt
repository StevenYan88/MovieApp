package com.steven.movieapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.steven.movieapp.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_movie_info.*


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        onRequestData()
    }


    abstract fun getLayoutId(): Int
    abstract fun initView()
    open fun onRequestData() {

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}