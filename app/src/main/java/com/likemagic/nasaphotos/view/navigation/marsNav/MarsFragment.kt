package com.likemagic.nasaphotos.view.navigation.marsNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.ViewModel.picFromMarsviewmodel.PFMAppState
import com.likemagic.nasaphotos.ViewModel.picFromMarsviewmodel.PFMViewModel
import com.likemagic.nasaphotos.databinding.FragmentMarsBinding

class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding: FragmentMarsBinding
        get() {
            return _binding!!
        }

    private val viewModel: PFMViewModel by lazy {
        ViewModelProvider(this)[PFMViewModel::class.java]
    }

    private val  marsAdapter = MarsAdapter()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.marsRecycler.adapter = marsAdapter
        viewModel.getLiveData().observe(viewLifecycleOwner){
            renderData(it)
        }
        viewModel.sendRequest()
    }

    private fun renderData(appState: PFMAppState){
        when(appState){
            is PFMAppState.Error -> {
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
            PFMAppState.Loading -> {}
            is PFMAppState.Success -> {
                marsAdapter.setData(appState.pictureFromMarsDTO.photos)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarsFragment()
    }
}