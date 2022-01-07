package com.cloud.leasing.bean

data class SearchBean(
    val sbData: SbData,
    val xqData: XqData
)

data class SbData(
    val sbList: MutableList<Search>,
    val sbTotal: Int
)

data class XqData(
    val xqList: MutableList<Search>,
    val xqTotal: Int
)

data class Search(
    val beamNum: String,
    val cutterDiam: String,
    val cutterType: String,
    val demandCity: Any,
    val demandNum: Any,
    val demandProvince: Any,
    val deviceBrand: String,
    val deviceCity: String,
    val deviceProvince: String,
    val deviceRentStatus: String,
    val deviceRentStatusCode: String,
    val deviceStatus: String,
    val deviceType: String,
    val drivingPower: String,
    val drivingTorque: String,
    val filePath: String,
    var focusStatus: String,
    val geologicalInfo: Any,
    val id: Int,
    val leaseTime: String,
    val mileageUsed: String,
    val openingRate: String,
    val outerDiameter: String,
    val projectLength: Any,
    val propulsiveForce: String,
    val turningRadius: String,
    val type: String,
    val usageTime: Any
)