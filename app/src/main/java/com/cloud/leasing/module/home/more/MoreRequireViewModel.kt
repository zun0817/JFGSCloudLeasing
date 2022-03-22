package com.cloud.leasing.module.home.more

import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.HomeRequireBean
import com.cloud.leasing.bean.SearchBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class MoreRequireViewModel : BaseViewModel() {

    val requiresLiveData = MutableLiveData<Result<MutableList<HomeRequireBean>>>()

    val searchDeviceLiveData = MutableLiveData<Result<SearchBean>>()

    val searchRequireLiveData = MutableLiveData<Result<SearchBean>>()

    @PageName
    override fun getPageName() = PageName.MORE_REQUIRE

    fun requestOfHomeRequires() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfHomeRequires()
            requiresLiveData.value = result
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

}