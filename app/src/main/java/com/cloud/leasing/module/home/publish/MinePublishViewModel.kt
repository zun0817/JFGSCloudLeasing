package com.cloud.leasing.module.home.publish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.bean.MinePublishBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.MinePublishItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MinePublishViewModel : BaseRecyclerViewModel() {

    val listData = MutableLiveData<MutableList<MinePublishItemViewData>>()

    var list: MutableList<MinePublishItemViewData> = mutableListOf()

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            val viewDataList: List<BaseViewData<*>>
            if (!isLoadMore) {
                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "中国中铁"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "浙商中铁"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "中铁技服"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "无锡中铁"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "厦门中铁"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "中铁广发"
                )))
                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "铁建重工"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "辽宁三三"
                )))
            } else {
                // 在第5页模拟网络异常
                if (page == 5) {
                    postData(isLoadMore, emptyList())
                    return@launch
                }
                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "北方重工"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "山河智能"
                )))
                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "中交天和"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "隧道股份"
                )))
                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "济南重工"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "中船重工"
                )))
                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "德国海瑞克"
                )))

                list.add(MinePublishItemViewData(MinePublishBean(
                    isSelect = false,
                    isVisible = false,
                    name = "小松工业"
                )))
            }
            postData(isLoadMore, list)
            listData.value = list
        }
    }

    @PageName
    override fun getPageName() = PageName.MINEPUBLISH
}