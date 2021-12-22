package com.cloud.leasing.bean

import java.io.Serializable

data class FollowDeviceBean(
    val applicableStratum: String,
    val assetOwnership: String,
    val beamNum: String,
    val createTime: String,
    val cutterDiam: String,
    val cutterType: String,
    var deleted: String,
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
    var focusStatus: Int,
    val hingeForm: String,
    val id: Int,
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