package com.cloud.leasing.bean

import java.io.Serializable

data class MaintenanceBean(
    val current: Int,
    val pages: Int,
    val records: MutableList<Maintenance>,
    val searchCount: Boolean,
    val size: Int,
    val total: Int
) : Serializable

data class Maintenance(
    val checkRange: Any,
    val createTime: String,
    val criticalTimeNode: Any,
    val cumRings: Double,
    val currentGeology: String,
    val dailyRecord: String,
    val dailyRings: Double,
    val dateTime: String,
    val deviceStatusDescribe: Any,
    val drivingType: String,
    val faultTime: Any,
    val id: Int,
    val isFault: String,
    val mainGeology: String,
    val maxDailyRings: Double,
    val peopleStaffing: String,
    val produceDailyFaultList: Any?,
    val produceMatter: String,
    val progressRatio: Double,
    val resumeId: Int,
    val tunneMeters: String,
    val abnormalTotal: Int,
    val exceptionDetails: String
) : Serializable