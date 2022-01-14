package com.cloud.leasing.bean

data class ResumeDetailBean(
    val contractAmount: Double?,
    val createTime: String,
    val criticalTimeNode: String?,
    val deviceFlieList: List<DeviceFlie>,
    val deviceId: Int,
    val deviceNo: Any,
    val deviceResumeStatus: String,
    val deviceType: String,
    val deviceUser: String?,
    val drivingNodeTime: Any,
    val exitTime: String?,
    val id: Int,
    val inBlock: Any?,
    val intervalGeology: Any?,
    val intervalMileage: String?,
    val packageAccTime: Any,
    val packageTime: String?,
    val projectAddress: String?,
    val projectCatelog: String?,
    val projectManager: String?,
    val projectName: Any?,
    val projectNumber: String?,
    val ringWidth: Any,
    val settlementRange: String?,
    val shieldSpec: String,
    val startTime: Any?,
    val updateTime: Any,
    val useCity: String?,
    val workingDiam: String
)

data class DeviceFlie(
    val createTime: String,
    val deleteTime: Any,
    val deleted: String,
    val deviceFileType: String,
    val fileName: String,
    val filePath: String,
    val fileSort: Int,
    val fileSuffix: String,
    val fileType: String,
    val id: Int,
    val resumeId: Int,
    val uid: String
)