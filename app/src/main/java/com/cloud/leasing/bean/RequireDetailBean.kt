package com.cloud.leasing.bean

data class RequireDetailBean(
    val beamNum: Any,
    val corporateName: String,
    val createTime: String,
    val cutterDiam: String,
    val cutterType: String,
    val cutterTypeName: String,
    val deleted: String,
    val demandCity: String,
    val demandFileList: List<DemandFile>,
    val demandNum: Int,
    val demandProvince: String,
    val deviceBrand: String,
    val deviceBrandName: String,
    val deviceType: String,
    val deviceTypeName: String,
    val drivingTorque: String,
    val focusStatus: Int,
    val geologicalInfo: String,
    val geologicalInfoName: Any,
    val id: Int,
    val isExamine: String,
    val limitedRange: String,
    val matchStatus: String,
    val mobile: String,
    val name: String,
    val openingRate: String,
    val outerDiameter: String,
    val projectLength: Double,
    val projectLocation: String,
    val propulsiveForce: String,
    val province: String,
    val remarks: Any,
    val turningRadius: String,
    val usageTime: String,
    val userId: Int
)

data class DemandFile(
    val createTime: String,
    val deleteTime: Any,
    val deleted: String,
    val demandFileType: String,
    val demandId: Int,
    val fileName: String,
    val filePath: String,
    val fileSize: String,
    val fileSort: Int,
    val fileSuffix: String,
    val fileType: String,
    val id: Int
)