package com.cloud.leasing.bean

import java.io.Serializable

data class MinePublishBean(
    var isSelect: Boolean,
    var isVisible: Boolean,
    var name: String
) : Serializable