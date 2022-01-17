package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class AddProductDailyViewModel : BaseViewModel() {

    override fun getPageName() = PageName.ADD_PRODUCT_DAILY

    fun requestOfDeleteCutter(filePath: String) {
        viewModelScope.launch {
            val param = getQueryProfileParam(filePath)
            val result = NetworkApi.requestOfDeleteFile(param)
            //deleteCutterLiveData.value = result
        }
    }

    private fun getQueryProfileParam(filePath: String): MutableMap<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["path"] = filePath
        return map
    }
}