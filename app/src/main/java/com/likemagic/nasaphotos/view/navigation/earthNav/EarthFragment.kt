package com.likemagic.nasaphotos.view.navigation.earthNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.ViewModel.picOfEarthViewModel.POEAppState
import com.likemagic.nasaphotos.ViewModel.picOfEarthViewModel.POEViewModel
import com.likemagic.nasaphotos.databinding.FragmentEarthBinding

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val viewModel: POEViewModel by lazy {
        ViewModelProvider(this)[POEViewModel::class.java]
    }

    private val earthAdapter = EarthAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.marsRecycler.adapter = earthAdapter
        viewModel.getLiveData().observe(viewLifecycleOwner){
            renderData(it)
        }
        viewModel.sendRequest()
    }

    private fun renderData(appState: POEAppState){
        when(appState){
            is POEAppState.Error -> {
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
            POEAppState.Loading -> {}
            is POEAppState.Success -> {
                earthAdapter.setData(appState.pictureOfEarthDTO)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EarthFragment()
    }
}