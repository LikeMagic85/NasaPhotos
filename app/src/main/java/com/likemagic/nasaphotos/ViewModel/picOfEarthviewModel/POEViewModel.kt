package com.likemagic.nasaphotos.ViewModel.picOfEarthviewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.likemagic.nasaphotos.BuildConfig
import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.repository.potdDTO.PictureOfTheDayDTO
import com.likemagic.nasaphotos.repository.POTDRetrofit.PictureOfTheDayRetrofitImpl
import com.likemagic.nasaphotos.repository.earthDTO.PictureOfEarthDTO
import com.likemagic.nasaphotos.repository.earthRetrofit.PictureOfEarthRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class POEViewModel(
    val liveData: MutableLiveData<POEAppState> = MutableLiveData(),
    private val pictureOfEarthRetrofitImpl: PictureOfEarthRetrofitImpl = PictureOfEarthRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<POEAppState> {
        return liveData
    }


    fun sendRequest() {
        liveData.postValue(POEAppState.Loading)
        pictureOfEarthRetrofitImpl.getRetrofit().getPictureOfEarth(BuildConfig.NASA_API_KEY)
            .enqueue(callback)
    }

    private val callback = object : Callback<PictureOfEarthDTO> {
        override fun onResponse(
            call: Call<PictureOfEarthDTO>,
            response: Response<PictureOfEarthDTO>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(POEAppState.Success(it))
                }
            } else if(response.code() in 400..499) {
                liveData.postValue(POEAppState.Error(AppError.ErrorClient))
            }
            else if(response.code() in 500..599) {
                liveData.postValue(POEAppState.Error(AppError.ErrorServer))
            }
        }

        override fun onFailure(call: Call<PictureOfEarthDTO>, t: Throwable) {
            liveData.postValue(POEAppState.Error(AppError.ErrorUnknown))
        }

    }
}