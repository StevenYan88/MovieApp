package com.steven.movieapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Description:
 * Data：3/13/2019-11:33 AM
 * @author yanzhiwen
 */
@Dao
interface HistoryDao {
    @Query("SELECT * FROM histories")
    fun getSearchHistory(): LiveData<List<History>>

    @Insert
    fun insertHistory(history: History)

    @Query("SELECT * FROM histories WHERE name = :searchName")
    fun getSearchHistoryByName(searchName: String): LiveData<History>
}