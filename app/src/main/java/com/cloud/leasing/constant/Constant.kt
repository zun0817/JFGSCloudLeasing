package com.cloud.leasing.constant

object Constant {

    /** 接口模块 */

    const val BASE_URL = "http://123.52.43.250:2209"

    const val BASE_FILE_URL = "http://123.52.43.250:2209/creg/"

    const val PATH_FILE_UPLOAD = "/file/upload"

    const val PATH_FILE_DELETE = "/file/deleteFile"

    const val PATH_REGISTER = "/api/v1/business/user/app/save"

    const val PATH_SMSCODE = "/api/v1/business/user/app/authCode"

    const val PATH_RESET = "/api/v1/business/user/app/resetPassword"

    const val PATH_LOGIN = "/api/v1/business/user/app/login"

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

    /** 本地存储 */

    const val FILE_AVATAR = "10"

    const val FILE_CONSULT  = "20"

    const val FILE_DEVICE  = "30"

    const val FILE_REQUIRE  = "40"

    const val FILE_BANNER  = "50"

    const val FILE_DATA  = "60"

    const val FILE_RESUME  = "70"

    const val FILE_IMAGE  = "80"

    const val FILE_CYCLE  = "90"

    const val USER_ID = "user_id"

    const val USER_TOKEN = "user_token"

    const val USER_PHONE = "user_phone"

    const val USER_PASSWORD = "user_password"

}