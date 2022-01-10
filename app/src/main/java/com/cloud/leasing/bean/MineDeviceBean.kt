package com.cloud.leasing.bean

import java.io.Serializable

data class MineDeviceBean(
    val matchStatusCount: Int,
    val pageInfo: PageInfo
) : Serializable

data class PageInfo(
    val endRow: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val isFirstPage: Boolean,
    val isLastPage: Boolean,
    val list: MutableList<MineDevice>,
    val navigateFirstPage: Int,
    val navigateLastPage: Int,
    val navigatePages: Int,
    val navigatepageNums: List<Int>,
    val nextPage: Int,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val prePage: Int,
    val size: Int,
    val startRow: Int,
    val total: Int
) : Serializable

data class MineDevice(
    var id: Int,
    var isSelect: Boolean = false,
    var isVisible: Boolean = false,
    val applicableStratum: String,
    val assetOwnership: String,
    val beamNum: String,
    val createTime: String,
    val cutterDiam: String,
    val cutterType: String,
    val deleted: String,
    val deviceArea: String,
    val deviceBrand: String,
    val deviceMainFileUrl: String,
    val deviceNo: String,
    val deviceRentStatus: String,
    val deviceResume: String,
    val deviceStatus: String,
    val deviceType: String,
    val drivingForm: String,
    val drivingPosition: String,
    val drivingPower: String,
    val drivingTorque: String,
    val focusStatus: Any,
    val hingeForm: String,
    val leaseTime: String,
    val matchStatus: String,
    val mileageUsed: String,
    val openingRate: String,
    val outerDiameter: String,
    val propertyOwner: String,
    val propulsiveForce: String,
    val remarks: String,
    val turningRadius: String,
    val userId: Int,
    val workingDiam: String
) : Serializable