package com.likemagic.nasaphotos.ViewModel.picSolarSystemViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.likemagic.nasaphotos.repository.solarSystem.getAllPhotos

class SSViewModel(
    private val liveData: MutableLiveData<SSAppState> = MutableLiveData(),

) : ViewModel() {

    fun getLiveData(): LiveData<SSAppState> {
        return liveData
    }


    fun getPhotos() {
        liveData.postValue(SSAppState.Loading)
        liveData.postValue(SSAppState.Success(getAllPhotos()))
    }

}