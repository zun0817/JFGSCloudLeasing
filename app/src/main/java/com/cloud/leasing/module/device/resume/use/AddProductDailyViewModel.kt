package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.ProductDailyFaultBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddProductDailyViewModel : BaseViewModel() {

    val addDailyLiveData = MutableLiveData<Result<String>>()

    override fun getPageName() = PageName.ADD_PRODUCT_DAILY

    fun requestOfAddProductDaily(
        resumeId: Int,
        dateTime: String?,
        tunneMeters: String?,
        currentGeology: String?,
        progressRatio: String?,
        dailyRings: String?,
        maxDailyRings: String?,
        cumRings: String?,
        dailyRecord: String?,
        produceMatter: String?,
        peopleStaffing: String?,
        deviceStatusDescribe: String?,
        isFault: Int,
        drivingType: String?,
        faultTime: String?,
        produceDailyFaultList: MutableList<ProductDailyFaultBean>
    ) {
        viewModelScope.launch {
            val param = getAddDailyParam(
                resumeId,
                dateTime,
                tunneMeters,
                currentGeology,
                progressRatio,
                dailyRings,
                maxDailyRings,
                cumRings,
                dailyRecord,
                produceMatter,
                peopleStaffing,
                deviceStatusDescribe,
                isFault,
                drivingType,
                faultTime,
                produceDailyFaultList
            )
            val result = NetworkApi.requestOfAddProductDaily(param)
            addDailyLiveData.value = result
        }
    }

    private fun getAddDailyParam(
        resumeId: Int,
        dateTime: String?,
        tunneMeters: String?,
        currentGeology: String?,
        progressRatio: String?,
        dailyRings: String?,
        maxDailyRings: String?,
        cumRings: String?,
        dailyRecord: String?,
        produceMatter: String?,
        peopleStaffing: String?,
        deviceStatusDescribe: String?,
        isFault: Int,
        drivingType: String?,
        faultTime: String?,
        produceDailyFaultList: MutableList<ProductDailyFaultBean>
    ): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["resumeId"] = resumeId
        map["dateTime"] = dateTime
        map["tunneMeters"] = tunneMeters
        map["currentGeology"] = currentGeology
        map["progressRatio"] = progressRatio
        map["dailyRings"] = dailyRings
        map["maxDailyRings"] = maxDailyRings
        map["cumRings"] = cumRings
        map["dailyRecord"] = dailyRecord
        map["produceMatter"] = produceMatter
        map["peopleStaffing"] = peopleStaffing
        map["deviceStatusDescribe"] = deviceStatusDescribe
        map["isFault"] = isFault
        map["drivingType"] = drivingType
        map["faultTime"] = faultTime
        map["produceDailyFaultList"] = produceDailyFaultList
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }
}