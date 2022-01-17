package com.cloud.leasing.module.device.resume

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityUseResumeLifecycleBinding
import com.cloud.leasing.module.device.resume.use.FaultLedgerFragment
import com.cloud.leasing.module.device.resume.use.MaintenanceFragment
import com.cloud.leasing.module.device.resume.use.ManageDataFragment
import com.cloud.leasing.module.device.resume.use.ProductDailyFragment
import com.cloud.leasing.util.ViewTouchUtil
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar

class UseResumeLifecycleActivity :
    BaseActivity<ActivityUseResumeLifecycleBinding>(ActivityUseResumeLifecycleBinding::inflate),
    View.OnClickListener, TabLayout.OnTabSelectedListener {

    companion object {
        fun startActivity(activity: Activity, resumeId: Int) {
            val intent = Intent()
            intent.setClass(activity, UseResumeLifecycleActivity::class.java)
            intent.putExtra("resumeId", resumeId)
            activity.startActivity(intent)
        }
    }

    private lateinit var manageDataFragment: ManageDataFragment

    private lateinit var productDailyFragment: ProductDailyFragment

    private lateinit var faultLedgerFragment: FaultLedgerFragment

    private lateinit var maintenanceFragment: MaintenanceFragment

    override fun getPageName() = PageName.USE_RESUME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initFragment()
    }

    private fun initView() {
        viewBinding.useResumeBackImg.setOnClickListener(this)
        viewBinding.useResumeTablayout.addOnTabSelectedListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.useResumeBackImg)
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
        manageDataFragment = ManageDataFragment.newInstance()
        productDailyFragment = ProductDailyFragment.newInstance()
        faultLedgerFragment = FaultLedgerFragment.newInstance()
        maintenanceFragment = MaintenanceFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.use_resume_frame, manageDataFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.use_resume_frame, productDailyFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.use_resume_frame, faultLedgerFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.use_resume_frame, maintenanceFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(productDailyFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(faultLedgerFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(maintenanceFragment)
            .commitAllowingStateLoss()

        val bundle = Bundle()
        bundle.putInt("resumeId", resumeId)
        productDailyFragment.arguments = bundle
        faultLedgerFragment.arguments = bundle
        maintenanceFragment.arguments = bundle
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.use_resume_back_img -> this.finish()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (tab.position) {
            0 -> {
                fragmentTransaction.hide(productDailyFragment)
                fragmentTransaction.hide(faultLedgerFragment)
                fragmentTransaction.hide(maintenanceFragment)
                fragmentTransaction.show(manageDataFragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
            1 -> {
                fragmentTransaction.hide(manageDataFragment)
                fragmentTransaction.hide(faultLedgerFragment)
                fragmentTransaction.hide(maintenanceFragment)
                fragmentTransaction.show(productDailyFragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
            2 -> {
                fragmentTransaction.hide(productDailyFragment)
                fragmentTransaction.hide(manageDataFragment)
                fragmentTransaction.hide(maintenanceFragment)
                fragmentTransaction.show(faultLedgerFragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
            3 -> {
                fragmentTransaction.hide(productDailyFragment)
                fragmentTransaction.hide(faultLedgerFragment)
                fragmentTransaction.hide(manageDataFragment)
                fragmentTransaction.show(maintenanceFragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }
}