package com.cloud.leasing.bean

import java.io.Serializable

data class ManageDataBean(
    val id: Int,
    val childrenList: List<ManageData>,
    val manageName: String,
    val manageStauts: String,
    val parentId: Int,
    val resumeId: Int
) : Serializable

data class ManageData(
    val id: Int,
    val childrenList: List<Any>,
    val manageName: String,
    val manageStauts: String,
    val parentId: Int,
    val resumeId: Int
) : Serializable