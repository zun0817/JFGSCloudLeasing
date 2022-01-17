package com.cloud.leasing.bean

data class RepairDailyBean(
    val current: Double,
    val pages: Double,
    val records: MutableList<RepairDaily>,
    val searchCount: Boolean,
    val size: Double,
    val total: Double
)

data class RepairDaily(
    val assistMatter: String,
    val beginTime: String,
    val dateTime: String,
    val endTime: String,
    val id: Double,
    val jobContent: String,
    val pageNo: Any,
    val pageSize: Any,
    val planTime: String,
    val ratio: String,
    val remarks: String,
    val resumeId: Double,
    val safetyAccident: String,
    val startTime: String
)