package com.cloud.leasing.network.base

/**
 * 网络数据返回基类
 */
data class BaseResponse<T>(
    var code: String = "0",
    val msg: String? = null,
    val data: T? = null
)
