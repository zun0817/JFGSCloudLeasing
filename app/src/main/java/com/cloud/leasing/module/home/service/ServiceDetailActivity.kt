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
import com.cloud.leasing.util.DensityUtil
import com.cloud.leasing.util.ScreenUtils
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar


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
            email: String,
            type: Int
        ) {
            val intent = Intent()
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            intent.putExtra("name", name)
            intent.putExtra("phone", phone)
            intent.putExtra("email", email)
            intent.putExtra("type", type)
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
        when (intent.getIntExtra("type", 0)) {
            0 -> {
                viewBinding.serviceDetailTitleTv.text = "????????????"
                viewBinding.serviceDetailContent1Tv.text =
                    "         " + resources.getString(R.string.gongchengshigong_one)
                viewBinding.serviceDetailContent2Tv.text =
                    "         " + resources.getString(R.string.gongchengshigong_two)
                viewBinding.serviceDetailTitle1Tv.visibility = View.GONE
                viewBinding.serviceDetailTitle2Tv.visibility = View.GONE
                viewBinding.serviceDetailTitle3Tv.visibility = View.GONE
                viewBinding.serviceDetailOne.visibility = View.GONE
                viewBinding.serviceDetailTwo.visibility = View.GONE
                viewBinding.serviceDetailThree.visibility = View.GONE
                viewBinding.serviceDetailFour.visibility = View.GONE
                viewBinding.serviceDetailFive.visibility = View.GONE
                viewBinding.serviceDetailContent3Tv.visibility = View.GONE
            }
            1 -> {
                viewBinding.serviceDetailTitleTv.text = "????????????"
                viewBinding.serviceDetailContent1Tv.text =
                    "         " + resources.getString(R.string.shebeipinggu_one)
                viewBinding.serviceDetailContent2Tv.text =
                    "         " + resources.getString(R.string.shebeipinggu_two)
                viewBinding.serviceDetailContent3Tv.text =
                    "         " + resources.getString(R.string.shebeipinggu_three)
                viewBinding.serviceDetailTitle1Tv.visibility = View.GONE
                viewBinding.serviceDetailTitle2Tv.visibility = View.GONE
                viewBinding.serviceDetailTitle3Tv.visibility = View.GONE
                viewBinding.serviceDetailOne.visibility = View.GONE
                viewBinding.serviceDetailTwo.visibility = View.GONE
                viewBinding.serviceDetailThree.visibility = View.GONE
                viewBinding.serviceDetailFour.visibility = View.GONE
                viewBinding.serviceDetailFive.visibility = View.GONE
            }
            2 -> {
                viewBinding.serviceDetailTitleTv.text = "????????????"
                viewBinding.serviceDetailTitle1Tv.text = "???1???????????????"
                viewBinding.serviceDetailTitle2Tv.text = "???2???????????????"
                viewBinding.serviceDetailContent1Tv.text =
                    "         " + resources.getString(R.string.daoxiangxitong_one)
                viewBinding.serviceDetailContent2Tv.text =
                    "         " + resources.getString(R.string.daoxiangxitong_two)
                viewBinding.serviceDetailOne.visibility = View.GONE
                viewBinding.serviceDetailTwo.visibility = View.GONE
                viewBinding.serviceDetailThree.visibility = View.GONE
                viewBinding.serviceDetailFour.visibility = View.GONE
                viewBinding.serviceDetailFive.visibility = View.GONE
                viewBinding.serviceDetailContent3Tv.visibility = View.GONE
                viewBinding.serviceDetailTitle3Tv.visibility = View.GONE
            }
            3 -> {
                viewBinding.serviceDetailTitleTv.text = "????????????"
                viewBinding.serviceDetailTitle1Tv.text = "???1???????????????"
                viewBinding.serviceDetailTitle2Tv.text = "???2???????????????"
                viewBinding.serviceDetailContent1Tv.text =
                    "         " + resources.getString(R.string.jinengjianding_one)
                viewBinding.serviceDetailContent2Tv.text =
                    "         " + resources.getString(R.string.jinengjianding_two)
                viewBinding.serviceDetailOne.visibility = View.GONE
                viewBinding.serviceDetailTwo.visibility = View.GONE
                viewBinding.serviceDetailThree.visibility = View.GONE
                viewBinding.serviceDetailFour.visibility = View.GONE
                viewBinding.serviceDetailFive.visibility = View.GONE
                viewBinding.serviceDetailContent3Tv.visibility = View.GONE
                viewBinding.serviceDetailTitle3Tv.visibility = View.GONE
            }
            4 -> {
                viewBinding.serviceDetailTitleTv.text = "?????????3D????????????"
                viewBinding.serviceDetailTitle1Tv.text = "???1???????????????"
                viewBinding.serviceDetailTitle2Tv.text = "???2???????????????"
                viewBinding.serviceDetailContent1Tv.text =
                    "         " + resources.getString(R.string.dungouji_one)
                viewBinding.serviceDetailContent2Tv.text =
                    "         " + resources.getString(R.string.dungouji_two)
                viewBinding.serviceDetailOne.visibility = View.GONE
                viewBinding.serviceDetailTwo.visibility = View.GONE
                viewBinding.serviceDetailThree.visibility = View.GONE
                viewBinding.serviceDetailFour.visibility = View.GONE
                viewBinding.serviceDetailFive.visibility = View.GONE
                viewBinding.serviceDetailContent3Tv.visibility = View.GONE
                viewBinding.serviceDetailTitle3Tv.visibility = View.GONE
            }
            5 -> {
                viewBinding.serviceDetailTitleTv.text = "???????????????"
                viewBinding.serviceDetailTitle1Tv.text = "???1???????????????"
                viewBinding.serviceDetailTitle2Tv.text = "???2???????????????"
                viewBinding.serviceDetailTitle3Tv.text = "???3???????????????"
                viewBinding.serviceDetailContent1Tv.text =
                    "         " + resources.getString(R.string.zhuangbeiyun_one)
                viewBinding.serviceDetailContent2Tv.text =
                    "         " + resources.getString(R.string.zhuangbeiyun_two)
                viewBinding.serviceDetailContent3Tv.text =
                    "         " + resources.getString(R.string.zhuangbeiyun_three)
                viewBinding.serviceDetailOne.visibility = View.GONE
                viewBinding.serviceDetailTwo.visibility = View.GONE
                viewBinding.serviceDetailThree.visibility = View.GONE
                viewBinding.serviceDetailFour.visibility = View.GONE
                viewBinding.serviceDetailFive.visibility = View.GONE
                viewBinding.serviceDetailOne.visibility = View.GONE
            }
        }
//        val content = intent.getStringExtra("content")
//        content?.let {
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
//            val path = "<![CDATA[$it]]>"
//            viewBinding.serviceDetailWebview.loadData(
//                path,
//                "text/html; charset=UTF-8",
//                null)
//        }
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