package com.cloud.leasing.module.home.more

import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.HomeDeviceBean
import com.cloud.leasing.bean.SearchBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class MoreDeviceViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val deviceLiveData = MutableLiveData<Result<MutableList<HomeDeviceBean>>>()

    val searchDeviceLiveData = MutableLiveData<Result<SearchBean>>()

    val searchRequireLiveData = MutableLiveData<Result<SearchBean>>()

    val followLiveData = MutableLiveData<Result<String>>()

    val unfollowLiveData = MutableLiveData<Result<String>>()

    @PageName
    override fun getPageName() = PageName.MORE_DEVICE

    fun requestOfHomeDevices() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfHomeDevices()
            deviceLiveData.value = result
        }
    }

    fun requestOfQueryData(type: Int) {
        viewModelScope.launch {
            val param = getSearchParam(type)
            val result = NetworkApi.requestOfQueryData(param)
            when (type) {
                1 -> searchDeviceLiveData.value = result
                2 -> searchRequireLiveData.value = result
            }
        }
    }

    private fun getSearchParam(type: Int): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["pageNo"] = 1
        map["pageSize"] = 50
        map["type"] = type
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
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

}