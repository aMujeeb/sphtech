package com.android.test.sphapplication.rest

//interface containing function signatures to be implemented
interface APIClientService {
    fun requestQuarterRecordsList(resourceID : String, limit : Int)
    fun requestQuarterRecordsListByOffset(resourceID : String, offset : Int, limit : Int)
}