package com.cloud.leasing.util

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.text.DecimalFormat

/**
 * 获取指定文件大小
 */
@Throws(Exception::class)
fun getFileSize(file: File): Long {
    var size: Long = 0
    if (file.exists()) {
        var fis: FileInputStream? = null
        fis = FileInputStream(file)
        size = fis.available().toLong()
    } else {
        file.createNewFile()
        Log.e("获取文件大小", "文件不存在!")
    }
    return size
}

/**
 * 转换文件大小
 */
fun formatFileSize(fileS: Long): String {
    val df = DecimalFormat("#.00")
    var fileSizeString = ""
    val wrongSize = "0B"
    if (fileS == 0L) {
        return wrongSize
    }
    fileSizeString = when {
        fileS < 1024 -> {
            df.format(fileS.toDouble()).toString() + "B"
        }
        fileS < 1048576 -> {
            df.format(fileS.toDouble() / 1024).toString() + "KB"
        }
        fileS < 1073741824 -> {
            df.format(fileS.toDouble() / 1048576).toString() + "MB"
        }
        else -> {
            df.format(fileS.toDouble() / 1073741824).toString() + "GB"
        }
    }
    return fileSizeString
}