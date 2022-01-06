package com.cloud.leasing.bean

import java.io.Serializable

data class UploadFileBean(
    val deviceFileType: String,
    val fileName: String,
    val filePath: String,
    val fileSize: String,
    val fileType: String
) : Serializable