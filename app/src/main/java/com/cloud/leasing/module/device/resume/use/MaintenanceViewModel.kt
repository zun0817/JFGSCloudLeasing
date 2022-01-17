package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.DeviceResumeBean
import com.cloud.leasing.bean.MaintenanceBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class MaintenanceViewModel : BaseViewModel() {

    val maintenanceLiveData = MutableLiveData<Result<MaintenanceBean>>()

    @PageName
    override fun getPageName() = PageName.MAINTENANCE

    fun requestOfResumeMaintenance(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfResumeMaintenance(resumeId)
            maintenanceLiveData.value = result
        }
    }
}