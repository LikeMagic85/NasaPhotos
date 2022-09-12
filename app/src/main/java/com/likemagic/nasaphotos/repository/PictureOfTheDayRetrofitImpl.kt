package com.likemagic.nasaphotos.repository

import com.google.gson.GsonBuilder
import com.likemagic.nasaphotos.utils.NASA_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfTheDayRetrofitImpl {

    private val pictureOfTheDayRetrofitImpl = Retrofit.Builder()
        .baseUrl(NASA_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    fun getRetrofit():PictureOfTheDayAPI{
        return pictureOfTheDayRetrofitImpl.create(PictureOfTheDayAPI::class.java)
    }
}