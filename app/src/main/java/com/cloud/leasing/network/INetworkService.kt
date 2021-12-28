package com.cloud.leasing.network

import com.cloud.leasing.bean.*
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.network.base.BaseResponse
import okhttp3.MultipartBody
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

    @POST(Constant.PATH_MINE_INFO)
    suspend fun requestOfMineInfo(@Body requestBody: RequestBody): BaseResponse<ProfileBean>

    @POST(Constant.PATH_LOGOUT)
    suspend fun requestOfLogout(): BaseResponse<Int>

    @POST(Constant.PATH_COMPANY_AUTH)
    suspend fun requestOfCompanyAuth(@Body requestBody: RequestBody): BaseResponse<String>

    @POST(Constant.PATH_FOLLOW_MINE)
    suspend fun requestOfDiviceFollow(@Body requestBody: RequestBody): BaseResponse<MutableList<FollowDeviceBean>>

    @POST(Constant.PATH_FOLLOW_MINE)
    suspend fun requestOfRequireFollow(@Body requestBody: RequestBody): BaseResponse<MutableList<FollowRequireBean>>

    @POST(Constant.PATH_ADD_FOLLOW)
    suspend fun requestOfAddFollow(@Body requestBody: RequestBody): BaseResponse<String>

    @POST(Constant.PATH_QUIT_FOLLOW)
    suspend fun requestOfUnfollow(@Body requestBody: RequestBody): BaseResponse<String>

    @POST(Constant.PATH_MINE_DEVICE)
    suspend fun requestOfMineDevice(@Body requestBody: RequestBody): BaseResponse<MineDeviceBean>

    @POST(Constant.PATH_MINE_REQUIRE)
    suspend fun requestOfMineRequire(@Body requestBody: RequestBody): BaseResponse<MineRequireBean>

    @GET(Constant.PATH_HOME_DEVICES)
    suspend fun requestOfHomeDevices(): BaseResponse<MutableList<HomeDeviceBean>>

    @GET(Constant.PATH_HOME_REQUIRES)
    suspend fun requestOfHomeRequires(): BaseResponse<MutableList<HomeRequireBean>>

    @POST(Constant.PATH_BANNER)
    suspend fun requestOfBanner(): BaseResponse<MutableList<BannerBean>>

    @POST(Constant.PATH_NOTICE_SERVICE)
    suspend fun requestOfNoticeService(@Body requestBody: RequestBody): BaseResponse<ServiceBean>

    @Multipart
    @POST(Constant.PATH_FILE_UPLOAD)
    suspend fun requestOfUploadFile(
        @Part("fileType") fileType: RequestBody,
        @Part file: MultipartBody.Part
    ): BaseResponse<CompanyFileBean>
}