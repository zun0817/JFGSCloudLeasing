package com.cloud.leasing.module.device.resume

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.ResumeDetailBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class DeviceResumeDetailViewModel : BaseViewModel() {

    val resumeDetailLiveData = MutableLiveData<Result<ResumeDetailBean>>()

    val downloadFileLiveData = MutableLiveData<Result<ResponseBody>>()

    @PageName
    override fun getPageName() = PageName.DEVICE_RESUME_DETAIL

    fun requestOfDeviceResume(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfResumeDetail(resumeId)
            resumeDetailLiveData.value = result
        }
    }

    fun requestOfDownloadFile(fileName: String, path: String) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDownloadFile(fileName, path)
            downloadFileLiveData.value = result
        }
    }

}