package com.likemagic.nasaphotos.ViewModel.picOfEarthviewModel

import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.repository.earthDTO.PictureOfEarthDTO

sealed class POEAppState{
    data class Success(val pictureOfEarthDTO: PictureOfEarthDTO): POEAppState()
    data class Error(val error: AppError): POEAppState()
    object Loading: POEAppState()
}
