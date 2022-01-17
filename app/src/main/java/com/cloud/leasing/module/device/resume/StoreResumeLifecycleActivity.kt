package com.cloud.leasing.module.device.resume

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityStoreResumeLifecycleBinding
import com.cloud.leasing.module.device.resume.store.DailyCheckFragment
import com.cloud.leasing.module.device.resume.store.StoreDataFragment
import com.cloud.leasing.util.ViewTouchUtil
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar

class StoreResumeLifecycleActivity :
    BaseActivity<ActivityStoreResumeLifecycleBinding>(ActivityStoreResumeLifecycleBinding::inflate),
    View.OnClickListener, TabLayout.OnTabSelectedListener {

    companion object {
        fun startActivity(activity: Activity, resumeId: Int) {
            val intent = Intent()
            intent.setClass(activity, StoreResumeLifecycleActivity::class.java)
            intent.putExtra("resumeId", resumeId)
            activity.startActivity(intent)
        }
    }

    private lateinit var storeDataFragment: StoreDataFragment

    private lateinit var dailyCheckFragment: DailyCheckFragment

    override fun getPageName() = PageName.STORE_RESUME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initFragment()
    }

    private fun initView() {
        viewBinding.storeResumeBackImg.setOnClickListener(this)
        viewBinding.storeResumeTablayout.addOnTabSelectedListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.storeResumeBackImg)
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
        storeDataFragment = StoreDataFragment.newInstance()
        dailyCheckFragment = DailyCheckFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.store_resume_frame, storeDataFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.store_resume_frame, dailyCheckFragment)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(dailyCheckFragment)
            .commitAllowingStateLoss()

        val bundle = Bundle()
        bundle.putInt("resumeId", resumeId)
        storeDataFragment.arguments = bundle
        dailyCheckFragment.arguments = bundle
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.store_resume_back_img -> this.finish()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (tab.position) {
            0 -> {
                fragmentTransaction.hide(dailyCheckFragment)
                fragmentTransaction.show(storeDataFragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
            1 -> {
                fragmentTransaction.hide(storeDataFragment)
                fragmentTransaction.show(dailyCheckFragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }
}