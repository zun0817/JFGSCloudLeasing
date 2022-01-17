package com.cloud.leasing.bean

data class StoreDailyBean(
    val current: Double,
    val pages: Double,
    val records: MutableList<StoreDaily>,
    val searchCount: Boolean,
    val size: Double,
    val total: Double
)

data class StoreDaily(
    val coordinateMatter: String,
    val createTime: String,
    val dateTime: String,
    val depositCheckInfoList: Any,
    val deviceStatus: String,
    val endTime: Any,
    val exceptionDetails: String,
    val id: Double,
    val pageNo: Any,
    val pageSize: Any,
    val resumeId: Double,
    val startTime: Any
)