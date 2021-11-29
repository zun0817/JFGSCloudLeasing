package com.cloud.leasing.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import com.cloud.leasing.JFGSApplication

fun isNetworkConnect(): Boolean {
    val cm = JFGSApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo?.isAvailable ?: false
}

fun toNetworkSetting(context: Context) {
    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
    context.startActivity(intent)
}