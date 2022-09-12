package com.likemagic.nasaphotos.repository

import com.likemagic.nasaphotos.NASA_END_POINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET(NASA_END_POINT)
    fun getPictureOfTheDay(@Query("api_key") apiKey:String): Call<PictureOfTheDayDTO>
}