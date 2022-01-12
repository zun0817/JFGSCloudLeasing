package com.cloud.leasing.module.splash

import android.os.Bundle
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivitySplashBinding
import com.cloud.leasing.module.login.LoginActivity
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.hideStatusBar
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    override fun getPageName() = PageName.SPLASH

    override fun swipeBackEnable() = false

    private fun initView() {
        viewModel.startTimer(this, viewBinding.splashTimerTv)
        viewBinding.splashTimerTv.setOnClickListener {
            LoginActivity.startActivity(this)
            this.finish()
            viewModel.cancel()
        }
    }

    private fun initSystemBar() {
        immersionBar {
            hideStatusBar()
            fullScreen(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }
}