package com.steven.movieapp.repository

import androidx.lifecycle.LiveData
import com.steven.movieapp.db.History
import com.steven.movieapp.db.HistoryDao
import com.steven.movieapp.utils.AppExecutors

/**
 * Description:
 * Data：3/13/2019-10:27 AM
 * @author yanzhiwen
 */
class HistoryRepository private constructor(private val historyDao: HistoryDao) {


    fun getSearchHistory(): LiveData<List<History>> = historyDao.getSearchHistory()


    fun saveHistory(history: History) {
        AppExecutors.diskIO().execute {
            historyDao.insertHistory(history)
        }
    }


    fun getSearchHistoryByName(name: String): LiveData<History> = historyDao.getSearchHistoryByName(name)

    companion object {


        //单例：双重枷锁
        @Volatile
        private var instance: HistoryRepository? = null


        fun getInstance(historyDao: HistoryDao): HistoryRepository {
            return instance ?: synchronized(this) {
                instance ?: HistoryRepository(historyDao).also { instance = it }
            }
        }
    }
}