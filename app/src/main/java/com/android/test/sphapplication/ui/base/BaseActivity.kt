package com.android.test.sphapplication.ui.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.android.test.sphapplication.R

/**
 * Created by Mujee on 2/22/19 2019 - 02 - 22.
 */
class BaseActivity : AppCompatActivity() {

    protected var           mFragmentManager : FragmentManager = supportFragmentManager;
    private var             mIsBackNavigationEnabled : Boolean = false
    protected lateinit var  mSelectedFragment : BaseFragment
    protected lateinit var  mTitle : CharSequence
    var                     mAlertBuilder : AlertDialog.Builder? = null
    var                     mAlertDialog : AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
    }

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
    }

    public fun replaceFragment(fragment: BaseFragment, tag : String) {
        mSelectedFragment = fragment
        mTitle = title
        var transaction : FragmentTransaction = mFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

    public fun pushFragment(fragment: BaseFragment, tag : String) {
        mSelectedFragment = fragment
        mTitle = title
        var transaction : FragmentTransaction = mFragmentManager.beginTransaction()
        transaction.add(R.id.frameContainer, fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

    public fun replaceRootFragment(fragment: BaseFragment, tag : String) {
        clearFragmentBackStackList()
        mSelectedFragment = fragment
        mTitle = title
        var transaction : FragmentTransaction = mFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment, tag)
        transaction.commit()
    }


    public fun clearFragmentBackStackList() {
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        for (i in 0 until mFragmentManager.backStackEntryCount) {
            mFragmentManager.popBackStack()
        }
    }

    public fun getBackStackCount() : Int {
        return mFragmentManager.backStackEntryCount
    }

    protected fun exitApplication() {
        finishAffinity()
        System.exit(0)
    }

    fun displayGeneralErrorMessage(errMessage: String) {
        Handler(Looper.getMainLooper()).post(Runnable {
            mAlertBuilder = AlertDialog.Builder(this)

            mAlertBuilder!!.setMessage(errMessage)
            mAlertBuilder!!.setTitle(resources.getString(R.string.app_name))
            mAlertBuilder!!.setPositiveButton(resources.getString(R.string.ok)) { dialogInterface, i -> dialogInterface.dismiss() }

            if(mAlertDialog != null){
                mAlertDialog!!.dismiss()
            }

            mAlertDialog = mAlertBuilder!!.create()
            mAlertDialog!!.show()
        })
    }

    fun displayGeneralErrorMessage(resId: Int) {
        displayGeneralErrorMessage(resources.getString(resId))
    }
}