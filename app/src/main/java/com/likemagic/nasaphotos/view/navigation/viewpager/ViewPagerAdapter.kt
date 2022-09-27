package com.likemagic.nasaphotos.view.navigation.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.likemagic.nasaphotos.view.navigation.earthNav.EarthFragment
import com.likemagic.nasaphotos.view.navigation.marsNav.MarsFragment
import com.likemagic.nasaphotos.view.navigation.solarSystemNav.SolarSystemFragment

open class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(EarthFragment.newInstance(), MarsFragment.newInstance(), SolarSystemFragment.newInstance())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}