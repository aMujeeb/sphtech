package com.android.test.sphapplication.rest

import com.android.test.sphapplication.modals.ErrorEvent
import com.android.test.sphapplication.rest.responses.UsageDataResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class APIClient :APIClientService {

    private var mSPHAPIService: SPHAPIService = WebApiRestFactory.newInstance(WebApiRequestInterceptor())

    override fun requestQuarterRecordsList(resourceID: String, limit: Int) {
        mSPHAPIService.getQuarterDetails(resourceID, limit).enqueue(object : Callback<UsageDataResponse>{
            override fun onFailure(call: Call<UsageDataResponse>, t: Throwable) {
                postErrorMessage("Failure")
            }

            override fun onResponse(call: Call<UsageDataResponse>, response: Response<UsageDataResponse>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    if (response.code() === 200) {
                        EventBus.getDefault().post(response.body())
                    } else {
                        postErrorMessage("Failure")
                    }
                } else {
                    postErrorMessage("Failure")
                }
            }

        })
    }

    override fun requestQuarterRecordsListByOffset(resourceID: String, offset: Int, limit: Int) {
        mSPHAPIService.getQuarterDetailsPageWise(resourceID, offset, limit).enqueue(object : Callback<UsageDataResponse>{
            override fun onFailure(call: Call<UsageDataResponse>, t: Throwable) {
                postErrorMessage("Failure")
            }

            override fun onResponse(call: Call<UsageDataResponse>, response: Response<UsageDataResponse>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    if (response.code() === 200) {
                        EventBus.getDefault().post(response.body())
                    } else {
                        postErrorMessage("Failure")
                    }
                } else {
                    postErrorMessage("Failure")
                }
            }

        })
    }

    private fun postErrorMessage(error: String) {
        val errorEvent = ErrorEvent()
        errorEvent.errorMessage =error
        EventBus.getDefault().post(errorEvent)
    }
}