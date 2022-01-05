package com.cloud.leasing.module.home.have

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.DeviceTypeBean
import com.cloud.leasing.bean.ProvinceBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class AddDeviceViewModel : BaseViewModel() {

    val deviceTypeLiveData = MutableLiveData<Result<DeviceTypeBean>>()

    val provinceLiveData = MutableLiveData<Result<MutableList<ProvinceBean>>>()

    @PageName
    override fun getPageName() = PageName.ADDDEVICE

    fun requestOfDeviceType() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDeviceType()
            deviceTypeLiveData.value = result
        }
    }

    fun requestOfDeviceSite() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDeviceSite()
            provinceLiveData.value = result
        }
    }

}