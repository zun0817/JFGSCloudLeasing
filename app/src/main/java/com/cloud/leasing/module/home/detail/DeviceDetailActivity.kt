package com.cloud.leasing.module.home.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityDeviceDetailBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar

class DeviceDetailActivity :
    BaseActivity<ActivityDeviceDetailBinding>(ActivityDeviceDetailBinding::inflate),
    View.OnClickListener, TabLayout.OnTabSelectedListener, NestedScrollView.OnScrollChangeListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, DeviceDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var tabIndex = 0

    private var scrollviewFlag = false

    override fun getPageName() = PageName.DEVICE_DETAIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.deviceDetailBackImg.setOnClickListener(this)
        viewBinding.deviceDetailScrollview.setOnScrollChangeListener(this)
        viewBinding.deviceDetailTablayout.addOnTabSelectedListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.deviceDetailBackImg)
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
            R.id.device_detail_back_img -> this.finish()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        if (!scrollviewFlag) {
            when (tab.position) {
                0 -> {
                    viewBinding.deviceDetailScrollview.scrollTo(
                        0,
                        viewBinding.deviceDetailInfoLl.top
                    )
                }
                1 -> {
                    viewBinding.deviceDetailScrollview.scrollTo(
                        0,
                        viewBinding.deviceDetailResumeLl.top
                    )
                }
                2 -> {
                    viewBinding.deviceDetailScrollview.scrollTo(
                        0,
                        viewBinding.deviceDetailAnnexLl.top
                    )
                }
                3 -> {
                    viewBinding.deviceDetailScrollview.scrollTo(
                        0,
                        viewBinding.deviceDetailUserLl.top
                    )
                }
            }
        }
        scrollviewFlag = false
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    override fun onScrollChange(
        v: NestedScrollView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        scrollviewFlag = true
        tabIndex = viewBinding.deviceDetailTablayout.selectedTabPosition
        if (scrollY < viewBinding.deviceDetailResumeLl.top) {
            if (tabIndex != 0) {//增加判断，如果滑动的区域是tableIndex=0对应的区域，则不改变tablayout的状态
                viewBinding.deviceDetailTablayout.selectTab(
                    viewBinding.deviceDetailTablayout.getTabAt(
                        0
                    )
                )
            }
        } else if (scrollY >= viewBinding.deviceDetailResumeLl.top && scrollY < viewBinding.deviceDetailAnnexLl.top) {
            if (tabIndex != 1) {
                viewBinding.deviceDetailTablayout.selectTab(
                    viewBinding.deviceDetailTablayout.getTabAt(
                        1
                    )
                )
            }
        } else if (scrollY >= viewBinding.deviceDetailAnnexLl.top && scrollY < viewBinding.deviceDetailUserLl.top) {
            if (tabIndex != 2) {
                viewBinding.deviceDetailTablayout.selectTab(
                    viewBinding.deviceDetailTablayout.getTabAt(
                        2
                    )
                )
            }
        } else if (scrollY >= viewBinding.deviceDetailUserLl.top) {
            if (tabIndex != 3) {
                viewBinding.deviceDetailTablayout.selectTab(
                    viewBinding.deviceDetailTablayout.getTabAt(
                        3
                    )
                )
            }
        }
        scrollviewFlag = false
    }

}