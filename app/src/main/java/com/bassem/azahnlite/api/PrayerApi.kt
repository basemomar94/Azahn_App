package com.bassem.azahnlite.api

import retrofit2.Call
import retrofit2.http.GET

interface PrayerApi {

    @GET
    fun getData () : Call<List<Item>>

}