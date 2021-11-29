package com.cloud.leasing.bean.exception

class GlobalException private constructor(message: String) : RuntimeException(message) {
    companion object {
        fun of(message: String) = GlobalException(message)
    }
}