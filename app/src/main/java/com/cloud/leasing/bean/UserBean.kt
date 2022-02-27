package com.cloud.leasing.bean

import java.io.Serializable

data class UserBean(
    val perms: List<String>,
    val token: String,
    val user: User
) : Serializable

data class User(
    val birth: String,
    val deleted: String,
    val deptId: Any,
    val deptIds: Any,
    val deptName: Any,
    val email: String,
    val employeeNum: String,
    val gmtCreate: String,
    val gmtModified: String,
    val liveAddress: String,
    val mobile: String,
    val name: String,
    val password: String,
    val roleId: Int,
    val roleName: String,
    val sex: Int,
    val userId: Int,
    val userIdCreate: Any,
    val userIdentity: String,
    val userImage: Int,
    val userType: Any,
    val username: String,
    val userAuth: String
) : Serializable