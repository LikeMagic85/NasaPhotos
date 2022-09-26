package com.likemagic.nasaphotos.repository.marsRetrofit

import com.google.gson.GsonBuilder
import com.likemagic.nasaphotos.utils.NASA_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureFromMarsRetrofitImpl {

    private val pictureFromMarsRetrofitImpl = Retrofit.Builder()
        .baseUrl(NASA_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    fun getRetrofit(): PictureFromMarsAPI {
        return pictureFromMarsRetrofitImpl.create(PictureFromMarsAPI::class.java)
    }
}