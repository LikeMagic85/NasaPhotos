package com.likemagic.nasaphotos.view.navigation

import com.likemagic.nasaphotos.view.navigation.viewpager.DepthPageTransformer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.databinding.FragmentApiBinding
import com.likemagic.nasaphotos.view.navigation.viewpager.ViewPagerAdapter

class ApiFragment : Fragment() {
    private var _binding: FragmentApiBinding? = null
    private val binding: FragmentApiBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.viewPager.setPageTransformer(DepthPageTransformer())
        TabLayoutMediator(binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when(position){
                0 -> tab.text = requireContext().getString(R.string.earth)
                1 -> tab.text = requireContext().getString(R.string.mars)
                2 -> tab.text = requireContext().getString(R.string.solar_system)
            }
        }.attach()
    }

    private fun setupBottomNavigation(){
        /*binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.action_earth -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .addToBackStack("")
                        .replace(R.id.mainContainer, EarthFragment.newInstance())
                        .commit()
                }
            }
            true
        }
        binding.bottomNav.selectedItemId = R.id.action_earth*/
    }

    companion object {

        @JvmStatic
        fun newInstance() = ApiFragment()
    }
}