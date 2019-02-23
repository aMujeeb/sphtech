package com.android.test.sphapplication.ui.fragments.quarter_consumptions

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.test.sphapplication.R
import com.android.test.sphapplication.ui.adapters.QuarterDetailsAdapter
import com.android.test.sphapplication.ui.base.BaseFragment
import com.android.test.sphapplication.utils.LogMessageUtils
import kotlinx.android.synthetic.main.fragment_consumption.*

class QuarterConsumptionsFragment : BaseFragment(), IContractQuarterConsumptionView.QuarterConsumptionView {

    private lateinit var                    mView: View
    private lateinit var                    mQuarterPresenter : IContractQuarterConsumptionView.QuarterConsumptionPresenter
    private lateinit var                    mQuarterDetailsAdapter: QuarterDetailsAdapter
    private lateinit var                    mItemDecorator : DividerItemDecoration

    companion object {
        fun getInstance() : QuarterConsumptionsFragment {
            var instance = QuarterConsumptionsFragment()
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_consumption, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogMessageUtils.getInstance().logMessage("Loading Fragment")
        mQuarterPresenter = ImplQuarterConsumptionPresenter()
        mQuarterPresenter.setView(this)

        mLstQuarterDetails.layoutManager = LinearLayoutManager(getSupportActivity(), LinearLayoutManager.VERTICAL, false)
        mItemDecorator = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        mItemDecorator.setDrawable(ContextCompat.getDrawable(getSupportActivity(), R.drawable.item_divider)!!)
        mLstQuarterDetails.addItemDecoration(mItemDecorator)
        mQuarterDetailsAdapter = QuarterDetailsAdapter(getSupportActivity(), mQuarterPresenter)
        mLstQuarterDetails.adapter = mQuarterDetailsAdapter

        mQuarterPresenter.requestQuarterData()

        mLstQuarterDetails.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    mQuarterPresenter.requestQuarterDataByLimit()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mQuarterPresenter.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        mQuarterPresenter.onDestroy()
    }

    override fun showProgress() {
        mProgressQuarter.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        Handler(Looper.getMainLooper()).post(Runnable {
            if(mProgressQuarter != null){
                mProgressQuarter.visibility = View.GONE
            }
        })
    }

    override fun showError(error: String) {
        Handler(Looper.getMainLooper()).post(Runnable {
            displayGeneralErrorMessage(error)
        })
    }

    override fun showError(error: Int) {
        Handler(Looper.getMainLooper()).post(Runnable {
            displayGeneralErrorMessage(error)
        })
    }

    override fun loadViewData() {
        Handler(Looper.getMainLooper()).post(Runnable {
            if(mQuarterDetailsAdapter != null && mLstQuarterDetails != null) {
                mQuarterDetailsAdapter.updateItems()
                mQuarterDetailsAdapter.notifyDataSetChanged()
            }
        })
    }
}