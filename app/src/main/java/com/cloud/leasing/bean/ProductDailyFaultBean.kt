package com.cloud.leasing.bean

import java.io.Serializable

data class ProductDailyFaultBean(
    var ringNumber: String?,
    var faultDesc: String?,
    var faultBody: String?,
    var causeAnalysis: String?,
    var repRecord: String?,
    var repModel: String?,
    var fileUrl: String?,
    var downTime: String?,
    var processTime: String?,
    var resumeId: Int
) : Serializable
