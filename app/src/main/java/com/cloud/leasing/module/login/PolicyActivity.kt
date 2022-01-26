package com.cloud.leasing.module.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityPolicyBinding
import com.cloud.leasing.util.DensityUtil
import com.cloud.leasing.util.ScreenUtils
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar

class PolicyActivity : BaseActivity<ActivityPolicyBinding>(ActivityPolicyBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity, type: Int) {
            val intent = Intent()
            intent.setClass(activity, PolicyActivity::class.java)
            intent.putExtra("type", type)
            activity.startActivity(intent)
        }
    }

    override fun getPageName() = PageName.POLICY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebView()
        initView()
        initSystemBar()
    }

    private fun initView() {
        viewBinding.prolicyBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.prolicyBackImg)
        when (intent.getIntExtra("type", 1)) {
            1 -> {
                viewBinding.prolicyTitleTv.text = "用户服务协议"
                val content = resources.getString(R.string.user_agreement)
                viewBinding.prolicyContentTv.loadData(content, "text/html; charset=UTF-8", null)
            }
            2 -> {
                viewBinding.prolicyTitleTv.text = "隐私协议"
                val content = resources.getString(R.string.privacy_policy)
                viewBinding.prolicyContentTv.loadData(content, "text/html; charset=UTF-8", null)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.prolicy_back_img -> this.finish()
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        viewBinding.prolicyContentTv.isVerticalScrollBarEnabled = false
        viewBinding.prolicyContentTv.isHorizontalScrollBarEnabled = false
        viewBinding.prolicyContentTv.settings.javaScriptEnabled = true
        viewBinding.prolicyContentTv.settings.useWideViewPort = false
        viewBinding.prolicyContentTv.settings.loadWithOverviewMode = false
    }

}