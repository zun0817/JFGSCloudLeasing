package com.cloud.leasing.bean

import java.io.Serializable

data class ManageFileBean(
    val createTime: String,
    val deleteTime: Any,
    val deleted: String,
    val fileName: String,
    val filePath: String,
    val fileSort: Int,
    val fileSuffix: String,
    val fileType: String,
    val id: Int,
    val manageId: Int
) : Serializable