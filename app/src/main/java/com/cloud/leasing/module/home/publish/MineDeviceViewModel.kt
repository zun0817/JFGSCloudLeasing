package com.cloud.leasing.module.home.publish

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.bean.MineDeviceBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.MineDeviceItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MineDeviceViewModel : BaseRecyclerViewModel() {

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            val viewDataList: List<BaseViewData<*>>
            if (!isLoadMore) {
                viewDataList = listOf<BaseViewData<*>>(
                    MineDeviceItemViewData(MineDeviceBean(1, false)),
                    MineDeviceItemViewData(MineDeviceBean(2, false)),
                    MineDeviceItemViewData(MineDeviceBean(3, false)),
                    MineDeviceItemViewData(MineDeviceBean(4, false)),
                    MineDeviceItemViewData(MineDeviceBean(2, false)),
                    MineDeviceItemViewData(MineDeviceBean(3, false)),
                    MineDeviceItemViewData(MineDeviceBean(4, false)),
                    MineDeviceItemViewData(MineDeviceBean(1, false))
                )
            } else {
                // 在第5页模拟网络异常
                if (page == 5) {
                    postData(isLoadMore, emptyList())
                    return@launch
                }
                viewDataList = listOf<BaseViewData<*>>(
                    MineDeviceItemViewData(MineDeviceBean(3, false)),
                    MineDeviceItemViewData(MineDeviceBean(2, false)),
                    MineDeviceItemViewData(MineDeviceBean(3, false)),
                    MineDeviceItemViewData(MineDeviceBean(4, false)),
                    MineDeviceItemViewData(MineDeviceBean(2, false)),
                    MineDeviceItemViewData(MineDeviceBean(3, false)),
                    MineDeviceItemViewData(MineDeviceBean(4, false)),
                    MineDeviceItemViewData(MineDeviceBean(1, false))
                )
            }
            postData(isLoadMore, viewDataList)
        }
    }

    @PageName
    override fun getPageName() = PageName.MINEDEVICE
}