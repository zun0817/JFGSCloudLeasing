package com.cloud.leasing.module.device

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.DeviceItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DeviceViewModel : BaseRecyclerViewModel() {

    @PageName
    override fun getPageName() = PageName.DEVICE

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            val viewDataList: List<BaseViewData<*>>
            if (!isLoadMore) {
                viewDataList = listOf<BaseViewData<*>>(
                    DeviceItemViewData("中国中铁"),
                    DeviceItemViewData("浙商中铁"),
                    DeviceItemViewData("中铁技服"),
                    DeviceItemViewData("无锡中铁"),
                    DeviceItemViewData("厦门中铁"),
                    DeviceItemViewData("中铁广发"),
                    DeviceItemViewData("铁建重工"),
                    DeviceItemViewData("辽宁三三"),
                )
            } else {
                // 在第5页模拟网络异常
                if (page == 5) {
                    postError(isLoadMore)
                    return@launch
                }
                viewDataList = listOf<BaseViewData<*>>(
                    DeviceItemViewData("北方重工"),
                    DeviceItemViewData("山河智能"),
                    DeviceItemViewData("中交天和"),
                    DeviceItemViewData("隧道股份"),
                    DeviceItemViewData("济南重工"),
                    DeviceItemViewData("中船重工"),
                    DeviceItemViewData("德国海瑞克"),
                    DeviceItemViewData("小松工业"),
                )
            }
            postData(isLoadMore, viewDataList)
            // postError(isLoadMore)
        }
    }



}