package com.android.test.sphapplication.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.test.sphapplication.R
import com.android.test.sphapplication.ui.base.BaseActivity
import com.android.test.sphapplication.ui.fragments.quarter_consumptions.QuarterConsumptionsFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        loadMainView()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun loadMainView() {
        replaceRootFragment(QuarterConsumptionsFragment.getInstance(), "quarter")
    }
}
