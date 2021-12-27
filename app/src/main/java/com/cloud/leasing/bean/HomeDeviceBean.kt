package com.cloud.leasing.bean

import java.io.Serializable

data class HomeDeviceBean(
    val deviceBrand: String,
    val deviceBrandName: String,
    val deviceCity: String,
    val deviceMainFileUrl: String,
    val deviceNo: String,
    val deviceType: String,
    val deviceTypeName: String,
    val drivingPosition: String,
    val focusStatus: Int,
    val id: Int,
    val outerDiameter: String
) : Serializable