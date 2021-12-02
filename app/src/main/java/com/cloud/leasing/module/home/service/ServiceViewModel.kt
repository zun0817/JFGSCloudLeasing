package com.cloud.leasing.module.home.service

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.bean.ServiceBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.ServiceItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ServiceViewModel : BaseRecyclerViewModel() {

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            delay(1000L)
            val viewDataList = listOf<BaseViewData<*>>(
                ServiceItemViewData(ServiceBean(0, "盾构拆装机服务", "专业拆机，快速装机")),
                ServiceItemViewData(ServiceBean(1, "盾构系统整机调试服务", "专业人员调试服务")),
                ServiceItemViewData(ServiceBean(2, "设备运输服务", "专业车队远距离大件运输服务")),
                ServiceItemViewData(ServiceBean(3, "维保服务", "专业跟踪定期按时保养"))
            )
            postData(isLoadMore, viewDataList)
        }
    }

    @PageName
    override fun getPageName() = PageName.SERVICE
}