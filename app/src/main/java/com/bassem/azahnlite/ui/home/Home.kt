package com.bassem.azahnlite.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
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
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.DatatypeConstants.MINUTES


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

    override fun onResume() {
        super.onResume()
        setTimes()

        getnext()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(this).get(HomeViewmodel::class.java)
        binding.location.text = "$city, $country"

        binding.forward.setOnClickListener {

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
        // binding.timeshow.text="5:3"
        getnext()
    }

    @SuppressLint("NewApi")
    fun Countdown(prayer: String): LocalTime {

        val sdf = DateTimeFormatter.ofPattern("hh:mm a")
        var timeNow = LocalTime.now()

        val systemDate = Calendar.getInstance().time
        //  displaytime.text="systemDate.toString()"
        //  displaytime.setText("check")
        binding.timeshow.text = timeNow.format(sdf)

        var value: LocalTime = if (prayer.length <= 7) {

            var prayerPlus = "0$prayer"
            LocalTime.parse(prayerPlus.uppercase(), sdf)


        } else {
            LocalTime.parse(prayer.uppercase(), sdf)

        }
        return value
        /* var fdiff = String.format(
             Locale.ENGLISH,
             "%d hours %d minutes %d seconds",
             difference.toDays(),
             difference.toHoursPart(),
             difference.toMinutesPart(),
             difference.toSecondsPart()

         ) */


    }

    fun getnext() {
        var timeNow = LocalTime.now()

        val prayer1 = Countdown(fajr!!)
        val prayer2 = Countdown(dhuhr!!)
        val prayer3 = Countdown(asr!!)
        val prayer4 = Countdown(maghrib!!)
        val prayer5 = Countdown(isha!!)

        when {
            timeNow <= prayer1 -> {
                binding.fajr.setTextColor(Color.GREEN)
                binding.fajrTV.setTextColor(Color.GREEN)


                //To avoid on resume conflict
                binding.dhuhr.setTextColor(Color.BLACK)
                binding.dhurTV.setTextColor(Color.BLACK)

                binding.asr.setTextColor(Color.BLACK)
                binding.asrTV.setTextColor(Color.BLACK)

                binding.maghrib.setTextColor(Color.BLACK)
                binding.maghribTV.setTextColor(Color.BLACK)

                binding.isha.setTextColor(Color.BLACK)
                binding.ishaTV.setTextColor(Color.BLACK)

            }
            timeNow <= prayer2 -> {
                binding.dhuhr.setTextColor(Color.GREEN)
                binding.dhurTV.setTextColor(Color.GREEN)

                //To avoid on resume conflict
                binding.fajr.setTextColor(Color.BLACK)
                binding.fajrTV.setTextColor(Color.BLACK)

                binding.asr.setTextColor(Color.BLACK)
                binding.asrTV.setTextColor(Color.BLACK)

                binding.maghrib.setTextColor(Color.BLACK)
                binding.maghribTV.setTextColor(Color.BLACK)

                binding.isha.setTextColor(Color.BLACK)
                binding.ishaTV.setTextColor(Color.BLACK)

            }
            timeNow <= prayer3 -> {
                binding.asr.setTextColor(Color.GREEN)
                binding.asrTV.setTextColor(Color.GREEN)

                //To avoid on resume conflict
                binding.fajr.setTextColor(Color.BLACK)
                binding.fajrTV.setTextColor(Color.BLACK)

                binding.dhuhr.setTextColor(Color.BLACK)
                binding.dhurTV.setTextColor(Color.BLACK)

                binding.maghrib.setTextColor(Color.BLACK)
                binding.maghribTV.setTextColor(Color.BLACK)

                binding.isha.setTextColor(Color.BLACK)
                binding.ishaTV.setTextColor(Color.BLACK)

            }
            timeNow <= prayer4 -> {
                binding.maghrib.setTextColor(Color.GREEN)
                binding.maghribTV.setTextColor(Color.GREEN)

                //To avoid on resume conflict
                binding.fajr.setTextColor(Color.BLACK)
                binding.fajrTV.setTextColor(Color.BLACK)

                binding.dhuhr.setTextColor(Color.BLACK)
                binding.dhurTV.setTextColor(Color.BLACK)

                binding.asr.setTextColor(Color.BLACK)
                binding.asrTV.setTextColor(Color.BLACK)

                binding.isha.setTextColor(Color.BLACK)
                binding.ishaTV.setTextColor(Color.BLACK)

            }
            timeNow <= prayer5 -> {
                binding.isha.setTextColor(Color.GREEN)
                binding.ishaTV.setTextColor(Color.GREEN)

                //To avoid on resume conflict
                binding.fajr.setTextColor(Color.BLACK)
                binding.fajrTV.setTextColor(Color.BLACK)

                binding.dhuhr.setTextColor(Color.BLACK)
                binding.dhurTV.setTextColor(Color.BLACK)

                binding.asr.setTextColor(Color.BLACK)
                binding.asrTV.setTextColor(Color.BLACK)

                binding.maghrib.setTextColor(Color.BLACK)
                binding.maghribTV.setTextColor(Color.BLACK)


            }
        }


    }

}


