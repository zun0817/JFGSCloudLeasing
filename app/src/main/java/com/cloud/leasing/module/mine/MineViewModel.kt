package com.cloud.leasing.module.mine

import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.ProfileBean
import com.cloud.leasing.bean.UserInfoBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class MineViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val profileLiveData = MutableLiveData<Result<UserInfoBean>>()

    val mineInfoLiveData = MutableLiveData<Result<ProfileBean>>()

    val logoutLiveData = MutableLiveData<Result<Int>>()

    @PageName
    override fun getPageName() = PageName.MINE

    fun requestOfQueryProfile() {
        viewModelScope.launch {
            val param = getQueryProfileParam()
            val result = NetworkApi.requestOfQueryProfile(param)
            profileLiveData.value = result
        }
    }

    fun requestOfMineInfo() {
        viewModelScope.launch {
            val param = getMineInfoParam()
            val result = NetworkApi.requestOfMineInfo(param)
            mineInfoLiveData.value = result
        }
    }

    fun requestOfLogout() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfLogout()
            logoutLiveData.value = result
        }
    }

    private fun getQueryProfileParam(): MutableMap<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        return map
    }

    private fun getMineInfoParam(): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}