package com.cloud.leasing.module.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    val smsCodeLiveData = MutableLiveData<Result<String>>()

    @PageName
    override fun getPageName() = PageName.LOGIN

    fun requestOfSmsCode(phoneNumber: String) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfSmsCode(phoneNumber)
            smsCodeLiveData.value = result
        }
    }

}