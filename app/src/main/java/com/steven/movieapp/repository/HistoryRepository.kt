package com.steven.movieapp.repository

import androidx.lifecycle.LiveData
import com.steven.movieapp.bean.History
import com.steven.movieapp.dao.HistoryDao

/**
 * Description:
 * Data：3/13/2019-10:27 AM
 * @author yanzhiwen
 */
class HistoryRepository private constructor(private val historyDao: HistoryDao) {


    fun getSearchHistory(): LiveData<List<History>> = historyDao.getSearchHistory()

    suspend fun saveHistory(history: History) = historyDao.insertHistory(history)

    companion object {

        @Volatile
        private var instance: HistoryRepository? = null

        fun getInstance(historyDao: HistoryDao): HistoryRepository {
            return instance ?: synchronized(this) {
                instance ?: HistoryRepository(historyDao).also { instance = it }
            }
        }
    }
}