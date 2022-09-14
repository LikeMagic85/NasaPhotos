package com.likemagic.nasaphotos.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.ViewModel.POTDAppState
import com.likemagic.nasaphotos.ViewModel.POTDViewModel
import com.likemagic.nasaphotos.databinding.FragmentPictureOfTheDayBinding
import com.likemagic.nasaphotos.utils.WIKI_BASE_URL
import com.likemagic.nasaphotos.view.MainActivity

class PictureOfTheDayFragment : Fragment() {
    private var isMain = true
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
            renderData(it,2)
        }
        viewModel.sendRequest()
        findOnWiki()
        setBottomAppBar()
        bottomSheetBehaviorSetup()
        setupFab()
    }

    private fun bottomSheetBehaviorSetup(){
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_DRAGGING -> {}
                    BottomSheetBehavior.STATE_COLLAPSED -> {}
                    BottomSheetBehavior.STATE_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_SETTLING -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

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

    private fun renderData(appState: POTDAppState, position:Int){
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
                if(appState.pictureOfTheDayDTO[position].mediaType == "image"){
                    binding.imageView.load(appState.pictureOfTheDayDTO[position].url)
                    binding.webView.visibility = View.GONE
                    binding.imageView.visibility = View.VISIBLE
                }else{
                    binding.imageView.visibility = View.GONE
                    binding.webView.apply {
                        visibility = View.VISIBLE
                        settings.setJavaScriptEnabled(true)
                        webChromeClient = object : WebChromeClient() {
                        }
                        loadUrl(appState.pictureOfTheDayDTO[position].url)
                    }
                }

                binding.bottomSheet.title.text = appState.pictureOfTheDayDTO[position].title
                binding.bottomSheet.explanation.text = appState.pictureOfTheDayDTO[position].explanation
                setupChips(appState)
            }
        }
    }

    private fun findOnWiki(){
        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_BASE_URL${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun setupFab(){
        binding.fab.setOnClickListener{
            if(isMain){
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_fab))
            }else{
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_plus_fab))
            }
            isMain = !isMain
        }
    }

    private fun setupChips(appState: POTDAppState){
        binding.chipGroup.setOnCheckedChangeListener{group, position ->
            group.findViewById<Chip>(position)?.let {
                if(appState is POTDAppState.Success)
                when(position){
                    1 -> {
                        renderData(appState, 2)
                    }
                    2 -> {
                        renderData(appState, 1)
                    }
                    3 -> {
                        renderData(appState, 0)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}