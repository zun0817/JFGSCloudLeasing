package com.cloud.leasing.module.home.service

import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.JFGSApplication
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.bean.ServiceBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.ServiceItemViewData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.util.toast
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ServiceViewModel : BaseRecyclerViewModel() {

    var list: MutableList<ServiceItemViewData> = mutableListOf()

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            list.takeIf { it.size > 0 }?.apply { clear() }
            val param = getServiceParam()
            val result = NetworkApi.requestOfNoticeService(param)
            showResult(result)
            postData(isLoadMore, list)
        }
    }

    private fun showResult(result: Result<ServiceBean>) {
        result.onFailure {
            it.toString().toast(JFGSApplication.instance)
        }.onSuccess { serviceBean ->
            serviceBean.list.forEach { bean ->
                list.add(ServiceItemViewData(bean))
            }
        }
    }

    private fun getServiceParam(): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["pageNum"] = 1
        map["pageSize"] = 50
        map["noticeCode"] = "notice_incr"
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    @PageName
    override fun getPageName() = PageName.SERVICE
}