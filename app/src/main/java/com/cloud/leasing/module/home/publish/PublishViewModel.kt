package com.cloud.leasing.module.home.publish

import android.text.format.DateFormat
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.Test1ViewData
import com.cloud.leasing.item.Test2ViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PublishViewModel : BaseRecyclerViewModel() {

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            val time = DateFormat.format("MM-dd HH:mm:ss", System.currentTimeMillis())

            val viewDataList: List<BaseViewData<*>>
            if (!isLoadMore) {
                viewDataList = listOf<BaseViewData<*>>(
                    Test1ViewData("a-$time"),
                    Test2ViewData("b-$time"),
                    Test1ViewData("c-$time"),
                    Test2ViewData("d-$time"),
                    Test1ViewData("e-$time"),
                    Test2ViewData("f-$time"),
                    Test1ViewData("g-$time"),
                    Test2ViewData("h-$time"),
                )
            } else {
                // 在第5页模拟网络异常
                if (page == 5) {
                    postError(isLoadMore)
                    return@launch
                }
                viewDataList = listOf<BaseViewData<*>>(
                    Test1ViewData("a-$time"),
                    Test2ViewData("b-$time"),
                    Test1ViewData("c-$time"),
                    Test2ViewData("d-$time"),
                    Test1ViewData("e-$time"),
                    Test2ViewData("f-$time"),
                    Test1ViewData("g-$time"),
                    Test2ViewData("h-$time"),
                )
            }
            postData(isLoadMore, viewDataList)
            // postError(isLoadMore)
        }
    }

    @PageName
    override fun getPageName() = PageName.HOME
}