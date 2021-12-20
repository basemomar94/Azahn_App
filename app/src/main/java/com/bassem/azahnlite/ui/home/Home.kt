package com.bassem.azahnlite.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bassem.azahnlite.R
import com.bassem.azahnlite.api.Item
import com.bassem.azahnlite.data_base.PrayersDatabase
import com.bassem.azahnlite.databinding.FragmentHomeBinding
import com.jaeger.library.StatusBarUtil
import java.lang.System.currentTimeMillis
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.Instant.now
import java.time.LocalDate
import java.time.LocalDateTime.now
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList
import java.util.Locale
import java.util.concurrent.TimeUnit


class Home() : Fragment(R.layout.fragment_home) {
    var city: String? = null
    var country: String? = null
    var prayerList: ArrayList<Item>? = null
    var currentList: ArrayList<Item>? = null


    var asr: String? = null
    var date_for: String? = null
    var dhuhr: String? = null
    var fajr: String? = null
    var isha: String? = null
    var maghrib: String? = null
    val shurooq: String? = null
    var day = 0
    lateinit var mlist: ArrayList<Item>


    var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var viewModel = ViewModelProvider(this).get(HomeViewmodel::class.java)
        //  StatusBarUtil.setTransparent(activity)

        getDatabase()
        //   getCity()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(this).get(HomeViewmodel::class.java)
        binding.location.text = "$city, $country"

        binding.forward.setOnClickListener {
            println(day)

            try {
                day++
                getCity()
                setTimes()
            } catch (E: Exception) {
                day = mlist.size
            }


        }
        binding.back.setOnClickListener {
            try {
                day--
                getCity()
                setTimes()
            } catch (E: Exception) {
                day = 0
            }

        }
        binding.dateTV.setOnClickListener {
            binding.dateTV.text = viewModel.getdate()
        }


        setTimes()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCity() {
        val preference = context!!.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        city = preference.getString("city", "....")
        country = preference.getString("country", "...")
        currentList = mlist
        fajr = currentList!![day].fajr
        dhuhr = currentList!![day].dhuhr
        asr = currentList!![day].asr
        maghrib = currentList!![day].maghrib
        isha = currentList!![day].isha
        date_for = currentList!![day].date_for
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDatabase() {
        val db = PrayersDatabase.getinstance(context)
        PrayersDatabase.db_write.execute {
            mlist = db.dao().getall() as ArrayList<Item>
            println(mlist.size)
            getCity()

        }
    }

    fun setTimes() {
        binding.fajr.text = fajr
        binding.dhuhr.text = dhuhr
        binding.asr.text = asr
        binding.maghrib.text = maghrib
        binding.isha.text = isha
        binding.dateTV.text = date_for
    }




}