package com.likemagic.nasaphotos.repository.earthRetrofit

import com.likemagic.nasaphotos.repository.earthDTO.PictureOfEarthDTO
import com.likemagic.nasaphotos.repository.marsDTO.PictureFromMarsDTO
import com.likemagic.nasaphotos.utils.NASA_END_POINT_EARTH
import com.likemagic.nasaphotos.utils.NASA_END_POINT_MARS
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfEarthAPI {
    @GET(NASA_END_POINT_EARTH)
    fun getPictureOfEarth(@Query("api_key") apiKey:String): Call<PictureOfEarthDTO>
}