package com.cloud.leasing.bean

import java.io.Serializable

data class CompanyFileBean(
    val fileUrl: String,
    val fileSize: String,
    val originalFileName: String,
    val fileName: String,
    val filePath: String
) : Serializable
