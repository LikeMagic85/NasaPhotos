package com.likemagic.nasaphotos.repository.earthRetrofit

import com.google.gson.GsonBuilder
import com.likemagic.nasaphotos.utils.NASA_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfEarthRetrofitImpl {

    private val pictureOfEarthRetrofitImpl = Retrofit.Builder()
        .baseUrl(NASA_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    fun getRetrofit(): PictureOfEarthAPI {
        return pictureOfEarthRetrofitImpl.create(PictureOfEarthAPI::class.java)
    }
}