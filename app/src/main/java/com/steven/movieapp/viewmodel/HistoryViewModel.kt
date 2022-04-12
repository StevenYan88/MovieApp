package com.steven.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.steven.movieapp.base.BaseViewModel
import com.steven.movieapp.bean.History
import com.steven.movieapp.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Description:
 * Data：3/13/2019-1:59 PM
 * @author yanzhiwen
 */
class HistoryViewModel constructor(private val historyRepository: HistoryRepository) :
    BaseViewModel() {

    fun saveMovieName(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.saveHistory(history)
        }
    }

    fun getSearchHistory(): LiveData<List<History>> = historyRepository.getSearchHistory()

}
