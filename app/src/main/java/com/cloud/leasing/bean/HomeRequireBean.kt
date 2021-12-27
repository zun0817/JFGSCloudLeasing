package com.cloud.leasing.bean

import java.io.Serializable

data class HomeRequireBean(
    val cutterDiam: Any,
    val demandCity: String,
    val demandNum: String,
    val demandProvince: Any,
    val deviceBrand: String,
    val deviceBrandName: String,
    val deviceType: String,
    val deviceTypeName: String,
    val geologicalInfo: String,
    val geologicalInfoName: Any,
    val id: Int,
    val isExamine: Any,
    val matchStatus: Any,
    val projectLength: String,
    val projectLocation: String,
    val usageTime: String,
    val userId: Int
) : Serializable