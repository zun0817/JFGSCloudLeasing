package com.cloud.leasing.module.device.resume

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityRepairResumeLifecycleBinding
import com.gyf.immersionbar.ktx.immersionBar

class RepairResumeLifecycleActivity :
    BaseActivity<ActivityRepairResumeLifecycleBinding>(ActivityRepairResumeLifecycleBinding::inflate) {

    companion object {
        fun startActivity(activity: Activity, resumeId: Int) {
            val intent = Intent()
            intent.setClass(activity, RepairResumeLifecycleActivity::class.java)
            intent.putExtra("resumeId", resumeId)
            activity.startActivity(intent)
        }
    }

    override fun getPageName() = PageName.REPAIR_RESUME

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