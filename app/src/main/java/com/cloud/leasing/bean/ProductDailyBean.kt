package com.cloud.leasing.bean

import java.io.Serializable

data class ProductDailyBean(
    val current: Int,
    val pages: Int,
    val records: MutableList<RecordBean>,
    val searchCount: Boolean,
    val size: Int,
    val total: Int
) : Serializable

data class RecordBean(
    val checkRange: Any,
    val createTime: String,
    val criticalTimeNode: Any,
    val cumRings: Int,
    val currentGeology: String,
    val dailyRecord: String,
    val dailyRings: Int,
    val dateTime: String,
    val deviceStatusDescribe: Any,
    val drivingType: String,
    val faultTime: Any,
    val id: Int,
    val isFault: String,
    val mainGeology: String,
    val maxDailyRings: Int,
    val peopleStaffing: String,
    val produceDailyFaultList: Any,
    val produceMatter: String,
    val progressRatio: Double,
    val resumeId: Int,
    val tunneMeters: String
) : Serializable