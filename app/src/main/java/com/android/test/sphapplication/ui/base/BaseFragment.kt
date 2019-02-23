package com.android.test.sphapplication.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by Mujee on 2/22/19 2019 - 02 - 22.
 */
class BaseFragment : Fragment() {

    private lateinit var        mBaseActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseActivity = activity as BaseActivity
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}