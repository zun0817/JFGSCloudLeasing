package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.FaultLedgerBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class FaultLedgerViewModel : BaseViewModel() {

    val faultLedgerLiveData = MutableLiveData<Result<FaultLedgerBean>>()

    @PageName
    override fun getPageName() = PageName.FAULT_LEDGER

    fun requestOfResumeFaultLedger(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfResumeFaultLedger(resumeId)
            faultLedgerLiveData.value = result
        }
    }
}