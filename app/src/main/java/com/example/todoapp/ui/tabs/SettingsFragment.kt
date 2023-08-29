package com.example.todoapp.ui.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.todoapp.databinding.FragmentSettingsBinding
import java.util.Locale


class SettingsFragment : Fragment() {
    lateinit var viewBinding:FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding= FragmentSettingsBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.languageSpinner.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position==1){
                    val localeListCompt=LocaleListCompat.create(Locale("en"))
                    AppCompatDelegate.setApplicationLocales(localeListCompt)
                }else if(position==2){
                    val localeListCompt=
                        LocaleListCompat.create(java.util.Locale("ar"))
                    AppCompatDelegate.setApplicationLocales(localeListCompt)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        viewBinding.modeSpinner.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position==1){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }else if(position==2){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }
}