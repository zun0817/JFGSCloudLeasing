package com.cloud.leasing.module.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.DeviceDetailBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class DeviceDetailViewModel : BaseViewModel() {

    val deviceDetailLiveData = MutableLiveData<Result<DeviceDetailBean>>()

    @PageName
    override fun getPageName() = PageName.DEVICE_DETAIL

    fun requestOfDeviceDetail(deviceId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDeviceDetail(deviceId)
            deviceDetailLiveData.value = result
        }
    }

}