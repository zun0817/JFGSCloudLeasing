package com.cloud.leasing.module.splash

import android.app.Activity
import android.content.Intent
import android.os.CountDownTimer
import android.widget.TextView
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.module.login.LoginActivity

class SplashViewModel : BaseViewModel() {

    private var countDownTimer: CountDownTimer? = null

    @PageName
    override fun getPageName() = PageName.SPLASH

    fun startTimer(activity: Activity, textView: TextView) {
        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView.text = "跳过 " + millisUntilFinished / 1000 + " s"
            }

            override fun onFinish() {
                LoginActivity.startActivity(activity)
                activity.finish()
            }
        }
        countDownTimer!!.start()
    }

    fun cancel() {
        countDownTimer?.apply {
            cancel()
        }
    }

}