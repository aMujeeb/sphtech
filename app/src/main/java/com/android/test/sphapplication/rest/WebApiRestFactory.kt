package com.android.test.sphapplication.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebApiRestFactory {

    companion object {
        var url : String = ""
        fun newInstance(webCallInterceptor: WebApiRequestInterceptor): SPHAPIService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            //Setting login interceptor & time outs respectively
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(httpLoggingInterceptor)
            builder.addInterceptor(webCallInterceptor)
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.writeTimeout(60, TimeUnit.SECONDS)

            //base url
            url = "https://data.gov.sg/api/"

            //Adding GSon converter
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()

            return retrofit.create(SPHAPIService::class.java!!)
        }
    }
}