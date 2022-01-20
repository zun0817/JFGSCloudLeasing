package com.cloud.leasing.module.device.resume.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.CheckDailyItemBean
import com.cloud.leasing.bean.CompanyFileBean
import com.cloud.leasing.bean.ProductDailyFaultBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddCheckDailyViewModel : BaseViewModel() {

    val addDailyLiveData = MutableLiveData<Result<String>>()

    val checkItemLiveData = MutableLiveData<Result<MutableList<CheckDailyItemBean>>>()

    override fun getPageName() = PageName.STORE_CHECK_DAILY

    fun requestOfStoreCheck(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfStoreCheck(resumeId)
            checkItemLiveData.value = result
        }
    }

    fun requestOfAddProductDaily(
        resumeId: Int,
        dateTime: String?,
        ratio: String?,
        safetyAccident: String?,
        jobContent: String?,
        assistMatter: String?,
        remarks: String?,
        dailyImageUrl: String?,
        beginTime: String?,
        planTime: String?
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
                dailyImageUrl,
                beginTime, planTime
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
        dailyImageUrl: String?,
        beginTime: String?,
        planTime: String?
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
        map["beginTime"] = beginTime
        map["planTime"] = planTime
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }
}