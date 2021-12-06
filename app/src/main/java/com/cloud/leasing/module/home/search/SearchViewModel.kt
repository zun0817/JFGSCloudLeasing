package com.cloud.leasing.module.home.search

import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.constant.PageName

class SearchViewModel : BaseViewModel() {

    @PageName
    override fun getPageName() = PageName.SEARCH
}