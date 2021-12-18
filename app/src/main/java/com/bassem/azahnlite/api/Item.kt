package com.bassem.azahnlite.api
import java.io.Serializable


data class Item(
    val asr: String,
    val date_for: String,
    val dhuhr: String,
    val fajr: String,
    val isha: String,
    val maghrib: String,
    val shurooq: String
) : Serializable