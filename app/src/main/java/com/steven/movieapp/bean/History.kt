package com.steven.movieapp.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Description:
 * Data：3/13/2019-10:01 AM
 * @author yanzhiwen
 */
@Entity(tableName = "histories")
data class History(
    @PrimaryKey
    val name: String
)


