package com.cloud.leasing.bean

import java.io.Serializable

data class DeviceResumeBean(
    val createTime: String,
    val deviceId: Int,
    val deviceNo: String,
    val deviceResumeStatus: String,
    val exitTime: Any?,
    val id: Int,
    val packageTime: Any,
    val projectNumber: String,
    val startTime: Any?,
    val tunneMeterTotal: String,
    val tunneMeters: String,
    val workingDiam: String
) : Serializable