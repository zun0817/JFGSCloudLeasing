package com.cloud.leasing.module.device.resume.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.DeviceResumeBean
import com.cloud.leasing.bean.ManageDataBean
import com.cloud.leasing.bean.ManageFileBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class StoreDataViewModel : BaseViewModel() {

    val manageDataLiveData = MutableLiveData<Result<MutableList<ManageDataBean>>>()

    val manageFileLiveData = MutableLiveData<Result<MutableList<ManageFileBean>>>()

    @PageName
    override fun getPageName() = PageName.STORE_DATA

    fun requestOfManageData(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfManageData(resumeId)
            manageDataLiveData.value = result
        }
    }

    fun requestOfManageFile(manageId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfManageFile(manageId)
            manageFileLiveData.value = result
        }
    }
}