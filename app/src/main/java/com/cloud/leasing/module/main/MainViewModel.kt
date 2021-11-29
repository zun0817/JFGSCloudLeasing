package com.cloud.leasing.module.main

import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.constant.PageName

class MainViewModel : BaseViewModel() {

    @PageName
    override fun getPageName() = PageName.HOME

}