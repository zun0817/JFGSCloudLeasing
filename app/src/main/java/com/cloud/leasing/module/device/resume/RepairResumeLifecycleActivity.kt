package com.cloud.leasing.module.device.resume

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityRepairResumeLifecycleBinding
import com.cloud.leasing.module.device.resume.repair.FaultDailyFragment
import com.cloud.leasing.module.device.resume.repair.FaultDataFragment
import com.cloud.leasing.util.ViewTouchUtil
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar

class RepairResumeLifecycleActivity :
    BaseActivity<ActivityRepairResumeLifecycleBinding>(ActivityRepairResumeLifecycleBinding::inflate),
    View.OnClickListener, TabLayout.OnTabSelectedListener {

    companion object {
        fun startActivity(activity: Activity, resumeId: Int) {
            val intent = Intent()
            intent.setClass(activity, RepairResumeLifecycleActivity::class.java)
            intent.putExtra("resumeId", resumeId)
            activity.startActivity(intent)
        }
    }

    private lateinit var faultDataFragment: FaultDataFragment

    private lateinit var faultDailyFragment: FaultDailyFragment

    override fun getPageName() = PageName.REPAIR_RESUME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initFragment()
    }

    private fun initView() {
        viewBinding.repairResumeBackImg.setOnClickListener(this)
        viewBinding.repairResumeTablayout.addOnTabSelectedListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.repairResumeBackImg)
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

    private fun initFragment() {
        val resumeId= intent.getIntExtra("resumeId", 0)
        faultDataFragment = FaultDataFragment.newInstance()
        faultDailyFragment = FaultDailyFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.repair_resume_frame, faultDataFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.repair_resume_frame, faultDailyFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(faultDailyFragment)
            .commitAllowingStateLoss()

        val bundle = Bundle()
        bundle.putInt("resumeId", resumeId)
        faultDataFragment.arguments = bundle
        faultDailyFragment.arguments = bundle
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.repair_resume_back_img -> this.finish()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (tab.position) {
            0 -> {
                fragmentTransaction.hide(faultDailyFragment)
                fragmentTransaction.show(faultDataFragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
            1 -> {
                fragmentTransaction.hide(faultDataFragment)
                fragmentTransaction.show(faultDailyFragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }
}