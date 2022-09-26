package com.likemagic.nasaphotos.ViewModel.potdViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.likemagic.nasaphotos.BuildConfig
import com.likemagic.nasaphotos.ViewModel.AppError
import com.likemagic.nasaphotos.repository.POTDRetrofit.PictureOfTheDayRetrofitImpl
import com.likemagic.nasaphotos.repository.potdDTO.PictureOfTheDayDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class POTDViewModel(
    val liveData: MutableLiveData<POTDAppState> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<POTDAppState> {
        return liveData
    }

    private fun getModifiedDate(): String {
        val cal = Calendar.getInstance()
        val fmt = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DATE, -2);
        return fmt.format(cal.time)
    }

    fun sendRequest() {
        liveData.postValue(POTDAppState.Loading)
        pictureOfTheDayRetrofitImpl.getRetrofit().getPictureOfTheDay(BuildConfig.NASA_API_KEY, getModifiedDate())
            .enqueue(callback)
    }

    private val callback = object : Callback<PictureOfTheDayDTO> {
        override fun onResponse(
            call: Call<PictureOfTheDayDTO>,
            response: Response<PictureOfTheDayDTO>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(POTDAppState.Success(it))
                }
            } else if(response.code() in 400..499) {
                liveData.postValue(POTDAppState.Error(AppError.ErrorClient))
            }
            else if(response.code() in 500..599) {
                liveData.postValue(POTDAppState.Error(AppError.ErrorServer))
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayDTO>, t: Throwable) {
            liveData.postValue(POTDAppState.Error(AppError.ErrorUnknown))
        }

    }
}