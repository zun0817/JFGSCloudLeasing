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

        const val ACGN = "acgn"
        const val SMALL_VIDEO = "small_video"
        const val GOLD = "gold"
        const val ABOUT = "about"
    }
}