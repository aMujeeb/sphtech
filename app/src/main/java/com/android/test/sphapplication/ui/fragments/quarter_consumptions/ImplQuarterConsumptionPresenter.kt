package com.android.test.sphapplication.ui.fragments.quarter_consumptions

import android.content.Context
import com.android.test.sphapplication.R
import com.android.test.sphapplication.db.AppDataBase
import com.android.test.sphapplication.modals.AnnualUsageRecord
import com.android.test.sphapplication.modals.ErrorEvent
import com.android.test.sphapplication.modals.UsageRecord
import com.android.test.sphapplication.rest.responses.UsageDataResponse
import com.android.test.sphapplication.utils.LogMessageUtils
import com.android.test.sphapplication.utils.NetWorkUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import java.util.Objects.compare
import java.util.regex.PatternSyntaxException


class ImplQuarterConsumptionPresenter : IContractQuarterConsumptionView.QuarterConsumptionPresenter {

    private lateinit var                            mQuarterView : IContractQuarterConsumptionView.QuarterConsumptionView
    private lateinit var                            mEventBus: EventBus
    private lateinit var                            mContext: Context

    private var                                     mUsageRecords : ArrayList<UsageRecord> = ArrayList()
    private var                                     mAnnualUsageRecords : List<AnnualUsageRecord> = ArrayList()
    private var                                     mLimitCount : Int = 0
    private var                                     mTotalCount : Int = 0
    private var                                     mIsRequestSent : Boolean = true
    private var                                     mYearlyMap : MutableMap<String, AnnualUsageRecord> = HashMap()
    private lateinit var                            mAppDataBase: AppDataBase

    override fun setView(view: IContractQuarterConsumptionView.QuarterConsumptionView, context: Context) {
        mQuarterView = view
        mContext = context
        mEventBus = EventBus.getDefault()
        if(!mEventBus.isRegistered(this)) {
            mEventBus.register(this)
        }
        mAppDataBase = AppDataBase.getAppDataBase(mContext)!!
    }

    override fun requestQuarterData() {
        if(NetWorkUtil.isNetworkAvailable(mContext)) {
            mQuarterView.showProgress()
            UsageRecord.requestQuarterDetails()
        } else {
            mQuarterView.showError(R.string.no_network_available_get_from_db)
            Thread(Runnable {
                sortAnnualDataByYear(mAppDataBase.annualUsageRecordDao().getAll() as ArrayList<AnnualUsageRecord>)
                mQuarterView.loadViewData()
            }).start()
        }
    }

    override fun requestQuarterDataByLimit() {
        if(NetWorkUtil.isNetworkAvailable(mContext)) {
            if (mIsRequestSent) {
                if (mUsageRecords.size <= mTotalCount) {
                    mQuarterView.showProgress()
                    UsageRecord.requestQuarterDetailsByOffset(mLimitCount)
                    mIsRequestSent = false
                }
            }
        } else {
            mQuarterView.showError(R.string.no_network_available)
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

                for (usageRecord : UsageRecord in usageDataResponse.result!!.records!!) {
                    try {
                        var annualUsageRecord = AnnualUsageRecord()
                        val splitArray = usageRecord.quarter.split("-")
                        LogMessageUtils.getInstance().logMessage("Item Split :" + splitArray[0])
                        if(!mYearlyMap.containsKey(splitArray[0])) {
                            annualUsageRecord.mYear = splitArray[0]
                            annualUsageRecord.mNumberOfQuarters = 1
                            annualUsageRecord.mAnnualSum = usageRecord.volume_of_mobile_data.toDouble()
                            mYearlyMap.put(splitArray[0], annualUsageRecord)
                        } else {
                            annualUsageRecord = mYearlyMap.get(splitArray[0])!!
                            annualUsageRecord.mAnnualSum += usageRecord.volume_of_mobile_data.toDouble()
                            annualUsageRecord.mNumberOfQuarters++
                            mYearlyMap.put(splitArray[0], annualUsageRecord)
                        }
                        Thread(Runnable {
                            annualUsageRecord.saveRecord(mAppDataBase)
                        }).start()
                    } catch (ex: PatternSyntaxException) {
                        LogMessageUtils.getInstance().logMessage("Exception")
                    }
                }
                sortAnnualDataByYear(ArrayList(mYearlyMap.values))
                mQuarterView.loadViewData()
            }
        }
    }

    override fun getUsageRecords(): ArrayList<UsageRecord> {
        return mUsageRecords
    }

    override fun getAnnualUsageRecords(): List<AnnualUsageRecord> {
        return mAnnualUsageRecords
    }

    private fun sortAnnualDataByYear(valueList : ArrayList<AnnualUsageRecord>) {
        mAnnualUsageRecords = valueList.sortedWith(compareBy { it.mYear })
    }
}