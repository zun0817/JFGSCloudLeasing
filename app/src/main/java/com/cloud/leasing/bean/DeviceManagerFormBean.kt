package com.cloud.leasing.bean

data class DeviceManagerFormBean(
    val current: Int,
    val pages: Int,
    val records: MutableList<Record>,
    val searchCount: Boolean,
    val size: Int,
    val total: Int
)

data class Record(
    val cutterDiam: Any,
    val cutterType: String,
    var cutterTypeName: String,
    val deviceImageUrl: Any,
    val deviceNo: String,
    val deviceResumeStatus: String,
    val deviceStatus: String,
    val deviceType: String,
    var deviceTypeName: String,
    val drivingPosition: String,
    val focusStatus: Any,
    val id: Int,
    val intervalMileage: String,
    val matchStatus: Int,
    val nowResumeId: String,
    val progressRatio: Any,
    val propertyOwner: String,
    val workingDiam: String
)