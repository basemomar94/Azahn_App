package com.bassem.azahnlite.ui.home

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
import com.bassem.azahnlite.databinding.FragmentHomeBinding
import com.jaeger.library.StatusBarUtil

class Home () : Fragment(R.layout.fragment_home) {
    var city : String?=null
    var country : String?=null
    var prayerList : ArrayList<Item>?=null
    var asr: String?=null
    val date_for: String?=null
    var dhuhr: String?=null
    var fajr: String?=null
    var isha: String?=null
    var maghrib: String?=null
    val shurooq: String?=null




    var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //  StatusBarUtil.setTransparent(activity)
        getCity()

        var viewModel = ViewModelProvider(this).get(HomeViewmodel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(this).get(HomeViewmodel::class.java)
        binding.location.text="$city, $country"

        binding.dateTV.text=viewModel.getdate()
        binding.forward.setOnClickListener {
            binding.dateTV.text=viewModel.dateforward()
        }
        binding.back.setOnClickListener {
            binding.dateTV.text=viewModel.datebackward()
        }
        binding.dateTV.setOnClickListener {
            binding.dateTV.text=viewModel.getdate()
        }
        binding.fajr.text=fajr
        binding.dhuhr.text=dhuhr
        binding.asr.text=asr
        binding.maghrib.text=maghrib
        binding.isha.text=isha









    }
    fun getCity(){
        val bundle = arguments
        if (bundle!=null){
            city = bundle.getString("city")
            country=bundle.getString("country")
           prayerList=bundle.getSerializable("prayers") as ArrayList<Item>
            fajr= prayerList!![0].fajr
            dhuhr=prayerList!![0].dhuhr
            asr=prayerList!![0].asr
            maghrib=prayerList!![0].maghrib
            isha=prayerList!![0].isha
            println("$fajr============last")








        }
    }

}