package com.cloud.leasing.module.home.more

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.HomeDeviceBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class MoreDeviceViewModel : BaseViewModel() {

    val deviceLiveData = MutableLiveData<Result<MutableList<HomeDeviceBean>>>()

    @PageName
    override fun getPageName() = PageName.MORE_DEVICE

    fun requestOfHomeDevices() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfHomeDevices()
            deviceLiveData.value = result
        }
    }
}