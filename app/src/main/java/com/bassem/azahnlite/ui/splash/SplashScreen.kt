package com.bassem.azahnlite.ui.splash

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.bassem.azahnlite.MainActivity
import com.bassem.azahnlite.R
import com.bassem.azahnlite.api.Item
import com.bassem.azahnlite.api.Myprayers
import com.bassem.azahnlite.ui.home.Home
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import com.jaeger.library.StatusBarUtil
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class SplashScreen : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var longitude: Double? = null
    var altitude: Double? = null
    var cityBundle: String? = null
    var countryBundle: String? = null
    var item: Item? = null
    var prayersList: List<Item>? = null
    var prayerArray: ArrayList<Item>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        StatusBarUtil.setTransparent(SplashScreen@ this)
        var viewModel = ViewModelProvider(this)[SplashViewmodel::class.java]
        getCurrentLocation()

        //  getPrayers()


    }

    fun getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

    }

    fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            




        }


        task.addOnSuccessListener {
            getAdress(it.latitude, it.longitude)
        }


    }

    fun getAdress(La: Double, Lo: Double) {
        var num = 0

        while (num < 3) {
            var geocoder: Geocoder? = null
            geocoder = Geocoder(this, Locale.getDefault())
            var adress: List<Address> = geocoder.getFromLocation(La, Lo, 1)
            if (adress.isNotEmpty()) {
                var city = adress[0].locality
                //    cityBundle = city
                var state: String = adress[0].adminArea
                cityBundle = state

                var country: String = adress[0].countryName
                countryBundle = country
                getPrayers()


            } else {

            }
            num++

        }


    }


    fun sendToActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("city", cityBundle)
        intent.putExtra("country", countryBundle)

        intent.putExtra("prayers", prayerArray)


        startActivity(intent)
        finish()
    }

    fun getPrayers() {
        val apiKey = "d3e8fcd1ee38e5ab5e16fabfc98fdfae"

        val url = "https://muslimsalat.com/$cityBundle.json?key=$apiKey"


        var client: OkHttpClient = OkHttpClient()
        val request = Request.Builder().url(url).build()

        Thread(Runnable {


            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                    runOnUiThread {
                        val builder = AlertDialog.Builder(this@SplashScreen)
                        builder.setMessage("Please check your internet connection")
                        builder.setPositiveButton("try again",DialogInterface.OnClickListener { dialogInterface, i -> restartApp(this@SplashScreen)  })
                        builder.setNegativeButton("exsit",DialogInterface.OnClickListener({dialogInterface, i -> exsit() }))
                        builder.show()
                    }




                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        throw IOException("the problem is =====================$response")
                    } else {
                        println("===============${response.body.toString()}")
                        val body = response.body?.string()

                        val gson = GsonBuilder().create()
                        val prayers = gson.fromJson(body, Myprayers::class.java)


                        prayersList=prayers.items

                        prayerArray = ArrayList(prayersList)
                        println(prayerArray)


                       sendToActivity()


                    }

                }
            })
        }).start()


    }

    private fun exsit() {
        finishAffinity()


    }

    fun restartApp (context: Activity){
        val intent = Intent(context,SplashScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            (context as Activity).finish()
        }
        Runtime.getRuntime().exit(0)
    }


}