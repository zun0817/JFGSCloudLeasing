package com.cloud.leasing.module.home.publish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.MineDeviceBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.MineDeviceItemViewData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class MineDeviceViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val mineDeviceLiveData = MutableLiveData<Result<MineDeviceBean>>()

//    val listData = MutableLiveData<MutableList<MineDeviceItemViewData>>()
//
//    var list: MutableList<MineDeviceItemViewData> = mutableListOf()
//
//    var alllist: MutableList<MineDeviceItemViewData> = mutableListOf()

//    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
//        viewModelScope.launch {
//            list.takeIf { it.size > 0 }?.apply { clear() }
//            val param = getMineDeviceParam(page)
//            val result = NetworkApi.requestOfMineDevice(param)
//            showResult(result)
//            postData(isLoadMore, list)
//            alllist.addAll(list)
//            listData.value = alllist
//        }
//    }
//
//    private fun showResult(result: Result<MineDeviceBean>) {
//        result.onFailure {
//            it.toString().toast(JFGSApplication.instance)
//        }.onSuccess { mineDeviceBean ->
//            mineDeviceBean.pageInfo.list.forEach { bean ->
//                list.add(MineDeviceItemViewData(bean))
//            }
//        }
//    }

    fun requestOfMineDevice(){
        viewModelScope.launch {
            val param = getMineDeviceParam()
            val result = NetworkApi.requestOfMineDevice(param)
            mineDeviceLiveData.value = result
        }
    }

    private fun getMineDeviceParam(): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        map["pageNum"] = 1
        map["pageSize"] = 50
        map["keyword"] = ""
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    @PageName
    override fun getPageName() = PageName.MINEDEVICE
}