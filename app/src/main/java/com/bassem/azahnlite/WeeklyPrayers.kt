package com.bassem.azahnlite

data class WeeklyPrayers(
    val address: String,
    val city: String,
    val country: String,
    val `for`: String,
    val items: List<ItemWeekly>,
    val latitude: String,
    val link: String,
    val longitude: String,
    val map_image: String,
    val method: Int,
    val postal_code: String,
    val prayer_method_name: String,
    val qibla_direction: String,
    val query: String,
    val sealevel: String,
    val state: String,

)