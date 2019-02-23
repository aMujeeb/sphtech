package com.android.test.sphapplication.rest

import com.android.test.sphapplication.rest.responses.UsageDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//Interface for mapping end points based on bae URL
interface SPHAPIService {

    @GET("action/datastore_search")
    abstract fun getQuarterDetails(@Query("resource_id") resourceID : String, @Query("limit") limit : Int) : Call<UsageDataResponse>

    @GET("action/datastore_search")
    abstract fun getQuarterDetailsPageWise(@Query("resource_id") resourceID : String, @Query("offset") offset : Int, @Query("limit") limit : Int) : Call<UsageDataResponse>
}