package com.cloud.leasing.module.mine.follow

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.RequireItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RequireViewModel : BaseRecyclerViewModel() {

    @PageName
    override fun getPageName() = PageName.REQUIRE

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            val viewDataList: List<BaseViewData<*>>
            if (!isLoadMore) {
                viewDataList = listOf<BaseViewData<*>>(
                    RequireItemViewData("中国中铁"),
                    RequireItemViewData("浙商中铁"),
                    RequireItemViewData("中铁技服"),
                    RequireItemViewData("无锡中铁"),
                    RequireItemViewData("厦门中铁"),
                    RequireItemViewData("中铁广发"),
                    RequireItemViewData("铁建重工"),
                    RequireItemViewData("辽宁三三"),
                )
            } else {
                // 在第5页模拟网络异常
                if (page == 2) {
                    postData(isLoadMore, emptyList())
                    return@launch
                }
                viewDataList = listOf<BaseViewData<*>>(
                    RequireItemViewData("北方重工"),
                    RequireItemViewData("山河智能"),
                    RequireItemViewData("中交天和"),
                    RequireItemViewData("隧道股份"),
                    RequireItemViewData("济南重工"),
                    RequireItemViewData("中船重工"),
                    RequireItemViewData("德国海瑞克"),
                    RequireItemViewData("小松工业"),
                )
            }
            postData(isLoadMore, viewDataList)
        }
    }


}