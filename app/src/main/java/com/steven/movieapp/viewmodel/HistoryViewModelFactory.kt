package com.steven.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steven.movieapp.db.HistoryRepository

/**
 * Description:
 * Data：2/28/2019-10:34 AM
 * @author yanzhiwen
 */
class HistoryViewModelFactory constructor(private val historyRepository: HistoryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewModel(historyRepository) as T
    }
}