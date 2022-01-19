package com.cloud.leasing.bean

import java.io.Serializable

data class ManageDataBean(
    val childrenList: List<ManageData>,
    val id: Int,
    val manageName: String,
    val manageStauts: String,
    val parentId: Int,
    val resumeId: Int
) : Serializable

data class ManageData(
    val childrenList: List<Any>,
    val id: Int,
    val manageName: String,
    val manageStauts: String,
    val parentId: Int,
    val resumeId: Int
) : Serializable