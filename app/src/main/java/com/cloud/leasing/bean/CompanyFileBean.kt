package com.cloud.leasing.bean

import java.io.Serializable

data class CompanyFileBean(
    val filePath: String,
    val fileSize: String,
    val fileName: String
) : Serializable
