package com.android.test.sphapplication.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.android.test.sphapplication.db.dao.AnnualUsageRecordDao
import com.android.test.sphapplication.modals.AnnualUsageRecord

@Database(entities = [ AnnualUsageRecord::class ], version = 1, exportSchema = false)
open abstract class AppDataBase : RoomDatabase() {
    abstract fun annualUsageRecordDao(): AnnualUsageRecordDao

    companion object {
        var instance: AppDataBase? = null

        fun getAppDataBase(context: Context): AppDataBase? {
            if (instance == null){
                synchronized(AppDataBase::class){
                    instance = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "sph_db").build()
                }
            }
            return instance
        }

        fun destroyDataBase(){
            instance = null
        }
    }
}