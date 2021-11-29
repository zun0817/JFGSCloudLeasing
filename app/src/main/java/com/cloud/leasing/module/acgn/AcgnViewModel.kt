package com.cloud.leasing.module.acgn

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.list.base.BaseRecyclerViewModel
import com.cloud.leasing.bean.User
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.item.Test3ViewData
import com.cloud.leasing.persistence.database.XDatabase
import kotlinx.coroutines.launch

class AcgnViewModel : BaseRecyclerViewModel() {

    override fun needNetwork(): Boolean {
        return false
    }

    override fun loadData(isLoadMore: Boolean, isReLoad: Boolean, page: Int) {
        viewModelScope.launch {
            // 模拟插入一些数据
            XDatabase.userDao().insert(
                listOf(
                    User(1, "吴", "一", null),
                    User(2, "黄", "二", null),
                    User(3, "李", "三", null),
                    User(4, "张", "四", null),
                    User(5, "林", "五", null),
                    User(6, "吴", "六", null),
                    User(7, "黄", "七", null),
                    User(8, "李", "八", null),
                    User(9, "张", "九", null),
                    User(10, "林", "十", null),
                )
            )

            // 从数据库加载数据
            val viewData = mutableListOf<Test3ViewData>()
            XDatabase.userDao().getAll().forEach {
                viewData.add(Test3ViewData(it))
            }
            postData(isLoadMore, viewData)
        }
    }

    @PageName
    override fun getPageName() = PageName.ACGN

}