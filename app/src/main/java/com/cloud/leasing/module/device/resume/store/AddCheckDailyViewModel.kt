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

    val addDepositLiveData = MutableLiveData<Result<String>>()

    val checkItemLiveData = MutableLiveData<Result<MutableList<CheckDailyItemBean>>>()

    override fun getPageName() = PageName.STORE_CHECK_DAILY

    fun requestOfStoreCheck(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfStoreCheck(resumeId)
            checkItemLiveData.value = result
        }
    }

    fun requestOfSubmitDeposit(
        resumeId: Int,
        dateTime: String,
        coordinateMatter: String,
        depositCheckInfoList: MutableList<String>
    ) {
        viewModelScope.launch {
            val param = getAddFaultDailyParam(
                resumeId,
                dateTime,
                coordinateMatter,
                depositCheckInfoList
            )
            val result = NetworkApi.requestOfSubmitDeposit(param)
            addDepositLiveData.value = result
        }
    }

    private fun getAddFaultDailyParam(
        resumeId: Int,
        dateTime: String,
        coordinateMatter: String,
        depositCheckInfoList: MutableList<String>
    ): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["resumeId"] = resumeId
        map["dateTime"] = dateTime
        map["coordinateMatter"] = coordinateMatter
        map["depositCheckInfoList"] = depositCheckInfoList
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }
}