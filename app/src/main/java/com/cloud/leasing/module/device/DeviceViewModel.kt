package com.cloud.leasing.module.device

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.bean.DeviceManagerFormBean
import com.cloud.leasing.bean.DeviceTypeBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class DeviceViewModel : BaseRecyclerViewModel() {

    val deviceTypeLiveData = MutableLiveData<Result<DeviceTypeBean>>()

    val deviceLiveData = MutableLiveData<Result<DeviceManagerFormBean>>()

    @PageName
    override fun getPageName() = PageName.DEVICE

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            val param = getDeviceForm()
            val result = NetworkApi.requestOfDeviceManagerForm(param)
            deviceLiveData.value = result
        }
    }

    fun requestOfDeviceType() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDeviceType()
            deviceTypeLiveData.value = result
        }
    }

    private fun getDeviceForm(): MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["pageNo"] = "1"
        map["pageSize"] = "50"
        return map
    }
}