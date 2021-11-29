package com.cloud.leasing.util

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View

object ViewTouchUtil {
    /**
     * 自定义扩大view的范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    fun expandViewTouchDelegate(
        view: View, top: Int,
        bottom: Int, left: Int, right: Int
    ) {
        (view.parent as View).post {
            val bounds = Rect()
            view.isEnabled = true
            view.getHitRect(bounds)
            bounds.top -= top
            bounds.bottom += bottom
            bounds.left -= left
            bounds.right += right
            val touchDelegate = TouchDelegate(bounds, view)
            if (View::class.java.isInstance(view.parent)) {
                (view.parent as View).touchDelegate = touchDelegate
            }
        }
    }

    fun expandViewTouchDelegate(view: View, bottom: Int) {
        (view.parent as View).post {
            val bounds = Rect()
            view.isEnabled = true
            view.getHitRect(bounds)
            bounds.top -= 0
            bounds.bottom = bounds.bottom * 5
            bounds.left -= 0
            bounds.right += 0
            val touchDelegate = TouchDelegate(bounds, view)
            if (View::class.java.isInstance(view.parent)) {
                (view.parent as View).touchDelegate = touchDelegate
            }
        }
    }

    /**
     * 扩大view的点击范围 左右上下都为15；
     */
    fun expandViewTouchDelegate(view: View) {
        (view.parent as View).post {
            val bounds = Rect()
            view.isEnabled = true
            view.getHitRect(bounds)
            bounds.top -= 15
            bounds.bottom += 15
            bounds.left -= 15
            bounds.right += 15
            val touchDelegate = TouchDelegate(bounds, view)
            if (View::class.java.isInstance(view.parent)) {
                (view.parent as View).touchDelegate = touchDelegate
            }
        }
    }

    /**
     * 还原View的触摸和点击响应范围,最小不小于View自身范围
     */
    fun restoreViewTouchDelegate(view: View) {
        (view.parent as View).post {
            val bounds = Rect()
            bounds.setEmpty()
            val touchDelegate = TouchDelegate(bounds, view)
            if (View::class.java.isInstance(view.parent)) {
                (view.parent as View).touchDelegate = touchDelegate
            }
        }
    }
}