package com.android.test.sphapplication.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by Mujee on 2/22/19 2019 - 02 - 22.
 */
open class BaseFragment : Fragment() {

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

    fun addFragment(fragment: BaseFragment, tag: String) {
        mBaseActivity?.pushFragment(fragment, tag)
    }

    fun pushFragment(fragment: BaseFragment, tag: String) {
        mBaseActivity?.pushFragment(fragment, tag)
    }

    fun replaceFragment(fragment: BaseFragment, tag: String) {
        mBaseActivity?.replaceFragment(fragment, tag)
    }

    fun replaceRootFragment(fragment: BaseFragment, tag: String) {
        mBaseActivity?.replaceRootFragment(fragment, tag)
    }

    fun setTitle(resId: Int) {
        if (resId != -1) {
            mBaseActivity.setTitle(resId)
        }
    }

    fun setTitle(title: CharSequence) {
        mBaseActivity.setTitle(title)
    }

    open fun getSupportActivity(): BaseActivity {
        if(activity != null) {
            return activity as BaseActivity
        } else {
            return BaseActivity()
        }
    }

    fun displayGeneralErrorMessage(errMessage: String) {
        mBaseActivity?.displayGeneralErrorMessage(errMessage)
    }

    fun displayGeneralErrorMessage(resId: Int) {
        mBaseActivity?.displayGeneralErrorMessage(resId)
    }
}