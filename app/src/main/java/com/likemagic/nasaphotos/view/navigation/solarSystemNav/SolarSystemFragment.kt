package com.likemagic.nasaphotos.view.navigation.solarSystemNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.ViewModel.picSolarSystemViewModel.SSAppState
import com.likemagic.nasaphotos.ViewModel.picSolarSystemViewModel.SSViewModel
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

    private val viewModel: SSViewModel by lazy {
        ViewModelProvider(this)[SSViewModel::class.java]
    }

    private val ssAdapter = SolarSystemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolarSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.solarSystemRecycler.adapter = ssAdapter
        viewModel.getLiveData().observe(viewLifecycleOwner){
            renderData(it)
        }
        viewModel.getPhotos()
    }

    private fun renderData(appState: SSAppState){
        when(appState){
            is SSAppState.Error -> {
                when (appState.error) {
                    is AppError.ErrorClient -> {
                        Snackbar.make(binding.root,requireContext().resources.getString(R.string.errorClient), Snackbar.LENGTH_SHORT).show()
                    }
                    is AppError.ErrorServer -> {
                        Snackbar.make(binding.root,requireContext().resources.getString(R.string.errorServer), Snackbar.LENGTH_SHORT).show()
                    }
                    is AppError.ErrorUnknown -> {
                        Snackbar.make(binding.root,requireContext().resources.getString(R.string.errorUnknown), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            SSAppState.Loading -> {}
            is SSAppState.Success -> {
                ssAdapter.setData(appState.photos)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SolarSystemFragment()
    }
}