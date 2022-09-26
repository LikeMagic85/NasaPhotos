package com.likemagic.nasaphotos.ViewModel.picSolarSystemViewModel

import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.repository.solarSystem.PhotoOfSolarSystem

sealed class SSAppState{
    data class Success(val photos:List<PhotoOfSolarSystem>): SSAppState()
    data class Error(val error: AppError): SSAppState()
    object Loading: SSAppState()
}
