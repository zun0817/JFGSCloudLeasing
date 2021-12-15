package com.cloud.leasing.network

import com.cloud.leasing.constant.Constant
import com.cloud.leasing.network.base.BaseNetworkApi

/**
 * 网络请求具体实现
 * 需要部署服务端：http://123.52.43.250:2209
 */
object NetworkApi : BaseNetworkApi<INetworkService>(Constant.BASE_URL) {

    suspend fun requestVideoDetail(id: String) = getResult {
        service.requestVideoDetail(id)
    }

    suspend fun requestOfSmsCode(phoneNumber: String) = getResult {
        service.requestOfSmsCode(phoneNumber)
    }

    suspend fun requestOfRegister(param: MutableMap<String, String>) = getResult {
        service.requestOfRegister(param)
    }
}