package com.steven.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.steven.movieapp.db.History
import com.steven.movieapp.repository.HistoryRepository

/**
 * Description:
 * Data：3/13/2019-1:59 PM
 * @author yanzhiwen
 */
class HistoryViewModel constructor(private val historyRepository: HistoryRepository) : ViewModel() {

    fun getSearchHistory(): LiveData<List<History>> =
        historyRepository.getSearchHistory()

    fun getSearchHistoryByName(name: String): LiveData<History> =
        historyRepository.getSearchHistoryByName(name)
}
