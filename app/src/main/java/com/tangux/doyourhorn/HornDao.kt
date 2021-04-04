package com.tangux.doyourhorn

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HornDao {
    @Query("SELECT * FROM horn")
    fun getAll(): List<Horn>

    @Query("SELECT * FROM horn WHERE uid IN (:hornIds)")
    fun loadAllByIds(hornIds: IntArray): List<Horn>

    @Query("SELECT * FROM horn WHERE color LIKE :color AND state LIKE :state LIMIT 1")
    fun findByColor(color: String, state: String): Horn

    @Insert
    fun insertAll(vararg horns: Horn)

    @Delete
    fun delete(horn: Horn)
}