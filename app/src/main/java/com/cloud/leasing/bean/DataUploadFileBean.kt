package com.cloud.leasing.bean

import java.io.Serializable

data class DataUploadFileBean(
    val fileName: String,
    val filePath: String,
    val fileSort: Int,
    val fileSuffix: String,
    val fileType: Int
) : Serializable