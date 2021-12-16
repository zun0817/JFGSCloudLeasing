package com.cloud.leasing.module.forget

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

class ForgetViewModel : BaseViewModel() {

    val smsCodeLiveData = MutableLiveData<Result<String>>()

    val resetLiveData = MutableLiveData<Result<String>>()

    @PageName
    override fun getPageName() = PageName.FORGET

    fun requestOfSmsCode(phoneNumber: String) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfSmsCode(phoneNumber)
            smsCodeLiveData.value = result
        }
    }

    fun requestOfResetPassword(mobile: String, passWord: String, randCode: String) {
        viewModelScope.launch {
            val param = getResetPasswordParam(mobile, passWord, randCode)
            val result = NetworkApi.requestOfResetPassword(param)
            resetLiveData.value = result
        }
    }

    private fun getResetPasswordParam(
        mobile: String,
        passWord: String,
        randCode: String
    ): RequestBody {
        val map = mutableMapOf<String, String>()
        map["phoneNumber"] = mobile
        map["passWord"] = passWord
        map["messageRandCode"] = randCode
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}