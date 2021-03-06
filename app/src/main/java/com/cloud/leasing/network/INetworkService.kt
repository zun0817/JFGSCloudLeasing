package com.cloud.leasing.network

import com.cloud.leasing.bean.*
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.network.base.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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

    @GET(Constant.PATH_DEVICE_PARAM_LIST)
    suspend fun requestOfDeviceType(): BaseResponse<DeviceTypeBean>

    @GET(Constant.PATH_DEVICE_SITE)
    suspend fun requestOfDeviceSite(): BaseResponse<MutableList<ProvinceBean>>

    @POST(Constant.PATH_DEVICE_ADD)
    suspend fun requestOfAddDevice(@Body requestBody: RequestBody): BaseResponse<String>

    @POST(Constant.PATH_REQUIRE_ADD)
    suspend fun requestOfAddRequire(@Body requestBody: RequestBody): BaseResponse<String>

    @POST(Constant.PATH_QUERY_DATA)
    suspend fun requestOfQueryData(@Body requestBody: RequestBody): BaseResponse<SearchBean>

    @GET(Constant.PATH_HOME_DEVICE_DETAIL)
    suspend fun requestOfDeviceDetail(@Query("deviceId") deviceId: Int): BaseResponse<DeviceDetailBean>

    @GET(Constant.PATH_HOME_REQUIRE_DETAIL)
    suspend fun requestOfRequireDetail(@Query("demandId") demandId: Int): BaseResponse<RequireDetailBean>

    @FormUrlEncoded
    @POST(Constant.PATH_FILE_DELETE)
    suspend fun requestOfDeleteFile(@FieldMap param: MutableMap<String, Any>): BaseResponse<String>

    @GET(Constant.PATH_DEVICE_MANAGE_LIST)
    suspend fun requestOfDeviceManagerForm(@QueryMap param: MutableMap<String, Any?>): BaseResponse<DeviceManagerFormBean>

    @GET(Constant.PATH_DEVICE_MANAGE_DETAIL)
    suspend fun requestOfDeviceManageDetail(@Query("deviceId") deviceId: Int): BaseResponse<DeviceManageDetailBean>

    @GET(Constant.PATH_DEVICE_MANAGE_RESUME)
    suspend fun requestOfDeviceResume(@Query("deviceId") deviceId: Int): BaseResponse<MutableList<DeviceResumeBean>>

    @GET(Constant.PATH_DEVICE_RESUME_DETAIL)
    suspend fun requestOfResumeDetail(@Query("resumeId") resumeId: Int): BaseResponse<ResumeDetailBean>

    @GET(Constant.PATH_RESUME_PRODUCT_DAILY)
    suspend fun requestOfResumeProductDaily(@Query("resumeId") resumeId: Int): BaseResponse<ProductDailyBean>

    @GET(Constant.PATH_RESUME_FAULT_LEDGER)
    suspend fun requestOfResumeFaultLedger(@Query("resumeId") resumeId: Int): BaseResponse<FaultLedgerBean>

    @GET(Constant.PATH_RESUME_MAINTENANCE)
    suspend fun requestOfResumeMaintenance(@Query("resumeId") resumeId: Int): BaseResponse<MaintenanceBean>

    @GET(Constant.PATH_FAULT_DAILY)
    suspend fun requestOfResumeFaultDaily(@Query("resumeId") resumeId: Int): BaseResponse<RepairDailyBean>

    @GET(Constant.PATH_STORE_DAILY)
    suspend fun requestOfResumeStoreDaily(@Query("resumeId") resumeId: Int): BaseResponse<StoreDailyBean>

    @POST(Constant.PATH_ADD_PRODUCT_DAILY)
    suspend fun requestOfAddProductDaily(@Body requestBody: RequestBody): BaseResponse<String>

    @GET(Constant.PATH_MANAGE_DATA)
    suspend fun requestOfManageData(@Query("resumeId") resumeId: Int): BaseResponse<MutableList<ManageDataBean>>

    @GET(Constant.PATH_MANAGE_FILE)
    suspend fun requestOfManageFile(@Query("manageId") manageId: Int): BaseResponse<MutableList<ManageFileBean>>

    @POST(Constant.PATH_FAULT_ADD)
    suspend fun requestOfAddFaultDaily(@Body requestBody: RequestBody): BaseResponse<String>

    @GET(Constant.PATH_STORE_CHECK)
    suspend fun requestOfStoreCheck(@Query("resumeId") resumeId: Int): BaseResponse<MutableList<CheckDailyItemBean>>

    @GET(Constant.PATH_FILE_DOWNLOAD)
    suspend fun requestOfDownloadFile(@Query("fileName") fileName: String, @Query("path") path: String): BaseResponse<ResponseBody>

    @POST(Constant.PATH_LIFT_FILE)
    suspend fun requestOfLifeCycleFile(@Body requestBody: RequestBody): BaseResponse<String>

    @GET(Constant.PATH_ADD_MAINTENANCE)
    suspend fun requestOfAddMaintenance(@Query("resumeId") resumeId: Int): BaseResponse<MutableList<AddMaintenanceBean>>

    @POST(Constant.PATH_SUBMIT_MAINTENANCE)
    suspend fun requestOfSubmitMaintenance(@Body requestBody: RequestBody): BaseResponse<String>

    @POST(Constant.PATH_ADD_DEPOSIT)
    suspend fun requestOfSubmitDeposit(@Body requestBody: RequestBody): BaseResponse<String>

    @GET(Constant.PATH_CITY)
    suspend fun requestOfCityList(@Query("areaCode") areaCode: String): BaseResponse<MutableList<CityBean>>

    @GET(Constant.PATH_UPDATE_VERSION)
    suspend fun requestOfUpdateVersion(@Query("appType") appType: Int): BaseResponse<UpdateVersionBean>

    @Multipart
    @POST(Constant.PATH_FILE_UPLOAD)
    suspend fun requestOfUploadFile(
        @Part("fileType") fileType: RequestBody,
        @Part file: MultipartBody.Part
    ): BaseResponse<CompanyFileBean>
}