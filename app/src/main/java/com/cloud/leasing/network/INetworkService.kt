package com.cloud.leasing.network

import com.cloud.leasing.bean.VideoBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.network.base.BaseResponse
import retrofit2.http.*

interface INetworkService {

    @GET("videodetail")
    suspend fun requestVideoDetail(@Query("id") id: String): BaseResponse<VideoBean>

    @GET(Constant.PATH_SMSCODE)
    suspend fun requestOfSmsCode(@Query("phoneNumber") phoneNumber: String): BaseResponse<String>

    @FormUrlEncoded
    @POST(Constant.PATH_REGISTER)
    suspend fun requestOfRegister(@FieldMap param: MutableMap<String, String>): BaseResponse<String>
}