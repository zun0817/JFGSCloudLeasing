package com.cloud.leasing.module.device

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.CityBean
import com.cloud.leasing.bean.DeviceManageDetailBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class DeviceManageDetailViewModel : BaseViewModel() {

    val cityLiveData = MutableLiveData<Result<MutableList<CityBean>>>()

    val deviceDetailLiveData = MutableLiveData<Result<DeviceManageDetailBean>>()

    @PageName
    override fun getPageName() = PageName.DEVICE_MANAGE_DETAIL

    fun requestOfDeviceManageDetail(deviceId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDeviceManageDetail(deviceId)
            deviceDetailLiveData.value = result
        }
    }

    fun requestOfCityList(areaCode: String) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfCityList(areaCode)
            cityLiveData.value = result
        }
    }

}