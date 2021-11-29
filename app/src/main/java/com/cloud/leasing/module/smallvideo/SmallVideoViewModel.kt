package com.cloud.leasing.module.smallvideo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.VideoBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class SmallVideoViewModel : BaseViewModel() {

    val helloWorldLiveData = MutableLiveData<Result<VideoBean>>()

    fun requestVideoDetail(id: String) {
        viewModelScope.launch {
            val result = NetworkApi.requestVideoDetail(id)
            helloWorldLiveData.value = result
        }
    }

    @PageName
    override fun getPageName() = PageName.SMALL_VIDEO

}