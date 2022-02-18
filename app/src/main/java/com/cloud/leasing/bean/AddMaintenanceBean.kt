package com.cloud.leasing.bean

import java.io.Serializable

data class AddMaintenanceBean(
    var createTime: String,
    var exceptionDetails: Any? = "",
    var id: Int,
    var isAbnormal: Any? = "0",
    var mtnceName: String,
    var mtncePosition: String,
    var mtnceSystem: String,
    var needingAttention: String,
    var realPicture: Any? = "",
    var refPicture: String,
    var resumeId: Int
) : Serializable