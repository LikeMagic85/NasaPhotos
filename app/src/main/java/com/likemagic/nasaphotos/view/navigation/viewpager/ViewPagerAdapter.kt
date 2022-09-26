package com.likemagic.nasaphotos.view.navigation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.likemagic.nasaphotos.view.navigation.earthNav.EarthFragment
import com.likemagic.nasaphotos.view.navigation.marsNav.MarsFragment
import com.likemagic.nasaphotos.view.navigation.SolarSystemFragment

open class ViewPagerAdapter(private val fm: FragmentManager):FragmentStatePagerAdapter(fm) {

    private val fragments = arrayOf(EarthFragment.newInstance(), MarsFragment.newInstance(), SolarSystemFragment.newInstance())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Earth"
            1 -> "Mars"
            2 -> "SolarSystem"
            else -> null
        }
    }
}