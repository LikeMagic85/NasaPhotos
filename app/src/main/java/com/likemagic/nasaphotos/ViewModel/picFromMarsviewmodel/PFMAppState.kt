package com.likemagic.nasaphotos.ViewModel.picFromMarsviewmodel

import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.repository.marsDTO.PictureFromMarsDTO

sealed class PFMAppState{
    data class Success(val pictureFromMarsDTO: PictureFromMarsDTO): PFMAppState()
    data class Error(val error: AppError): PFMAppState()
    object Loading: PFMAppState()
}
