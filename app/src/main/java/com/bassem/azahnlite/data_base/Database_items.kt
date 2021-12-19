package com.bassem.azahnlite.data_base

import androidx.room.Entity
import androidx.room.PrimaryKey


//@Entity(tableName = "prayers_table")
 class Database_items(){
  //  @PrimaryKey(autoGenerate = true)

    var id : Int=0
    val asr: String?=null
    val date_for: String?=null
    val dhuhr: String?=null
    val fajr: String?=null
    val isha: String?=null
    val maghrib: String?=null
    val shurooq: String?=null


}
