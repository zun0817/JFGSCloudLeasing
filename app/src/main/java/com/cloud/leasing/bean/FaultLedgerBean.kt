package com.cloud.leasing.bean

data class FaultLedgerBean(
    val produceDailyFaultList: ProduceDailyFaultList?,
    val ringsTotal: String,
    val tunneMetersTotal: String
)

data class ProduceDailyFaultList(
    val current: Int,
    val pages: Int,
    val records: MutableList<FaultRecord>,
    val searchCount: Boolean,
    val size: Int,
    val total: Int
)

data class FaultRecord(
    val causeAnalysis: String,
    val createTime: String,
    val dailyId: Int,
    val downTime: String,
    val faultBody: String,
    val faultDesc: String,
    val faultHandler: String,
    val fileUrl: Any,
    val id: Int,
    val pageNo: Any,
    val processTime: String,
    val repModel: String,
    val repRecord: String,
    val resumeId: Int,
    val ringNumber: String
)