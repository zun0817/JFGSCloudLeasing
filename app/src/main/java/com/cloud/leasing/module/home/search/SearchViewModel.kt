package com.cloud.leasing.module.home.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.SearchBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class SearchViewModel : BaseViewModel() {

    val searchDeviceLiveData = MutableLiveData<Result<SearchBean>>()

    val searchRequireLiveData = MutableLiveData<Result<SearchBean>>()

    @PageName
    override fun getPageName() = PageName.SEARCH

    fun requestOfQueryData(type: Int, keyWord: String?) {
        viewModelScope.launch {
            val param = getSearchParam(type, keyWord)
            val result = NetworkApi.requestOfQueryData(param)
            when(type){
                1 -> searchDeviceLiveData.value = result
                2 -> searchRequireLiveData.value = result
            }

        }
    }

    private fun getSearchParam(type: Int, keyWord: String?): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["pageNo"] = 1
        map["pageSize"] = 50
        map["type"] = type
        map["keyWord"] = keyWord
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }
}