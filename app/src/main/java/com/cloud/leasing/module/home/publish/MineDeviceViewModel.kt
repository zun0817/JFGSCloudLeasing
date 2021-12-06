package com.cloud.leasing.module.home.publish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.bean.MineDeviceBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.MineDeviceItemViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MineDeviceViewModel : BaseRecyclerViewModel() {

    val listData = MutableLiveData<MutableList<MineDeviceItemViewData>>()

    var list: MutableList<MineDeviceItemViewData> = mutableListOf()

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {

        viewModelScope.launch {
            // 模拟网络数据加载
            delay(1000L)

            if (!isLoadMore) {
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            1,
                            isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            2, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            3, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            4, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            2, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            3, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            1, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            1, isSelect = false,
                            isVisible = false
                        )
                    )
                )
            } else {
                // 在第5页模拟网络异常
                if (page == 5) {
                    postData(isLoadMore, emptyList())
                    return@launch
                }
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            3, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            2, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            1, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            4, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            2, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            3, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            4, isSelect = false,
                            isVisible = false
                        )
                    )
                )
                list.add(
                    MineDeviceItemViewData(
                        MineDeviceBean(
                            1, isSelect = false,
                            isVisible = false
                        )
                    )
                )
            }
            postData(isLoadMore, list)
            listData.value = list
        }
    }

    @PageName
    override fun getPageName() = PageName.MINEDEVICE
}