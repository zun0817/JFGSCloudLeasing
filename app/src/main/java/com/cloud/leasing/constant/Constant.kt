package com.cloud.leasing.constant

object Constant {

    /** 接口模块 */

    const val BASE_URL = "http://123.52.43.250:2209"

    const val BASE_FILE_URL = "http://123.52.43.250:2209/creg/"

    const val PATH_REGISTER = "/api/v1/business/user/app/save"

    const val PATH_SMSCODE = "/api/v1/business/user/app/authCode"

    const val PATH_RESET = "/api/v1/business/user/app/resetPassword"

    const val PATH_LOGIN = "/api/v1/business/user/app/login"

    const val PATH_LOGIN_MESSAGE = "/api/v1/business/user/app/loginMessage"

    const val PATH_QUERY_PROFILE = "/api/v1/business/user/app/queryProfile"

    const val PATH_LOGOUT = "/api/v1/business/user/app/logout"

    const val PATH_MINE_INFO = "/api/v1/business/user/app/mineInfo"

    const val PATH_COMPANY_AUTH = "/api/v1/userAuth/app/authSubmit"

    /** 本地存储 */

    const val USER_ID = "user_id"

    const val USER_TOKEN = "user_token"

}