package com.cloud.leasing.bean

import java.io.Serializable

data class ServiceBean(
    val endRow: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val isFirstPage: Boolean,
    val isLastPage: Boolean,
    val list: List<NoticeServiceBean>,
    val navigateFirstPage: Int,
    val navigateLastPage: Int,
    val navigatePages: Int,
    val navigatepageNums: List<Int>,
    val nextPage: Int,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val prePage: Int,
    val size: Int,
    val startRow: Int,
    val total: Int
) : Serializable

data class NoticeServiceBean(
    val browseCount: String,
    val contacts: String,
    val createTime: String,
    val deleted: String,
    val id: Int,
    val mailbox: String,
    val noticeAppImage: String,
    val noticeCode: String,
    val noticeContent: String,
    val noticeDesc: String,
    val noticePcImage: String,
    val noticeSort: Int,
    val noticeStatus: String,
    val noticeTitle: String,
    val phone: String,
    val pointCount: Any,
    val position: String,
    val reqUrl: String,
    val status: Any,
    val updateTime: String,
    val userId: Any
)