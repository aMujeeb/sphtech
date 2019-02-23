package com.android.test.sphapplication.utils

import android.content.Context
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager



class NetWorkUtil {
    companion object {
        fun isNetworkAvailable(context : Context) : Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}