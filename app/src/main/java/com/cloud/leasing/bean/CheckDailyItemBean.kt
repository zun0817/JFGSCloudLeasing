package com.cloud.leasing.bean

import java.io.Serializable

data class CheckDailyItemBean(
    val checkName: String,
    val createTime: String,
    val id: Int,
    val pageNo: Any,
    val pageSize: Any,
    val resumeId: Int,
    val sort: Int,
    var deviceStatus: Int,
    var coordinateMatter: String?,
    var exceptionDetails: String?
) : Serializable