package com.cloud.leasing.module.device.resume.repair

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.DeviceResumeBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class FaultDailyViewModel : BaseViewModel() {

    val resumeLiveData = MutableLiveData<Result<MutableList<DeviceResumeBean>>>()

    @PageName
    override fun getPageName() = PageName.FAULT_DAILY

    fun requestOfDeviceResume(deviceId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDeviceResume(deviceId)
            resumeLiveData.value = result
        }
    }
}