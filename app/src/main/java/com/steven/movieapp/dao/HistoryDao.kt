package com.steven.movieapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.steven.movieapp.bean.History

/**
 * Description:
 * Data：3/13/2019-11:33 AM
 * @author yanzhiwen
 */
@Dao
interface HistoryDao {
    @Query("SELECT * FROM histories")
    fun getSearchHistory(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

}