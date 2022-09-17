package com.likemagic.nasaphotos.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.utils.APP_SETTINGS
import com.likemagic.nasaphotos.utils.SYSTEM_THEME
import com.likemagic.nasaphotos.view.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(nightThemeSettings())
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, PictureOfTheDayFragment.newInstance())
            .commit()
    }

    private fun nightThemeSettings():Int{
        val sp = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)
        return if (sp.getBoolean(SYSTEM_THEME, false)){
            MODE_NIGHT_FOLLOW_SYSTEM
        }else MODE_NIGHT_NO
    }
}