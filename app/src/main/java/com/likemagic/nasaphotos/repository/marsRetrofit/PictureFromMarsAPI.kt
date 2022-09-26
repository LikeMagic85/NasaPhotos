package com.likemagic.nasaphotos.repository.marsRetrofit

import com.likemagic.nasaphotos.repository.marsDTO.PictureFromMarsDTO
import com.likemagic.nasaphotos.utils.NASA_END_POINT_MARS
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureFromMarsAPI {
    @GET(NASA_END_POINT_MARS)
    fun getPictureFromMars(@Query("api_key") apiKey:String, @Query("earth_date") start: String): Call<PictureFromMarsDTO>
}