package com.likemagic.nasaphotos.view.picture

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.likemagic.nasaphotos.R
import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.ViewModel.potdViewModel.POTDAppState
import com.likemagic.nasaphotos.ViewModel.potdViewModel.POTDViewModel
import com.likemagic.nasaphotos.databinding.FragmentPictureOfTheDayBinding
import com.likemagic.nasaphotos.utils.APP_SETTINGS
import com.likemagic.nasaphotos.utils.IMAGE_HD
import com.likemagic.nasaphotos.utils.VIDEO_ON_YOUTUBE_APP
import com.likemagic.nasaphotos.utils.WIKI_BASE_URL
import com.likemagic.nasaphotos.view.MainActivity
import com.likemagic.nasaphotos.view.settings.SettingsFragment

class PictureOfTheDayFragment : Fragment() {
    private var isMain = true
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
    get() {
        return _binding!!
    }

    private lateinit var sp: SharedPreferences

    private val viewModel: POTDViewModel by lazy {
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
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.bottomAppBar.performHide(true)
                        binding.fab.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.bottomAppBar.performShow(true)
                        binding.fab.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.bottomAppBar.performShow(true)
                        binding.fab.visibility = View.VISIBLE
                    }
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
        binding.bottomAppBar.z = 100f
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId){
            R.id.app_bar_fav -> {}
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .addToBackStack("")
                    .add(R.id.mainContainer, SettingsFragment.newInstance())
                    .commit()
            }
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
                    sp = requireActivity().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
                    val quality = if(!sp.getBoolean(IMAGE_HD, false)){
                        appState.pictureOfTheDayDTO[position].url
                    }else{
                        appState.pictureOfTheDayDTO[position].hdurl
                    }
                    binding.imageView.load(quality){
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                    binding.webView.visibility = View.GONE
                    binding.imageView.visibility = View.VISIBLE
                }else{
                    sp = requireActivity().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
                    if(!sp.getBoolean(VIDEO_ON_YOUTUBE_APP, false)){
                        binding.imageView.visibility = View.GONE
                        binding.webView.apply {
                            visibility = View.VISIBLE
                            settings.setJavaScriptEnabled(true)
                            webChromeClient = object : WebChromeClient() {
                            }
                            loadUrl(appState.pictureOfTheDayDTO[position].url)
                        }
                    }else{
                        binding.imageView.visibility = View.GONE
                        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(appState.pictureOfTheDayDTO[position].url))
                        requireActivity().startActivity(appIntent)
                        binding.webView.apply {
                            visibility = View.VISIBLE
                            settings.setJavaScriptEnabled(true)
                            webChromeClient = object : WebChromeClient() {
                            }
                            loadUrl(appState.pictureOfTheDayDTO[position].url)
                        }
                    }

                }
                with(binding.bottomSheet){
                    title.text = appState.pictureOfTheDayDTO[position].title
                    explanation.text = appState.pictureOfTheDayDTO[position].explanation
                }

                setupChips(appState)
            }
        }
    }

    private fun findOnWiki(){
        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_BASE_URL${binding.inputEditText.text.toString()}")
                binding.inputEditText.clearFocus()
            })
        }
    }

    private fun setupFab(){
        binding.fab.setOnClickListener{
            if(isMain){
                with(binding){
                    bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_fab))
                }

            }else{
                with(binding){
                    bottomAppBar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_hamburger_menu_bottom_bar)
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_plus_fab))
                }
            }
            isMain = !isMain
        }
    }

    private fun setupChips(appState: POTDAppState){
        binding.chipGroup. setOnCheckedStateChangeListener { group, checkedIds ->
            when (group.checkedChipId) {
                group[0].id -> renderData(appState, 2)
                group[1].id -> renderData(appState, 1)
                group[2].id -> renderData(appState, 0)
            }
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}