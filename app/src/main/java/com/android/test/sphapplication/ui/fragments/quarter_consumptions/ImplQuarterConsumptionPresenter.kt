package com.android.test.sphapplication.ui.fragments.quarter_consumptions

import com.android.test.sphapplication.R
import com.android.test.sphapplication.modals.AnnualUsageRecord
import com.android.test.sphapplication.modals.ErrorEvent
import com.android.test.sphapplication.modals.UsageRecord
import com.android.test.sphapplication.rest.responses.UsageDataResponse
import com.android.test.sphapplication.utils.LogMessageUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ImplQuarterConsumptionPresenter : IContractQuarterConsumptionView.QuarterConsumptionPresenter {

    private lateinit var                            mQuarterView : IContractQuarterConsumptionView.QuarterConsumptionView
    private lateinit var                            mEventBus: EventBus

    private var                                     mUsageRecords : ArrayList<UsageRecord> = ArrayList()
    private var                                     mAnnualUsageRecords : ArrayList<AnnualUsageRecord> = ArrayList()
    private var                                     mLimitCount : Int = 0
    private var                                     mTotalCount : Int = 0
    private var                                     mIsRequestSent : Boolean = true

    override fun setView(view: IContractQuarterConsumptionView.QuarterConsumptionView) {
        mQuarterView = view
        mEventBus = EventBus.getDefault()
        if(!mEventBus.isRegistered(this)) {
            mEventBus.register(this)
        }
    }

    override fun requestQuarterData() {
        mQuarterView.showProgress()
        UsageRecord.requestQuarterDetails()
    }

    override fun requestQuarterDataByLimit() {
        if(mIsRequestSent) {
            if (mUsageRecords.size <= mTotalCount) {
                mQuarterView.showProgress()
                UsageRecord.requestQuarterDetailsByOffset(mLimitCount)
                mIsRequestSent = false
            }
        }
    }

    override fun onDestroy() {
        mEventBus.unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onError(errorEvent: ErrorEvent) {
        mQuarterView.hideProgress()
        if(errorEvent.errorMessage.trim() != "") {
            mQuarterView.showError(errorEvent.errorMessage)
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onQuarterDetailsReceived(usageDataResponse: UsageDataResponse) {
        mQuarterView.hideProgress()
        LogMessageUtils.getInstance().logMessage("Data Recieved")
        if(usageDataResponse.result == null){
            mQuarterView.showError(R.string.no_records_available)
        } else {
            if(usageDataResponse.result!!.records!!.isEmpty() && mUsageRecords.size == 0) {
                mQuarterView.showError(R.string.no_records_available)
            } else {
                mIsRequestSent = true
                mUsageRecords.addAll(usageDataResponse.result!!.records!!)
                mLimitCount = mUsageRecords.count()
                mTotalCount = usageDataResponse.result!!.total

                mQuarterView.loadViewData()
            }
        }
    }

    override fun getUsageRecords(): ArrayList<UsageRecord> {
        return mUsageRecords
    }

    override fun getAnnualUsageRecords(): ArrayList<AnnualUsageRecord> {
        return mAnnualUsageRecords;
    }
}