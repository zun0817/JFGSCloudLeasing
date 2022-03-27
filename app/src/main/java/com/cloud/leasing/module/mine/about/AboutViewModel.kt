package com.cloud.leasing.module.mine.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.UpdateVersionBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class AboutViewModel : BaseViewModel() {

    val updateVersionLiveData = MutableLiveData<Result<UpdateVersionBean>>()

    @PageName
    override fun getPageName() = PageName.ABOUT

    fun requestOfUpdateVersion() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfUpdateVersion(1)
            updateVersionLiveData.value = result
        }
    }

}