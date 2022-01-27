package com.cloud.leasing.constant

import android.os.Environment
import java.io.File

object Constant {

    /** 接口模块 */

    //const val BASE_URL = "http://123.52.43.250:2209"

    const val BASE_URL = "http://zulin.cregcloud.com:59180"

    //const val BASE_FILE_URL = "http://123.52.43.250:2209/creg/"

    const val BASE_FILE_URL = "http://zulin.cregcloud.com:59180/creg/"

    const val PATH_FILE_UPLOAD = "/file/upload"

    const val PATH_FILE_DOWNLOAD = "/file/download"

    const val PATH_FILE_DELETE = "/file/deleteFile"

    const val PATH_REGISTER = "/api/v1/business/user/app/save"

    const val PATH_SMSCODE = "/api/v1/business/user/app/authCode"

    const val PATH_RESET = "/api/v1/business/user/app/resetPassword"

    const val PATH_LOGIN = "/login"

    const val PATH_LOGIN_MESSAGE = "/api/v1/business/user/app/loginMessage"

    const val PATH_QUERY_PROFILE = "/api/v1/business/user/app/queryProfile"

    const val PATH_LOGOUT = "/api/v1/business/user/app/logout"

    const val PATH_MINE_INFO = "/api/v1/business/user/app/mineInfo"

    const val PATH_COMPANY_AUTH = "/api/v1/userAuth/app/authSubmit"

    const val PATH_FOLLOW_MINE = "/api/v1/business/mine/app/listFocus"

    const val PATH_MINE_DEVICE = "/api/v1/business/mine/app/mineDevice"

    const val PATH_MINE_REQUIRE = "/api/v1/business/mine/app/mineDemand"

    const val PATH_ADD_FOLLOW = "/api/v1/business/mine/app/focus"

    const val PATH_QUIT_FOLLOW = "/api/v1/business/mine/app/unfollow"

    const val PATH_HOME_DEVICES = "/api/v1/rentDevice/app/homeRentDevList"

    const val PATH_HOME_REQUIRES = "/api/v1/demand/app/homeTopDemandList"

    const val PATH_HOME_DEVICE_DETAIL = "/api/v1/rentDevice/app/details"

    const val PATH_HOME_REQUIRE_DETAIL = "/api/v1/demand/app/homeDemandDetails"

    const val PATH_NOTICE_SERVICE = "/api/v1/business/tnoTice/app/queryFirstTnoticeList"

    const val PATH_BANNER = "/api/v1/business/tnoTice/app/bannerList"

    const val PATH_DEVICE_PARAM_LIST = "/sys/dict/getDistDeviceInfo"

    const val PATH_DEVICE_SITE = "/sys/area/list"

    const val PATH_DEVICE_ADD = "/api/v1/rentDevice/app/save"

    const val PATH_REQUIRE_ADD = "/api/v1/demand/app/save"

    const val PATH_QUERY_DATA = "/api/v1/es/app/queryData"

    const val PATH_DEVICE_MANAGE_LIST = "/api/v1/device/app/list"

    const val PATH_DEVICE_MANAGE_DETAIL = "/api/v1/device/app/machineDetails"

    const val PATH_DEVICE_MANAGE_RESUME = "/api/v1/deviceResume/pc/getResumeBydeviceId"

    const val PATH_DEVICE_RESUME_DETAIL = "/api/v1/deviceResume/app/resumeDetails"

    const val PATH_RESUME_PRODUCT_DAILY = "/api/v1/produceDaily/app/list"

    const val PATH_RESUME_FAULT_LEDGER = "/api/v1/produceDailyFault/app/list"

    const val PATH_RESUME_MAINTENANCE = "/api/v1/usedMaintenance/app/list"

    const val PATH_FAULT_DAILY = "/api/v1/repairMaintenance/app/list"

    const val PATH_STORE_DAILY = "/api/v1/depositMaintenance/app/list"

    const val PATH_ADD_PRODUCT_DAILY = "/api/v1/produceDaily/app/save"

    const val PATH_MANAGE_DATA = "/api/v1/deviceManage/pc/getManageByResumeId"

    const val PATH_MANAGE_FILE = "/api/v1/deviceManage/pc/getManageFileByManmageId"

    const val PATH_FAULT_ADD = "/api/v1/repairMaintenance/app/save"

    const val PATH_STORE_CHECK = "/api/v1/depositCheckItem/app/list"


    /** 本地存储 */

    val FILE_PATH = File(
        Environment.getExternalStorageDirectory(),
        "盾构租赁"
    ).path

    const val FILE_AVATAR = "10"

    const val FILE_CONSULT = "20"

    const val FILE_DEVICE = "30"

    const val FILE_REQUIRE = "40"

    const val FILE_BANNER = "50"

    const val FILE_DATA = "60"

    const val FILE_RESUME = "70"

    const val FILE_IMAGE = "80"

    const val FILE_CYCLE = "90"

    const val USER_ID = "user_id"

    const val USER_TOKEN = "user_token"

    const val USER_PHONE = "user_phone"

    const val USER_PASSWORD = "user_password"

    const val DEVICE_TYPE = "device_type"

    const val DEVICE_BRAND = "device_brand"

    const val DEVICE_CUTTER = "device_cutter"

}