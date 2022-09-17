package com.likemagic.nasaphotos.view.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.likemagic.nasaphotos.databinding.FragmentSettingsBinding
import com.likemagic.nasaphotos.utils.APP_SETTINGS
import com.likemagic.nasaphotos.utils.IMAGE_HD
import com.likemagic.nasaphotos.utils.SYSTEM_THEME
import com.likemagic.nasaphotos.utils.VIDEO_ON_YOUTUBE_APP


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() {
            return _binding!!
        }

    lateinit var sp: SharedPreferences

    lateinit var editor: SharedPreferences.Editor

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sp = requireActivity().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
        editor =  sp.edit()
        setupTab()
        imageSettings()
        videoSettings()
        systemSettings()
    }

    private fun setupTab(){
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{binding.imageSettings.visibility = View.VISIBLE}
                    1->{binding.videoSettings.visibility = View.VISIBLE}
                    2->{binding.systemSettings.visibility = View.VISIBLE}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{binding.imageSettings.visibility = View.GONE}
                    1->{binding.videoSettings.visibility = View.GONE}
                    2->{binding.systemSettings.visibility = View.GONE}
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{binding.imageSettings.visibility = View.VISIBLE}
                    1->{binding.videoSettings.visibility = View.VISIBLE}
                    2->{binding.systemSettings.visibility = View.VISIBLE}
                }
            }

        })
    }

    private fun imageSettings(){
        binding.hdImage.isChecked = sp.getBoolean(IMAGE_HD, false)
        binding.hdImage.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    editor.putBoolean(IMAGE_HD, true)
                }else{
                    editor.putBoolean(IMAGE_HD, false)
                }
                editor.apply()
            }
        })
    }

    private fun videoSettings(){
        binding.youtubePlayer.isChecked = sp.getBoolean(VIDEO_ON_YOUTUBE_APP, false)
        binding.youtubePlayer.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    editor.putBoolean(VIDEO_ON_YOUTUBE_APP, true)
                }else{
                    editor.putBoolean(VIDEO_ON_YOUTUBE_APP, false)
                }
                editor.apply()
            }
        })
    }

    private fun systemSettings(){
        binding.system.isChecked = sp.getBoolean(SYSTEM_THEME, false)
        binding.system.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    editor.putBoolean(SYSTEM_THEME, true)
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                }else{
                    editor.putBoolean(SYSTEM_THEME, false)
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
                editor.apply()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}