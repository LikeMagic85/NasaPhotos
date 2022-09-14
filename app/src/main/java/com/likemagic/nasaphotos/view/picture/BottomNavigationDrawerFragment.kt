package com.likemagic.nasaphotos.view.picture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.ViewModel.POTDViewModel
import com.likemagic.nasaphotos.databinding.BottomNavigationLayoutBinding
import com.likemagic.nasaphotos.databinding.FragmentPictureOfTheDayBinding

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {


    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
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
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_one -> {

                }
                R.id.navigation_two -> {

                }
            }
            true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BottomNavigationDrawerFragment()

    }
}