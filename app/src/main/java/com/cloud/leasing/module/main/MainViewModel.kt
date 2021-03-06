package com.cloud.leasing.module.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.DeviceTypeBean
import com.cloud.leasing.bean.UpdateVersionBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    val deviceTypeLiveData = MutableLiveData<Result<DeviceTypeBean>>()

    val updateVersionLiveData = MutableLiveData<Result<UpdateVersionBean>>()

    @PageName
    override fun getPageName() = PageName.HOME

    fun requestOfDeviceType() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDeviceType()
            deviceTypeLiveData.value = result
        }
    }

    fun requestOfUpdateVersion() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfUpdateVersion(1)
            updateVersionLiveData.value = result
        }
    }

}