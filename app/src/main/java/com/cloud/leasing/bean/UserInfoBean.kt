package com.cloud.leasing.bean

import java.io.Serializable

data class UserInfoBean(
    val company: String,
    val fileName: String,
    val filePath: String,
    val fileType: String,
    val mobile: Any,
    val name: String,
    val newPassWord: Any,
    val passWord: String,
    val userAuth: Any,
    val userId: Int,
    val userIdEntity: String,
    val userName: String
) : Serializable