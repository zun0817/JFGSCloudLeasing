package com.cloud.leasing.constant

import androidx.annotation.StringDef

@StringDef(
    PageName.SPLASH,
    PageName.LOGIN,
    PageName.REGISTER,
    PageName.FORGET,
    PageName.MAIN,
    PageName.HOME,
    PageName.DEVICE,
    PageName.WANT,
    PageName.HAVE,
    PageName.SERVICE,
    PageName.PUBLISH,
    PageName.MINEDEVICE,
    PageName.MINEPUBLISH,
    PageName.SEARCH,
    PageName.FOLLOW,
    PageName.EQUIPMENT,
    PageName.REQUIRE,
    PageName.MODIFY,
    PageName.COMPANYAUTH,
    PageName.ADDREQUIRE,
    PageName.ADDDEVICE,
    PageName.REQUIRE_DETAIL,
    PageName.DEVICE_DETAIL,
    PageName.DEVICE_RESUME,
    PageName.PROFILE_EDIT,
    PageName.MORE_DEVICE,
    PageName.MORE_REQUIRE,
    PageName.SERVICE_DETAIL,

    PageName.ACGN,
    PageName.SMALL_VIDEO,
    PageName.GOLD,
    PageName.MINE,
    PageName.ABOUT
)
@Retention(AnnotationRetention.SOURCE)
annotation class PageName {
    companion object {
        const val SPLASH = "splash"
        const val LOGIN = "login"
        const val REGISTER = "register"
        const val FORGET = "forget"
        const val MAIN = "main"
        const val HOME = "home"
        const val DEVICE = "device"
        const val MINE = "mine"
        const val WANT = "want"
        const val HAVE = "have"
        const val SERVICE = "service"
        const val PUBLISH = "publish"
        const val MINEDEVICE = "minedevice"
        const val MINEPUBLISH = "minepublish"
        const val SEARCH = "search"
        const val FOLLOW = "follow"
        const val EQUIPMENT = "equipment"
        const val REQUIRE = "require"
        const val MODIFY = "modify"
        const val COMPANYAUTH = "companyauth"
        const val ADDREQUIRE = "addrequire"
        const val ADDDEVICE = "adddevice"
        const val REQUIRE_DETAIL = "require_detail"
        const val DEVICE_DETAIL = "device_detail"
        const val DEVICE_RESUME = "device_resume"
        const val PROFILE_EDIT = "profile_edit"
        const val MORE_DEVICE = "more_device"
        const val MORE_REQUIRE = "more_require"
        const val SERVICE_DETAIL = "service_detail"

        const val ACGN = "acgn"
        const val SMALL_VIDEO = "small_video"
        const val GOLD = "gold"
        const val ABOUT = "about"
    }
}