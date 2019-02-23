package com.android.test.sphapplication.ui.adapters.view_holders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.test.sphapplication.modals.AnnualUsageRecord
import com.android.test.sphapplication.modals.UsageRecord
import kotlinx.android.synthetic.main.item_quarter.view.*

class QuarterDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /*fun bindViewValues(annualUsageRecord: AnnualUsageRecord){
        itemView.mLblConsValue.text = annualUsageRecord.annualSum.toString()
        if(annualUsageRecord.isReduced){
            itemView.mBtnIsLow.visibility = View.VISIBLE
        } else {
            itemView.mBtnIsLow.visibility = View.INVISIBLE
        }
    }*/

    fun bindViewValues(usageRecord: UsageRecord){
        itemView.mLblConsValue.text = usageRecord.quarter.toString()
    }
}