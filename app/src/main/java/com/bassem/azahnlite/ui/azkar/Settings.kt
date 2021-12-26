package com.bassem.azahnlite.ui.azkar

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.bassem.azahnlite.R
import com.bassem.azahnlite.databinding.FragmentHomeBinding
import com.bassem.azahnlite.databinding.FragmentSettingsBinding

class Settings ():Fragment(R.layout.fragment_settings) {

    var _binding : FragmentSettingsBinding?=null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseLanguage()

    }

    fun chooseLanguage (){
        binding.language.setOnClickListener {
            showDialog()

        }
    }
    fun showDialog(){
        val dialog=Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.language)
        dialog.show()
        val ar:RadioButton= activity!!.findViewById(R.id.Ar)
        ar.setOnClickListener {
            dialog.hide()
        }
    }
}