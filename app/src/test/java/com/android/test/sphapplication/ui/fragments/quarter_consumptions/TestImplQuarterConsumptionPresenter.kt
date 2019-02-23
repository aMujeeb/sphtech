package com.android.test.sphapplication.ui.fragments.quarter_consumptions

import com.android.test.sphapplication.db.AppDataBase
import com.android.test.sphapplication.utils.NetWorkUtil
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.any
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.powermock.api.mockito.PowerMockito


@RunWith(PowerMockRunner::class)
@PrepareForTest(EventBus::class, NetWorkUtil::class)
class TestImplQuarterConsumptionPresenter {

    @Mock
    lateinit var eventBus : EventBus

    @Mock
    lateinit var networkUtil : NetWorkUtil

    @Mock
    lateinit var appDataBase: AppDataBase

    lateinit var implQuarterConsumptionPresenter : ImplQuarterConsumptionPresenter

    @Before
    fun setUpTest() {
        PowerMockito.mockStatic(EventBus::class.java)
        PowerMockito.`when`(EventBus.getDefault()).thenReturn(eventBus)
        PowerMockito.mockStatic(NetWorkUtil::class.java)
        PowerMockito.mockStatic(AppDataBase::class.java)
        implQuarterConsumptionPresenter = ImplQuarterConsumptionPresenter()
    }

    @Test
    fun testSetView(){
        `when`(eventBus.isRegistered(any(ImplQuarterConsumptionPresenter::class.java))).thenReturn(false)
    }
}