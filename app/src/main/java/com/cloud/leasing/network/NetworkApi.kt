package com.cloud.leasing.network

import com.cloud.leasing.constant.Constant
import com.cloud.leasing.network.base.BaseNetworkApi
import okhttp3.RequestBody

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

    suspend fun requestOfRegister(requestBody: RequestBody) = getResult {
        service.requestOfRegister(requestBody)
    }

    suspend fun requestOfResetPassword(requestBody: RequestBody) = getResult {
        service.requestOfResetPassword(requestBody)
    }

    suspend fun requestOfLogin(requestBody: RequestBody) = getResult {
        service.requestOfLogin(requestBody)
    }

    suspend fun requestOfLoginMessage(requestBody: RequestBody) = getResult {
        service.requestOfLoginMessage(requestBody)
    }
}