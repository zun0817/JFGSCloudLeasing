package com.cloud.leasing.item

import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.LoadMoreState

class LoadMoreViewData(@LoadMoreState loadMoreState: Int) : BaseViewData<Int>(loadMoreState) {
}