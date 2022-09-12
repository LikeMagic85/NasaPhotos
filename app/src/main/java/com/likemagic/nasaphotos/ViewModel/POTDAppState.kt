package com.likemagic.nasaphotos.ViewModel

import com.likemagic.nasaphotos.repository.PictureOfTheDayDTO

sealed class POTDAppState{
    data class Success(val pictureOfTheDayDTO: PictureOfTheDayDTO):POTDAppState()
    data class Error(val error: AppError):POTDAppState()
    object Loading:POTDAppState()
}
