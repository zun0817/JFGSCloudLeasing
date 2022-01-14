package com.cloud.leasing.module.device.resume

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.ResumeDetailBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class DeviceResumeDetailViewModel : BaseViewModel() {

    val resumeDetailLiveData = MutableLiveData<Result<ResumeDetailBean>>()

    @PageName
    override fun getPageName() = PageName.DEVICE_RESUME_DETAIL

    fun requestOfDeviceResume(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfResumeDetail(resumeId)
            resumeDetailLiveData.value = result
        }
    }

}