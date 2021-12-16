package com.cloud.leasing.network

import com.cloud.leasing.bean.VideoBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.network.base.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface INetworkService {

    @GET("videodetail")
    suspend fun requestVideoDetail(@Query("id") id: String): BaseResponse<VideoBean>

    @GET(Constant.PATH_SMSCODE)
    suspend fun requestOfSmsCode(@Query("phoneNumber") phoneNumber: String): BaseResponse<String>

    @POST(Constant.PATH_REGISTER)
    suspend fun requestOfRegister(@Body requestBody: RequestBody): BaseResponse<String>

    @POST(Constant.PATH_RESET)
    suspend fun requestOfResetPassword(@Body requestBody: RequestBody): BaseResponse<String>

    @POST(Constant.PATH_LOGIN)
    suspend fun requestOfLogin(@Body requestBody: RequestBody): BaseResponse<String>

    @POST(Constant.PATH_LOGIN_MESSAGE)
    suspend fun requestOfLoginMessage(@Body requestBody: RequestBody): BaseResponse<String>
}