package com.cloud.leasing.constant

object Constant {

    /** 接口模块 */

    const val BASE_URL = "http://123.52.43.250:2209"

    const val BASE_FILE_URL = "http://123.52.43.250:2209/creg/"

    const val PATH_FILE_UPLOAD = "/file/upload"

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



    /** 本地存储 */

    const val USER_ID = "user_id"

    const val USER_TOKEN = "user_token"

    const val USER_PHONE = "user_phone"

    const val USER_PASSWORD = "user_password"

}