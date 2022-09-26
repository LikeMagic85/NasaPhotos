package com.likemagic.nasaphotos.ViewModel

sealed class AppError{
    object ErrorClient: AppError()
    object ErrorServer: AppError()
    object ErrorUnknown: AppError()
}
