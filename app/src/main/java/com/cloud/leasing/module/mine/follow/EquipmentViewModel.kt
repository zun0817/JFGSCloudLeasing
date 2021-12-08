package com.cloud.leasing.module.mine.follow

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.EquipmentItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EquipmentViewModel : BaseRecyclerViewModel() {

    @PageName
    override fun getPageName() = PageName.EQUIPMENT

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            val viewDataList: List<BaseViewData<*>>
            if (!isLoadMore) {
                viewDataList = listOf<BaseViewData<*>>(
                    EquipmentItemViewData("中国中铁"),
                    EquipmentItemViewData("浙商中铁"),
                    EquipmentItemViewData("中铁技服"),
                    EquipmentItemViewData("无锡中铁"),
                    EquipmentItemViewData("厦门中铁"),
                    EquipmentItemViewData("中铁广发"),
                    EquipmentItemViewData("铁建重工"),
                    EquipmentItemViewData("辽宁三三"),
                )
            } else {
                // 在第5页模拟网络异常
                if (page == 3) {
                    postData(isLoadMore, emptyList())
                    return@launch
                }
                viewDataList = listOf<BaseViewData<*>>(
                    EquipmentItemViewData("北方重工"),
                    EquipmentItemViewData("山河智能"),
                    EquipmentItemViewData("中交天和"),
                    EquipmentItemViewData("隧道股份"),
                    EquipmentItemViewData("济南重工"),
                    EquipmentItemViewData("中船重工"),
                    EquipmentItemViewData("德国海瑞克"),
                    EquipmentItemViewData("小松工业"),
                )
            }
            postData(isLoadMore, viewDataList)
        }
    }



}