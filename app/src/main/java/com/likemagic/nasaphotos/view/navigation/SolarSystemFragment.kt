package com.likemagic.nasaphotos.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.likemagic.nasaphotos.databinding.FragmentSolarSystemBinding

class SolarSystemFragment : Fragment() {

    private var _binding: FragmentSolarSystemBinding? = null
    private val binding: FragmentSolarSystemBinding
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
    ): View? {
        _binding = FragmentSolarSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SolarSystemFragment()
    }
}