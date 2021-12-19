package com.bassem.azahnlite

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bassem.azahnlite.api.Item
import com.bassem.azahnlite.ui.home.Home
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jaeger.library.StatusBarUtil
import java.util.ArrayList




class MainActivity : AppCompatActivity() {
    var cityBundle: String? = null
    var countryBundle: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomnaviagation = findViewById<BottomNavigationView>(R.id.bottomAppBarc)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomnaviagation.setupWithNavController(navController)


        sendToFragment()
    }

    fun sendToFragment() {
        val prayerBundle = intent.getSerializableExtra("prayers") as ArrayList<Item>?

        cityBundle = intent.getStringExtra("city")
        countryBundle = intent.getStringExtra("country")

        val mFragmentmange = supportFragmentManager
        val mFragmenttranscution = mFragmentmange.beginTransaction()
        val mFragment = Home()
        val mBundle = Bundle()
        mBundle.putString("city", cityBundle)
        mBundle.putString("country", countryBundle)
        mBundle.putSerializable("prayers",prayerBundle)
        mFragment.arguments = mBundle
        mFragmenttranscution.add(R.id.nav_host_fragment, mFragment).commit()

    }
}