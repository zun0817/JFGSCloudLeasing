package com.cloud.leasing.bean

import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.TabId
import kotlin.reflect.KClass

data class Tab(
    @TabId
    val id: String,
    val title: String,
    val icon: Int,
    val fragmentClz: KClass<out BaseFragment<*>>
)