package com.bassem.azahnlite.data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bassem.azahnlite.api.Item

@Dao
interface Dao {
    @Insert(onConflict =OnConflictStrategy.REPLACE)
    fun insert(list: List<Item>)

    /*
    @Query("Select * from prayers_table")
    fun getall(): ArrayList<Item>*/
}