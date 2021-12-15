/**
 * Kotlin扩展属性和扩展函数
 */
package com.cloud.leasing.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.text.TextUtils
import androidx.room.util.StringUtil
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * Boolean转Visibility
 */
fun Boolean.toVisibility() = if (this) View.VISIBLE else View.GONE

/**
 * Context转Activity
 */
fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> {
            this
        }
        is ContextWrapper -> {
            this.baseContext.getActivity()
        }
        else -> null
    }
}

fun isMobileNO(mobiles: String): Boolean {
    //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个
    //"\\d{9}"代表后面是可以是0～9的数字，有9位
    val telRegex = "[1][34578]\\d{9}"
    return if (TextUtils.isEmpty(mobiles)) false else mobiles.matches(Regex(telRegex))
}

fun isMobilPhone(phone: String): Boolean {
    val regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
    return if (phone.length != 11) {
        false
    } else {
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(phone)
        m.matches()
    }
}

val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp: Float
    get() = this.toFloat().dp

val Float.half: Float
    get() = this / 2F


val Int.half: Int
    get() = (this / 2F).toInt()

val Float.radians: Float
    get() = Math.toRadians(this.toDouble()).toFloat()

fun ImageView.setImageUrl(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

val String?.nullSafeValue: String
    get() = this ?: ""