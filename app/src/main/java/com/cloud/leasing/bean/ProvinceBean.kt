package com.cloud.leasing.bean

data class ProvinceBean(
    val children: List<Children>,
    val label: String,
    val value: String
)

data class Children(
    val label: String,
    val value: String
)