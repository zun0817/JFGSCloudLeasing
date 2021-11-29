package com.cloud.leasing.base

import com.cloud.leasing.constant.PageName

/**
 * 获取页面名称通用接口
 */
interface IGetPageName {

    @PageName
    fun getPageName(): String

}