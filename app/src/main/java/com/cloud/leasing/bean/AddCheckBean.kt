package com.cloud.leasing.bean

import java.io.Serializable

data class AddCheckBean(
    val checkJson: List<CheckJson>
) : Serializable

data class CheckJson(
    var checkName: String,
    var coordinateMatter: String?,
    var deviceStatus: Int,
    var exceptionDetails: String?
) : Serializable