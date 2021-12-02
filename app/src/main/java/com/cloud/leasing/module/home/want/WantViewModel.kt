package com.cloud.leasing.module.home.want

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.WantItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WantViewModel : BaseRecyclerViewModel() {

    @PageName
    override fun getPageName() = PageName.WANT

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            val viewDataList: List<BaseViewData<*>>
            if (!isLoadMore) {
                viewDataList = listOf<BaseViewData<*>>(
                    WantItemViewData(1),
                    WantItemViewData(2),
                    WantItemViewData(3),
                    WantItemViewData(4),
                    WantItemViewData(3),
                    WantItemViewData(2),
                    WantItemViewData(4),
                    WantItemViewData(1)
                )
            } else {
                // 在第5页模拟网络异常
                if (page == 5) {
                    postData(isLoadMore, emptyList())
                    return@launch
                }
                viewDataList = listOf<BaseViewData<*>>(
                    WantItemViewData(1),
                    WantItemViewData(1),
                    WantItemViewData(3),
                    WantItemViewData(4),
                    WantItemViewData(2),
                    WantItemViewData(2),
                    WantItemViewData(4),
                    WantItemViewData(4)
                )
            }
            postData(isLoadMore, viewDataList)
            // postError(isLoadMore)
        }
    }

}