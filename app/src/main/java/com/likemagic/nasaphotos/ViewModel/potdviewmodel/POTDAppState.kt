package com.likemagic.nasaphotos.ViewModel.potdviewmodel

import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.repository.potdDTO.PictureOfTheDayDTO

sealed class POTDAppState{
    data class Success(val pictureOfTheDayDTO: PictureOfTheDayDTO): POTDAppState()
    data class Error(val error: AppError): POTDAppState()
    object Loading: POTDAppState()
}
