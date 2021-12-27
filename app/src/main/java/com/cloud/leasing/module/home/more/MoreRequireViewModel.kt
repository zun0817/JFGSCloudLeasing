package com.cloud.leasing.module.home.more

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.HomeRequireBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class MoreRequireViewModel : BaseViewModel() {

    val requiresLiveData = MutableLiveData<Result<MutableList<HomeRequireBean>>>()

    @PageName
    override fun getPageName() = PageName.MORE_REQUIRE

    fun requestOfHomeRequires() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfHomeRequires()
            requiresLiveData.value = result
        }
    }
}