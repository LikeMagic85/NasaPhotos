package com.likemagic.nasaphotos.ViewModel.pfmviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.likemagic.nasaphotos.BuildConfig
import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.repository.marsDTO.PictureFromMarsDTO
import com.likemagic.nasaphotos.repository.marsRetrofit.PictureFromMarsRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PFMViewModel(
    val liveData: MutableLiveData<PFMAppState> = MutableLiveData(),
    private val pictureFromMarsRetrofitImpl: PictureFromMarsRetrofitImpl = PictureFromMarsRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PFMAppState> {
        return liveData
    }

    private fun getDate(): String {
        val cal = Calendar.getInstance()
        val fmt = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DATE, -2)
        return fmt.format(cal.time)
    }

    fun sendRequest() {
        liveData.postValue(PFMAppState.Loading)
        pictureFromMarsRetrofitImpl.getRetrofit().getPictureFromMars(BuildConfig.NASA_API_KEY, getDate())
            .enqueue(callback)
    }

    private val callback = object : Callback<PictureFromMarsDTO> {
        override fun onResponse(
            call: Call<PictureFromMarsDTO>,
            response: Response<PictureFromMarsDTO>
        ) { Log.d("@@@", response.body()?.photos?.size.toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(PFMAppState.Success(it))
                }
            } else if(response.code() in 400..499) {
                liveData.postValue(PFMAppState.Error(AppError.ErrorClient))
            }
            else if(response.code() in 500..599) {
                liveData.postValue(PFMAppState.Error(AppError.ErrorServer))
            }
        }

        override fun onFailure(call: Call<PictureFromMarsDTO>, t: Throwable) {
            liveData.postValue(PFMAppState.Error(AppError.ErrorUnknown))
        }

    }
}