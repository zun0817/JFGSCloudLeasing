package com.cloud.leasing.module.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.RequireDetailBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class RequireDetailViewModel : BaseViewModel() {

    val requireDetailLiveData = MutableLiveData<Result<RequireDetailBean>>()

    @PageName
    override fun getPageName() = PageName.REQUIRE_DETAIL

    fun requestOfRequireDetail(demandId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfRequireDetail(demandId)
            requireDetailLiveData.value = result
        }
    }
}