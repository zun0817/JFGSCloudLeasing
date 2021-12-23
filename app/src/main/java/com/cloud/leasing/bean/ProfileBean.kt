package com.cloud.leasing.bean

import java.io.Serializable

data class ProfileBean(

    val mineIssue: Int,
    val mineDeviceQty: Int,
    val mineDemandQty: Int,
    val mineFollowQty: Int,
    val mineUserName: Any,
    val mineCompany: Any,
    val isAttestation: String,
    val minHeadImg: String,
    val userStatus: String

) : Serializable
