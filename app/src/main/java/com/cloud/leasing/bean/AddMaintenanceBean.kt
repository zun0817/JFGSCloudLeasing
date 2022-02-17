package com.cloud.leasing.bean

import java.io.Serializable

data class AddMaintenanceBean(
    val createTime: String,
    val exceptionDetails: Any,
    val id: Int,
    val isAbnormal: Any,
    val mtnceName: String,
    val mtncePosition: String,
    val mtnceSystem: String,
    val needingAttention: String,
    val realPicture: Any,
    val refPicture: String,
    val resumeId: Int
) : Serializable