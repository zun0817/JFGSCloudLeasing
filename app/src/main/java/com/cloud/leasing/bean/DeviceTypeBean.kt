package com.cloud.leasing.bean

import java.io.Serializable

data class DeviceTypeBean(
    val cutterType: MutableList<CutterType>,
    val deviceBrand: MutableList<DeviceBrand>,
    val deviceType: MutableList<DeviceType>
) : Serializable

data class CutterType constructor(
    var id: Int = 0,
    var name: String = "",
    var value: String = ""
) : Serializable

data class DeviceBrand constructor(
    var id: Int = 0,
    var name: String = "",
    var value: String = ""
) : Serializable

data class DeviceType constructor(
    var id: Int = 0,
    var name: String = "",
    var value: String = ""
) : Serializable