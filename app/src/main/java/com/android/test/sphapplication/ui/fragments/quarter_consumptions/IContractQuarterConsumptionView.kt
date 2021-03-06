package com.android.test.sphapplication.ui.fragments.quarter_consumptions

import android.content.Context
import com.android.test.sphapplication.modals.AnnualUsageRecord
import com.android.test.sphapplication.modals.UsageRecord

//Contract interface for quarter data view (Following MVP Architecture)

interface IContractQuarterConsumptionView {

    interface QuarterConsumptionView {
        fun showProgress()
        fun hideProgress()
        fun showError(error : String)
        fun showError(error : Int)
        fun loadViewData()
    }

    interface QuarterConsumptionPresenter {
        fun setView(view : QuarterConsumptionView,context: Context)
        fun requestQuarterData()
        fun requestQuarterDataByLimit()
        fun onDestroy()
        fun getUsageRecords() : ArrayList<UsageRecord>
        fun getAnnualUsageRecords() : List<AnnualUsageRecord>
     }
}