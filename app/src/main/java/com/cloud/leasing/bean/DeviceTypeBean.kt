package com.cloud.leasing.bean

import java.io.Serializable

data class DeviceTypeBean(
    val cutterType: MutableList<CutterType>,
    val deviceBrand: MutableList<DeviceBrand>,
    val deviceType: MutableList<DeviceType>
) : Serializable

data class CutterType(
    val id: Int,
    val name: String,
    val value: String
) : Serializable

data class DeviceBrand(
    val id: Int,
    val name: String,
    val value: String
) : Serializable

data class DeviceType(
    val id: Int,
    val name: String,
    val value: String
) : Serializable