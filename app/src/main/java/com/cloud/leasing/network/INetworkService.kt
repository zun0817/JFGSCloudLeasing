package com.cloud.leasing.network

import com.cloud.leasing.bean.UserBean
import com.cloud.leasing.bean.UserInfoBean
import com.cloud.leasing.bean.VideoBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.network.base.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.*

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
    suspend fun requestOfLogin(@Body requestBody: RequestBody): BaseResponse<UserBean>

    @POST(Constant.PATH_LOGIN_MESSAGE)
    suspend fun requestOfLoginMessage(@Body requestBody: RequestBody): BaseResponse<UserBean>

    @FormUrlEncoded
    @POST(Constant.PATH_QUERY_PROFILE)
    suspend fun requestOfQueryProfile(@FieldMap param: MutableMap<String, Any>): BaseResponse<UserInfoBean>
}