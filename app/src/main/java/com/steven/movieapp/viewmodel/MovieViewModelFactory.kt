package com.steven.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steven.movieapp.repository.MovieRepository

/**
 * Description:
 * Data：2/28/2019-10:34 AM
 * @author yanzhiwen
 */
class MovieViewModelFactory(private val movieRepository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movieRepository) as T
    }
}