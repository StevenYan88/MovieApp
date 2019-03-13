package com.steven.movieapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Description:
 * Data：3/13/2019-10:01 AM
 * @author yanzhiwen
 */
@Entity(tableName = "histories")
data class History(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

