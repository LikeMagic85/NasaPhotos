package com.likemagic.nasaphotos.repository.POTDRetrofit

import com.likemagic.nasaphotos.repository.potdDTO.PictureOfTheDayDTO
import com.likemagic.nasaphotos.utils.NASA_END_POINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET(NASA_END_POINT)
    fun getPictureOfTheDay(@Query("api_key") apiKey:String, @Query("start_date") start: String): Call<PictureOfTheDayDTO>
}