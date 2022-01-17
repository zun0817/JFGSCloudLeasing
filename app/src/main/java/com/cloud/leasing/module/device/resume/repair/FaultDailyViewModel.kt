package com.cloud.leasing.module.device.resume.repair

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.RepairDailyBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class FaultDailyViewModel : BaseViewModel() {

    val faultDailyLiveData = MutableLiveData<Result<RepairDailyBean>>()

    @PageName
    override fun getPageName() = PageName.FAULT_DAILY

    fun requestOfResumeFaultDaily(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfResumeFaultDaily(resumeId)
            faultDailyLiveData.value = result
        }
    }
}