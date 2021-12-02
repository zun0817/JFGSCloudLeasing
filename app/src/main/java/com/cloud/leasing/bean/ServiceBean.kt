package com.cloud.leasing.bean

import java.io.Serializable

data class ServiceBean(
    var id: Int,
    var title: String,
    var describe: String
) : Serializable
