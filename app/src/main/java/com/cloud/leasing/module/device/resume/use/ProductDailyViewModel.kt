package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.ProductDailyBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class ProductDailyViewModel : BaseViewModel() {

    val productDailyLiveData = MutableLiveData<Result<ProductDailyBean>>()

    @PageName
    override fun getPageName() = PageName.PRODUCT_DAILY

    fun requestOfResumeProductDaily(resumeId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfResumeProductDaily(resumeId)
            productDailyLiveData.value = result
        }
    }
}