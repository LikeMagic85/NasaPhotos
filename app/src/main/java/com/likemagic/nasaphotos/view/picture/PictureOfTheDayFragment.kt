package com.likemagic.nasaphotos.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.ViewModel.POTDAppState
import com.likemagic.nasaphotos.ViewModel.POTDViewModel
import com.likemagic.nasaphotos.databinding.FragmentPictureOfTheDayBinding
import com.likemagic.nasaphotos.view.MainActivity

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
    get() {
        return _binding!!
    }

    private val viewModel:POTDViewModel by lazy {
        ViewModelProvider(this)[POTDViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getLiveData().observe(viewLifecycleOwner){
            renderData(it)
        }
        viewModel.sendRequest()
        findOnWiki()
        setBottomAppBar()
    }

    private fun bottomSheetBehaviorSettings(){
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.bottomSheetContainer)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                TODO("Not yet implemented")
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                slideOffset
            }
        })
    }

    private fun setBottomAppBar(){
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

         when(item.itemId){
            R.id.app_bar_fav -> {}
            R.id.app_bar_settings -> {}
             android.R.id.home ->{
                BottomNavigationDrawerFragment.newInstance().show(requireActivity().supportFragmentManager, "")
             }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(appState: POTDAppState){
        when(appState){
            is POTDAppState.Error -> {
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
            POTDAppState.Loading -> {

            }
            is POTDAppState.Success -> {
                binding.imageView.load(appState.pictureOfTheDayDTO.url)
                binding.bottomSheet.title.text = appState.pictureOfTheDayDTO.title
                binding.bottomSheet.explanation.text = appState.pictureOfTheDayDTO.explanation
            }
        }
    }

    private fun findOnWiki(){
        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}