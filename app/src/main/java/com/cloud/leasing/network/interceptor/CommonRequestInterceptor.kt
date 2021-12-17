package com.cloud.leasing.network.interceptor

import com.cloud.leasing.constant.Constant
import com.cloud.leasing.persistence.XKeyValue
import okhttp3.Interceptor
import okhttp3.Response

class CommonRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        // 这里添加公共请求头
        builder.addHeader("Authorization", XKeyValue.getString(Constant.USER_TOKEN))
        return chain.proceed(builder.build())
    }
}