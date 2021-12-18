package com.bassem.azahnlite.ui.home

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class HomeViewmodel : ViewModel() {

    var increase = 1

    fun getdate () : String {
        val datenow = Calendar.getInstance().time
        val sdt = SimpleDateFormat("dd/MM/yyyy")
        return sdt.format(datenow)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateforward () : String {

        var tomorrow = LocalDate.now().plusDays(increase.toLong())
        increase++

        var tomorrowdate= tomorrow.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        return tomorrowdate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun datebackward () : String {

        var tomorrow = LocalDate.now().plusDays(-increase.toLong())
        increase++

        var tomorrowdate= tomorrow.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        return tomorrowdate
    }




}