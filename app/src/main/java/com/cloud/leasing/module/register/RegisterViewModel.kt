package com.cloud.leasing.module.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel() {

    val smsCodeLiveData = MutableLiveData<Result<String>>()

    val registerLiveData = MutableLiveData<Result<String>>()

    @PageName
    override fun getPageName() = PageName.REGISTER

    fun requestVideoDetail(phoneNumber: String) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfSmsCode(phoneNumber)
            smsCodeLiveData.value = result
        }
    }

    fun requestOfRegister(mobile: String, passWord: String, randCode: String) {
        viewModelScope.launch {
            val param = getRegisterParam(mobile, passWord, randCode)
            val result = NetworkApi.requestOfRegister(param)
            registerLiveData.value = result
        }
    }

    fun getRegisterParam(
        mobile: String,
        passWord: String,
        randCode: String
    ): MutableMap<String, String> {
        val map = mutableMapOf<String, String>()
        map["mobile"] = mobile
        map["passWord"] = passWord
        map["randCode"] = randCode
        return map
    }

}