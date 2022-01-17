package com.cloud.leasing.module.device.resume.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.StoreDailyBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class DailyCheckViewModel : BaseViewModel() {

    val storeDailyLiveData = MutableLiveData<Result<StoreDailyBean>>()

    @PageName
    override fun getPageName() = PageName.DAILY_CHECK

    fun requestOfResumeStoreDaily(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfResumeStoreDaily(resumeId)
            storeDailyLiveData.value = result
        }
    }
}