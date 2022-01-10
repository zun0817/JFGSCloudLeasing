package com.cloud.leasing.module.home.publish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.JFGSApplication
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.bean.MineRequireBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.MinePublishItemViewData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.toast
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class MinePublishViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val mineRequireLiveData = MutableLiveData<Result<MineRequireBean>>()

//    val listData = MutableLiveData<MutableList<MinePublishItemViewData>>()
//
//    var list: MutableList<MinePublishItemViewData> = mutableListOf()
//
//    var alllist: MutableList<MinePublishItemViewData> = mutableListOf()

//    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
//        viewModelScope.launch {
//            list.takeIf { it.size > 0 }?.apply { clear() }
//            val param = getMineRequireParam(page)
//            val result = NetworkApi.requestOfMineRequire(param)
//            showResult(result)
//            postData(isLoadMore, list)
//            alllist.addAll(list)
//            listData.value = alllist
//        }
//    }
//
//    private fun showResult(result: Result<MineRequireBean>) {
//        result.onFailure {
//            it.toString().toast(JFGSApplication.instance)
//        }.onSuccess { mineRequireBean ->
//            mineRequireBean.list.forEach { bean ->
//                list.add(MinePublishItemViewData(bean))
//            }
//        }
//    }

    fun requestOfMineRequire(){
        viewModelScope.launch {
            val param = getMineRequireParam()
            val result = NetworkApi.requestOfMineRequire(param)
            mineRequireLiveData.value = result
        }
    }

    private fun getMineRequireParam(): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        map["pageNum"] = 1
        map["pageSize"] = 50
        map["keyword"] = ""
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    @PageName
    override fun getPageName() = PageName.MINEPUBLISH
}