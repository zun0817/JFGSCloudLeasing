package com.cloud.leasing.constant

import androidx.annotation.StringDef

@StringDef(TabId.HOME, TabId.ACGN, TabId.SMALL_VIDEO, TabId.DEVICE, TabId.MINE)
@Retention(AnnotationRetention.SOURCE)
annotation class TabId {
    companion object {
        const val HOME = "home"
        const val ACGN = "acgn"
        const val SMALL_VIDEO = "small_video"
        const val DEVICE = "device"
        const val MINE = "mine"
    }
}