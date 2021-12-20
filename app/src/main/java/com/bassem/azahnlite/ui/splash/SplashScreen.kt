package com.bassem.azahnlite.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bassem.azahnlite.ItemWeekly
import com.bassem.azahnlite.MainActivity
import com.bassem.azahnlite.R
import com.bassem.azahnlite.WeeklyPrayers
import com.bassem.azahnlite.api.Item
import com.bassem.azahnlite.api.Myprayers
import com.bassem.azahnlite.data_base.PrayersDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class SplashScreen : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    var cityBundle: String? = null
    var countryBundle: String? = null
    var item: Item? = null
    var prayersList: List<Item>? = null
    var latitue : Double?=null
    var longtiude : Double?=null

    var weeklyList: List<ItemWeekly>? = null
    var isConnect : Boolean?=null

    var prayerArray: ArrayList<Item>? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        StatusBarUtil.setTransparent(SplashScreen@ this)
        var viewModel = ViewModelProvider(this)[SplashViewmodel::class.java]
        Checking()


    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (hasLocationPermission()) {

            fusedLocationProviderClient.lastLocation.addOnSuccessListener {

                    location ->
                run {
                    getAddress(location.latitude, location.longitude)
                    latitue=location.latitude
                    longtiude=location.longitude
                    Save_coordinates(latitue.toString(),longtiude.toString())

                }
            }
        }
    }


    private fun getAddress(La: Double, Lo: Double) {
        var num = 0

        while (num < 3) {

            var geocoder = Geocoder(this, Locale.getDefault())
            val address: List<Address> = geocoder.getFromLocation(La, Lo, 1)
            if (address.isNotEmpty()) {
                var city = address[0].locality
                //    cityBundle = city
                val state: String = address[0].adminArea
                cityBundle = state

                val country: String = address[0].countryName
                countryBundle = country
                Save_City(cityBundle!!,countryBundle!!)

            }
            getPrayers()
            num++

        }


    }


    fun sendToActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getPrayers() {
        val apiKey = "d3e8fcd1ee38e5ab5e16fabfc98fdfae"
        val url = "https://muslimsalat.com/london/weekly.json?key=$apiKey"
        var client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        Thread(Runnable {


            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                    runOnUiThread {
                        val builder = AlertDialog.Builder(this@SplashScreen)
                        builder.setMessage("Please check your internet connection")
                        builder.setPositiveButton(
                            "try again",
                            DialogInterface.OnClickListener { _, _ -> restartApp(this@SplashScreen) })
                        builder.setNegativeButton(
                            "exsit",
                            DialogInterface.OnClickListener { _, _ -> exsit() }
                        )
                        builder.show()
                    }


                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {

                    } else {

                        val body = response.body?.string()

                        val gson = GsonBuilder().create()
                        val prayers = gson.fromJson(body, Myprayers::class.java)


                        prayersList = prayers.items
                        prayerArray = ArrayList(prayersList)
                        println(prayerArray)
                        val db = PrayersDatabase.getinstance(this@SplashScreen)
                        PrayersDatabase.db_write.execute {

                            db.dao().deleteAll()
                            db.dao().insert(prayerArray!!)
                        }
                       sendToActivity()
                    }

                }
            })
        }).start()


    }

    private fun exsit() {
        finishAffinity()


    }

    fun restartApp(context: Activity) {
        val intent = Intent(context, SplashScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
        Runtime.getRuntime().exit(0)
    }

    fun hasLocationPermission() =
        EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

    fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application can't work without location permission",
            101,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "Permission is granted", Toast.LENGTH_LONG).show()

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        if (EasyPermissions.somePermissionDenied(this, perms.first())) {
            AppSettingsDialog.Builder(Activity()).build().show()
        } else {
            requestLocationPermission()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

   fun isOnline ():Boolean{

       val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
       var activenetworkInfo : NetworkInfo?=null
       activenetworkInfo=cm.activeNetworkInfo
       return activenetworkInfo!=null && activenetworkInfo.isConnectedOrConnecting
   }

    fun Checking (){
        isOnline()

        isConnect=isOnline()
        if (isConnect as Boolean){

            if (!hasLocationPermission()) {
                requestLocationPermission()
            } else {
                getCurrentLocation()
            }
        } else {
            var intent = Intent(this,MainActivity::class.java)
           // intent.putExtra("isConnect",isConnect)
            startActivity(intent)

        }

    }

    fun Save_City (city:String,country:String){
        val sharedPref: SharedPreferences = this.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putString("city",city)
        editor.putString("country",country)
        editor.apply()
    }

    fun Save_coordinates (La :String,Lo:String){
        val sharedPref:SharedPreferences=this.getSharedPreferences("Pref",Context.MODE_PRIVATE)
        var editor=sharedPref.edit()
        editor.putString("La",La)
        editor.putString("Lo",Lo)
        editor.apply()


    }




}