package com.cloud.leasing.module.device.resume.repair

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

class AddFaultDailyViewModel : BaseViewModel() {

    val addDailyLiveData = MutableLiveData<Result<String>>()

    override fun getPageName() = PageName.ADD_FAULT_DAILY

    fun requestOfAddProductDaily(
        resumeId: Int,
        dateTime: String?,
        ratio: String?,
        safetyAccident: String?,
        jobContent: String?,
        assistMatter: String?,
        remarks: String?,
        dailyImageUrl: String?
    ) {
        viewModelScope.launch {
            val param = getAddFaultDailyParam(
                resumeId,
                dateTime,
                ratio,
                safetyAccident,
                jobContent,
                assistMatter,
                remarks,
                dailyImageUrl
            )
            val result = NetworkApi.requestOfAddFaultDaily(param)
            addDailyLiveData.value = result
        }
    }

    private fun getAddFaultDailyParam(
        resumeId: Int,
        dateTime: String?,
        ratio: String?,
        safetyAccident: String?,
        jobContent: String?,
        assistMatter: String?,
        remarks: String?,
        dailyImageUrl: String?
    ): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["resumeId"] = resumeId
        map["dateTime"] = dateTime
        map["ratio"] = ratio
        map["safetyAccident"] = safetyAccident
        map["jobContent"] = jobContent
        map["assistMatter"] = assistMatter
        map["remarks"] = remarks
        map["dailyImageUrl"] = dailyImageUrl
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }
}