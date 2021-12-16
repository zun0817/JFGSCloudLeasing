package com.cloud.leasing.module.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class RegisterViewModel : BaseViewModel() {

    val smsCodeLiveData = MutableLiveData<Result<String>>()

    val registerLiveData = MutableLiveData<Result<String>>()

    @PageName
    override fun getPageName() = PageName.REGISTER

    fun requestOfSmsCode(phoneNumber: String) {
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

    private fun getRegisterParam(
        mobile: String,
        passWord: String,
        randCode: String
    ): RequestBody {
        val map = mutableMapOf<String, String>()
        map["mobile"] = mobile
        map["passWord"] = passWord
        map["randCode"] = randCode
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}