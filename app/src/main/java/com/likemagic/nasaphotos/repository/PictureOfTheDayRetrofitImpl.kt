package com.likemagic.nasaphotos.repository

import com.google.gson.GsonBuilder
import com.likemagic.nasaphotos.NASA_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfTheDayRetrofitImpl {

    private val nasaBaseURL = NASA_BASE_URL

    private val pictureOfTheDayRetrofitImpl = Retrofit.Builder()
        .baseUrl(nasaBaseURL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    fun getRetrofit():PictureOfTheDayAPI{
        return pictureOfTheDayRetrofitImpl.create(PictureOfTheDayAPI::class.java)
    }
}