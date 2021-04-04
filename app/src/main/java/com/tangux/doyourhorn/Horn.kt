package com.tangux.doyourhorn

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Horn(
        @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid") val uid: Long = 0,

        @ColumnInfo(name = "date") val date: String?,
        @ColumnInfo(name = "start_time") val startTime: String?,
        @ColumnInfo(name = "end_time") val endTime: String?,
        @ColumnInfo(name = "color") val color: String?,
        @ColumnInfo(name = "img") val img: String?,
        @ColumnInfo(name = "state") val state: String?
){
    companion object {
        const val DATE = "date"
        const val IMG = "img"
        const val STATE = "state"
    }
}



