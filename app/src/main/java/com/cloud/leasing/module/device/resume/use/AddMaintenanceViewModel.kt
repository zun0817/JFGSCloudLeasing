package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.*
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddMaintenanceViewModel : BaseViewModel() {

    val submitLiveData = MutableLiveData<Result<String>>()

    val addMaintenanceLiveData = MutableLiveData<Result<MutableList<AddMaintenanceBean>>>()

    @PageName
    override fun getPageName() = PageName.ADD_MAINTENANCE

    fun requestOfAddMaintenance(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfAddMaintenance(resumeId)
            addMaintenanceLiveData.value = result
        }
    }

    fun requestOfSubmitMaintenance(
        resumeId: Int,
        dateTime: String,
        usedItemList: MutableList<ParamMaintenanceBean>) {
        viewModelScope.launch {
            val param = getSubmitMaintenanceParam(resumeId, dateTime, usedItemList)
            val result = NetworkApi.requestOfSubmitMaintenance(param)
            submitLiveData.value = result
        }
    }

    private fun getSubmitMaintenanceParam(
        resumeId: Int,
        dateTime: String,
        usedItemList: MutableList<ParamMaintenanceBean>
    ): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["resumeId"] = resumeId
        map["dateTime"] = dateTime
        map["usedItemList"] = usedItemList
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }
}