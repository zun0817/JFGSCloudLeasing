package com.cloud.leasing.module.mine.follow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.bean.FollowDeviceBean
import com.cloud.leasing.bean.FollowRequireBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.RequireItemViewData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class RequireViewModel : BaseRecyclerViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val requireLiveData = MutableLiveData<Result<MutableList<FollowRequireBean>>>()

    @PageName
    override fun getPageName() = PageName.REQUIRE

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            val param = getRequireParam()
            val result = NetworkApi.requestOfRequireFollow(param)
            requireLiveData.value = result
        }
    }

    private fun getRequireParam(): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        map["followType"] = 1
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}