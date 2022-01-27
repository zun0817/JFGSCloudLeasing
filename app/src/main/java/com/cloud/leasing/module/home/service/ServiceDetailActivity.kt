package com.cloud.leasing.module.home.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityServiceDetailBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.cloud.leasing.util.DensityUtil
import com.cloud.leasing.util.ScreenUtils
import com.cloud.leasing.util.ViewTouchUtil
import com.sky.filepicker.kotlin.contain


class ServiceDetailActivity :
    BaseActivity<ActivityServiceDetailBinding>(ActivityServiceDetailBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(
            activity: Activity,
            title: String,
            content: String,
            name: String,
            phone: String,
            email: String
        ) {
            val intent = Intent()
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            intent.putExtra("name", name)
            intent.putExtra("phone", phone)
            intent.putExtra("email", email)
            intent.setClass(activity, ServiceDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getPageName() = PageName.SERVICE_DETAIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initWebView()
        initSystemBar()
    }

    private fun initView() {
        viewBinding.serviceDetailBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.serviceDetailBackImg)
        viewBinding.serviceDetailTitleTv.text = intent.getStringExtra("title")
        viewBinding.serviceDetailNameTv.text = intent.getStringExtra("name")
        viewBinding.serviceDetailPhoneTv.text = intent.getStringExtra("phone")
        viewBinding.serviceDetailEmailTv.text = intent.getStringExtra("email")
        val content = intent.getStringExtra("content")
        content?.let {
//            it.takeIf { it.contain("<p><br/></p>") }?.apply {
//                viewBinding.serviceDetailWebview.loadData(
//                    replace("<p><br/></p>", "")
//                        .replace("<p>", "")
//                        .replace("</p>", "")
//                        .replace("<br/>", ""),
//                    "text/html; charset=UTF-8",
//                    null
//                )
//            } ?: viewBinding.serviceDetailWebview.loadData(
//                it,
//                "text/html; charset=UTF-8",
//                null
//            )
            val path = "<![CDATA[$it]]>"
            viewBinding.serviceDetailWebview.loadData(
                path,
                "text/html; charset=UTF-8",
                null)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        viewBinding.serviceDetailWebview.isVerticalScrollBarEnabled = false
        viewBinding.serviceDetailWebview.isHorizontalScrollBarEnabled = false
        viewBinding.serviceDetailWebview.settings.javaScriptEnabled = true
        viewBinding.serviceDetailWebview.settings.useWideViewPort = false
        viewBinding.serviceDetailWebview.settings.loadWithOverviewMode = false
        viewBinding.serviceDetailWebview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val screenWidth = ScreenUtils.getScreenWidth(this@ServiceDetailActivity)
                val width =
                    (screenWidth - DensityUtil.dp2px(this@ServiceDetailActivity, 50f)).toString()
                val width2 = (DensityUtil.px2dp(
                    this@ServiceDetailActivity,
                    screenWidth.toFloat()
                ) - 40).toString()
                val javascript = "javascript:function ResizeImages() {" +
                        "var myimg,oldwidth;" +
                        "var maxwidth = document.body.clientWidth;" +
                        "for(i=0;i <document.images.length;i++){" +
                        "myimg = document.images[i];" +
                        "if(myimg.width > " + width2 + "){" +
                        "oldwidth = myimg.width;" +
                        "myimg.width =" + width2 + ";" +
                        "}" +
                        "}" +
                        "}"
                view?.let {
                    it.loadUrl(javascript)
                    it.loadUrl("javascript:ResizeImages();")
                }
            }
        }
    }

    private fun initSystemBar() {
        immersionBar {
            statusBarColor(R.color.color_0e62B8)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.service_detail_back_img -> this.finish()
        }
    }
}