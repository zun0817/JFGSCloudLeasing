package com.cloud.leasing.module.home.have

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.HaveItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HaveViewModel : BaseRecyclerViewModel() {

    @PageName
    override fun getPageName() = PageName.HAVE

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            val viewDataList: List<BaseViewData<*>>
            if (!isLoadMore) {
                viewDataList = listOf<BaseViewData<*>>(
                    HaveItemViewData(1),
                    HaveItemViewData(2),
                    HaveItemViewData(3),
                    HaveItemViewData(4),
                    HaveItemViewData(3),
                    HaveItemViewData(2),
                    HaveItemViewData(4),
                    HaveItemViewData(1)
                )
            } else {
                // 在第5页模拟网络异常
                if (page == 5) {
                    postData(isLoadMore, emptyList())
                    return@launch
                }
                viewDataList = listOf<BaseViewData<*>>(
                    HaveItemViewData(1),
                    HaveItemViewData(1),
                    HaveItemViewData(3),
                    HaveItemViewData(4),
                    HaveItemViewData(2),
                    HaveItemViewData(2),
                    HaveItemViewData(4),
                    HaveItemViewData(4)
                )
            }
            postData(isLoadMore, viewDataList)
        }
    }

}