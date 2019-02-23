package com.android.test.sphapplication.rest

import com.android.test.sphapplication.utils.AppConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

//This class use to add headers to requests
class WebApiRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request : Request
        val builder = chain.request().newBuilder()
        builder.addHeader(AppConstants.AUTH_CONNECTION_TYPE, AppConstants.AUTH_CONNECTION_TYPE_VALUE)
        request = builder.build()

        return chain.proceed(request)
    }

}