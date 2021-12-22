package com.cloud.leasing.module.home.publish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.JFGSApplication
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.bean.MineDeviceBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.MineDeviceItemViewData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class MineDeviceViewModel : BaseRecyclerViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val listData = MutableLiveData<MutableList<MineDeviceItemViewData>>()

    var list: MutableList<MineDeviceItemViewData> = mutableListOf()

    private var totalPage = 0

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            if (!isLoadMore) {
                val param = getMineDeviceParam(page)
                val result = NetworkApi.requestOfMineDevice(param)
                result.onFailure {
                    it.toString().toast(JFGSApplication.instance)
                }.onSuccess { mineDeviceBean ->
                    totalPage = mineDeviceBean.pageInfo.endRow
                    mineDeviceBean.pageInfo.list.forEach { bean ->
                        list.add(MineDeviceItemViewData(bean))
                    }
                }
            } else {
                if (page == totalPage){
                    postData(isLoadMore, emptyList())
                    return@launch
                }
            }
            postData(isLoadMore, list)
            listData.value = list
        }
    }

    private fun getMineDeviceParam(pageNum: Int): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        map["pageNum"] = pageNum
        map["pageSize"] = 5
        map["keyword"] = ""
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    @PageName
    override fun getPageName() = PageName.MINEDEVICE
}