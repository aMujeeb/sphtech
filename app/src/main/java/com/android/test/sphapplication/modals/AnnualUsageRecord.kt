package com.android.test.sphapplication.modals

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.android.test.sphapplication.db.AppDataBase

@Entity
class AnnualUsageRecord {

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null

    var mAnnualSum : Double = 0.0
    var mYear : String = ""
    var mNumberOfQuarters : Int = 0

    companion object {
        fun getAllSavedRecords(appDataBase: AppDataBase) : List<AnnualUsageRecord> {
            return appDataBase.annualUsageRecordDao().getAll()
        }
    }

    fun saveRecord(appDataBase: AppDataBase) {
        appDataBase.annualUsageRecordDao().addNewAnnualUsageRecord(this)
    }
}