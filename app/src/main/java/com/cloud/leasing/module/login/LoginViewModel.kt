package com.cloud.leasing.module.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.UserBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class LoginViewModel : BaseViewModel() {

    val smsCodeLiveData = MutableLiveData<Result<String>>()

    val loginLiveData = MutableLiveData<Result<UserBean>>()

    val messageLiveData = MutableLiveData<Result<UserBean>>()

    @PageName
    override fun getPageName() = PageName.LOGIN

    fun requestOfSmsCode(phoneNumber: String) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfSmsCode(phoneNumber)
            smsCodeLiveData.value = result
        }
    }

    fun requestOfLogin(mobile: String, passWord: String) {
        viewModelScope.launch {
            val param = getLoginParam(mobile, passWord)
            val result = NetworkApi.requestOfLogin(param)
            loginLiveData.value = result
        }
    }

    fun requestOfLoginMessage(phoneNumber: String, messageRandCode: String) {
        viewModelScope.launch {
            val param = getLoginMessageParam(phoneNumber, messageRandCode)
            val result = NetworkApi.requestOfLoginMessage(param)
            messageLiveData.value = result
        }
    }

    private fun getLoginParam(
        mobile: String,
        passWord: String
    ): RequestBody {
        val map = mutableMapOf<String, String>()
        map["username"] = mobile
        map["password"] = passWord
        map["userType"] = "2"
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun getLoginMessageParam(
        phoneNumber: String,
        messageRandCode: String
    ): RequestBody {
        val map = mutableMapOf<String, String>()
        map["phoneNumber"] = phoneNumber
        map["messageRandCode"] = messageRandCode
        map["userType"] = "2"
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}