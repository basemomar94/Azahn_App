package com.bassem.azahnlite.api

data class Myprayers(
    val address: String,
    val city: String,
    val country: String,
  //  val country_code: String,
  //  val daylight: String,
  //  val `for`: String,
    var items: List<Item>,
  //  val latitude: String,
  //  val link: String,
  //  val longitude: String,
  //  val map_image: String,
  //  val method: Int,
  //  val postal_code: String,
  //  val prayer_method_name: String,
    val qibla_direction: String,
  //  val query: String,
  //  val sealevel: String,
  //  val state: String,
  //  val status_code: Int,
  //  val status_description: String,
  //  val status_valid: Int,
  //  val timezone: String,
  //  val title: String,
   // val today_weather: TodayWeather
)