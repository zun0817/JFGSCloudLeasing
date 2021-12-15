package com.cloud.leasing.module.home.detail

import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.constant.PageName

class DeviceDetailViewModel : BaseViewModel() {

    @PageName
    override fun getPageName() = PageName.DEVICE_DETAIL
}