package com.cloud.leasing.module.mine.follow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.bean.FollowDeviceBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.EquipmentItemViewData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class EquipmentViewModel : BaseRecyclerViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val followLiveData = MutableLiveData<Result<String>>()

    val unfollowLiveData = MutableLiveData<Result<String>>()

    val deviceLiveData = MutableLiveData<Result<MutableList<FollowDeviceBean>>>()

    @PageName
    override fun getPageName() = PageName.EQUIPMENT

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            val param = getEquipmentParam()
            val result = NetworkApi.requestOfDiviceFollow(param)
            deviceLiveData.value = result
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

    private fun getEquipmentParam(): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        map["followType"] = 0
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
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