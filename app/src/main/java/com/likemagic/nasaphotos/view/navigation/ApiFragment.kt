package com.likemagic.nasaphotos.view.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.databinding.FragmentApiBinding
import com.likemagic.nasaphotos.databinding.FragmentEarthBinding
import com.likemagic.nasaphotos.view.navigation.viewpager.ViewPagerAdapter
import java.text.FieldPosition

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
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ApiFragment()
    }
}