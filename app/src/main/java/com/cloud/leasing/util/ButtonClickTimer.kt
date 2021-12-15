package com.cloud.leasing.util

import android.view.View


/**
 * 作者： Zun.
 * 创建： 2018/11/5.
 * 说明： Activity基类
 */
object ButtonClickTimer {

    var lastClickTime: Long = 0
    var viewId: Int = 0
    var lastLinkTime: Long = 0

    /**
     * 防止重复点击 另外一种方法
     */
    val isFastDoubleClick: Boolean
        get() {
            val time = System.currentTimeMillis()
            val timeD = time - lastClickTime
            if (timeD in 1..499) {
                return true
            }
            lastClickTime = time
            return false
        }

    fun canClick(view: View): Boolean {
        if (viewId != view.id) {
            viewId = view.id
            return true
        }
        val currentTimeMillis = System.currentTimeMillis()
        return if (currentTimeMillis - lastClickTime > 500) {
            lastClickTime = currentTimeMillis
            true
        } else {
            lastClickTime = currentTimeMillis
            false
        }
    }

    fun canClick(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        return if (currentTimeMillis - lastClickTime > 500) {
            lastClickTime = currentTimeMillis
            true
        } else {
            lastClickTime = currentTimeMillis
            false
        }
    }

    fun canAdd(view: View): Boolean {
        if (viewId != view.id) {
            viewId = view.id
            return false
        }
        val currentTimeMillis = System.currentTimeMillis()
        return if (currentTimeMillis - lastClickTime < 500) {
            lastClickTime = currentTimeMillis
            true
        } else {
            lastClickTime = currentTimeMillis
            false
        }
    }

    fun canLink(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        return if (currentTimeMillis - lastLinkTime > 110000) {
            lastLinkTime = currentTimeMillis
            true
        } else {
            lastLinkTime = currentTimeMillis
            false
        }
    }

}
