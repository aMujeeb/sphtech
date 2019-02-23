package com.android.test.sphapplication.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.android.test.sphapplication.modals.AnnualUsageRecord

@Dao
interface AnnualUsageRecordDao {

    @Query("SELECT * FROM annualUsageRecord")
    abstract fun getAll(): List<AnnualUsageRecord>

    @Insert
    abstract fun addNewAnnualUsageRecord(usageRecord: AnnualUsageRecord): Long?
}