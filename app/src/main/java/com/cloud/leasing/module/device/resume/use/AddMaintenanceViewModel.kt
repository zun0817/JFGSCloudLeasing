package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.AddMaintenanceBean
import com.cloud.leasing.bean.FaultLedgerBean
import com.cloud.leasing.bean.ManageFileBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class AddMaintenanceViewModel : BaseViewModel() {

    val addMaintenanceLiveData = MutableLiveData<Result<MutableList<AddMaintenanceBean>>>()

    @PageName
    override fun getPageName() = PageName.ADD_MAINTENANCE

    fun requestOfAddMaintenance(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfAddMaintenance(resumeId)
            addMaintenanceLiveData.value = result
        }
    }
}