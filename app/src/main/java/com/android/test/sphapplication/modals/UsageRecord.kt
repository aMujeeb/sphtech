package com.android.test.sphapplication.modals

import com.android.test.sphapplication.rest.APIClient
import com.android.test.sphapplication.utils.AppConstants

class UsageRecord {

    var volume_of_mobile_data : String = ""
    var quarter : String =""
    var _id : Int = 0

    companion object {
        fun requestQuarterDetails() {
            APIClient().requestQuarterRecordsList(AppConstants.RESOURCE_ID, 10)
        }

        fun requestQuarterDetailsByOffset(offset : Int) {
            APIClient().requestQuarterRecordsListByOffset(AppConstants.RESOURCE_ID, offset, 10)
        }
    }

    //https://data.gov.sg/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=10
}