package com.android.test.sphapplication.utils

import android.util.Log

/**
 * Created by Mujee on 2/22/19 2019 - 02 - 22.
 */

//Class to display application related logs if required
class LogMessageUtils {
    companion object {
        private var instance : LogMessageUtils? = null
        fun getInstance() : LogMessageUtils {
            if(instance == null) {
                instance = LogMessageUtils()
            }
            return instance as LogMessageUtils
        }
    }

    fun logMessage(message : String){
        Log.d(AppConstants.APP_TAG, message)
    }
}