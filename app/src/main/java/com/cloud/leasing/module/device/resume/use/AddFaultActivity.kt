package com.cloud.leasing.module.device.resume.use

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddFaultBinding
import com.gyf.immersionbar.ktx.immersionBar

class AddFaultActivity : BaseActivity<ActivityAddFaultBinding>(ActivityAddFaultBinding::inflate) {

    companion object {
        val REQUEST_CODE = 1001
        fun startActivityForResult(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, AddFaultActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun getPageName() = PageName.ADD_FAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
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

}