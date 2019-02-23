package com.android.test.sphapplication.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.test.sphapplication.R
import com.android.test.sphapplication.modals.AnnualUsageRecord
import com.android.test.sphapplication.ui.adapters.view_holders.QuarterDataViewHolder
import com.android.test.sphapplication.ui.fragments.quarter_consumptions.IContractQuarterConsumptionView

class QuarterDetailsAdapter(var context : Context, var quarterConsumptionPresenter : IContractQuarterConsumptionView.QuarterConsumptionPresenter) : RecyclerView.Adapter<QuarterDataViewHolder>(){

    private var annualUsageRecords : List<AnnualUsageRecord> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): QuarterDataViewHolder {
        return QuarterDataViewHolder(LayoutInflater.from(context).inflate(R.layout.item_quarter, p0, false))
    }

    override fun getItemCount(): Int {
        return annualUsageRecords.size
    }

    override fun onBindViewHolder(p0: QuarterDataViewHolder, p1: Int) {
        p0.bindViewValues(annualUsageRecords[p1])
    }

    fun updateItems() {
        annualUsageRecords = quarterConsumptionPresenter.getAnnualUsageRecords()
    }
}