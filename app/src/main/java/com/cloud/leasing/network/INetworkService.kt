package com.cloud.leasing.network

import com.cloud.leasing.bean.VideoBean
import com.cloud.leasing.network.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkService {

    @GET("videodetail")
    suspend fun requestVideoDetail(@Query("id") id: String): BaseResponse<VideoBean>
}