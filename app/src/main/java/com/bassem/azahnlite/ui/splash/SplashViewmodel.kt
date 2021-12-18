package com.bassem.azahnlite.ui.splash

import android.accessibilityservice.GestureDescription
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull


class SplashViewmodel : ViewModel() {

    private val apikey = "d3e8fcd1ee38e5ab5e16fabfc98fdfae"
    val link = "https://muslimsalat.com/london.json?key=$apikey"






}