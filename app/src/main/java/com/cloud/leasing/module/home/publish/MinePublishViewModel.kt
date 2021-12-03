package com.cloud.leasing.module.home.publish

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.MinePublishItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MinePublishViewModel : BaseRecyclerViewModel() {

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            val viewDataList: List<BaseViewData<*>>
            if (!isLoadMore) {
                viewDataList = listOf<BaseViewData<*>>(
                    MinePublishItemViewData("中国中铁"),
                    MinePublishItemViewData("浙商中铁"),
                    MinePublishItemViewData("中铁技服"),
                    MinePublishItemViewData("无锡中铁"),
                    MinePublishItemViewData("厦门中铁"),
                    MinePublishItemViewData("中铁广发"),
                    MinePublishItemViewData("铁建重工"),
                    MinePublishItemViewData("辽宁三三"),
                )
            } else {
                // 在第5页模拟网络异常
                if (page == 5) {
                    postData(isLoadMore, emptyList())
                    return@launch
                }
                viewDataList = listOf<BaseViewData<*>>(
                    MinePublishItemViewData("北方重工"),
                    MinePublishItemViewData("山河智能"),
                    MinePublishItemViewData("中交天和"),
                    MinePublishItemViewData("隧道股份"),
                    MinePublishItemViewData("济南重工"),
                    MinePublishItemViewData("中船重工"),
                    MinePublishItemViewData("德国海瑞克"),
                    MinePublishItemViewData("小松工业"),
                )
            }
            postData(isLoadMore, viewDataList)
        }
    }

    @PageName
    override fun getPageName() = PageName.MINEPUBLISH
}