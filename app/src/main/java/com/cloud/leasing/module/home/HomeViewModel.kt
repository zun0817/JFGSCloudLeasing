package com.cloud.leasing.module.home

import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.HomeDeviceBean
import com.cloud.leasing.bean.HomeRequireBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class HomeViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val requiresLiveData = MutableLiveData<Result<MutableList<HomeRequireBean>>>()

    val deviceLiveData = MutableLiveData<Result<MutableList<HomeDeviceBean>>>()

    val followLiveData = MutableLiveData<Result<String>>()

    val unfollowLiveData = MutableLiveData<Result<String>>()

    fun requestOfHomeDevices() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfHomeDevices()
            deviceLiveData.value = result
        }
    }

    fun requestOfHomeRequires() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfHomeRequires()
            requiresLiveData.value = result
        }
    }

    fun requestOfAddFollow(followId: Int, deleted: String) {
        viewModelScope.launch {
            val param = getAddFollowParam(followId, deleted)
            val result = NetworkApi.requestOfAddFollow(param)
            followLiveData.value = result
        }
    }

    fun requestOfUnfollow(followId: Int, deleted: String) {
        viewModelScope.launch {
            val param = getUnfollowParam(followId, deleted)
            val result = NetworkApi.requestOfUnfollow(param)
            unfollowLiveData.value = result
        }
    }

    private fun getAddFollowParam(followId: Int, deleted: String): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        map["followType"] = 0
        map["followId"] = followId
        map["deleted"] = deleted
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun getUnfollowParam(followId: Int, deleted: String): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        map["followType"] = 0
        map["followId"] = followId
        map["deleted"] = deleted
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    @PageName
    override fun getPageName() = PageName.HOME
}