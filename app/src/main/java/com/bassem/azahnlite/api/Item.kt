package com.bassem.azahnlite.api
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "prayers_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id : Int=0,
    val asr: String,
    val date_for: String,
    val dhuhr: String,
    val fajr: String,
    val isha: String,
    val maghrib: String,
    val shurooq: String
) : Serializable