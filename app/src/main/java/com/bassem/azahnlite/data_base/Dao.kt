package com.bassem.azahnlite.data_base

import androidx.room.*
import androidx.room.Dao
import com.bassem.azahnlite.api.Item

@Dao
interface Dao {
    @Insert(onConflict =OnConflictStrategy.REPLACE)
    fun insert(list: List<Item>)


    @Query("Delete from prayers_table")
    fun deleteAll()


    @Query("Select * from prayers_table")
    fun getall(): List<Item>
}