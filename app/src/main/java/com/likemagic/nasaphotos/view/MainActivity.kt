package com.likemagic.nasaphotos.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.view.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, PictureOfTheDayFragment.newInstance())
            .commit()
    }
}