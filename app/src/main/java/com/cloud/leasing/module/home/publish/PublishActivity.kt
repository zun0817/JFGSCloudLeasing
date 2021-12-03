package com.cloud.leasing.module.home.publish

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityPublishBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar

class PublishActivity : BaseActivity<ActivityPublishBinding>(ActivityPublishBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, PublishActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getPageName() = PageName.PUBLISH

    private val viewModel: PublishViewModel by viewModels()

    private var mineDeviceFragment: MineDeviceFragment? = null

    private var minePublishFragment: MinePublishFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initFragment()
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

    private fun initView() {
        viewBinding.publishBackImg.setOnClickListener(this)
        viewBinding.publishSearchImg.setOnClickListener(this)
        viewBinding.publishDeviceLl.setOnClickListener(this)
        viewBinding.publishRequireLl.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.publishBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.publishSearchImg)
    }

    private fun initFragment() {
        mineDeviceFragment = MineDeviceFragment.newInstance()
        minePublishFragment = MinePublishFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.publish_fragment_container, mineDeviceFragment!!)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.publish_fragment_container, minePublishFragment!!)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(minePublishFragment!!).commitAllowingStateLoss()
    }

    override fun onClick(v: View?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (v!!.id) {
            R.id.publish_back_img -> this.finish()
            R.id.publish_search_img -> {
            }
            R.id.publish_device_ll -> {
                checkNull()
                viewBinding.publishDeviceTv.setTextColor(resources.getColor(R.color.color_0E64BC))
                viewBinding.publishDeviceView.visibility = View.VISIBLE
                viewBinding.publishRequireTv.setTextColor(resources.getColor(R.color.color_999999))
                viewBinding.publishRequireView.visibility = View.INVISIBLE
                fragmentTransaction.hide(minePublishFragment!!)
                fragmentTransaction.show(mineDeviceFragment!!)
                fragmentTransaction.commitAllowingStateLoss()
            }
            R.id.publish_require_ll -> {
                checkNull()
                viewBinding.publishRequireTv.setTextColor(resources.getColor(R.color.color_0E64BC))
                viewBinding.publishRequireView.visibility = View.VISIBLE
                viewBinding.publishDeviceTv.setTextColor(resources.getColor(R.color.color_999999))
                viewBinding.publishDeviceView.visibility = View.INVISIBLE
                fragmentTransaction.hide(mineDeviceFragment!!)
                fragmentTransaction.show(minePublishFragment!!)
                fragmentTransaction.commitAllowingStateLoss()
            }
        }
    }

    private fun checkNull() {
        if (mineDeviceFragment == null) {
            mineDeviceFragment = MineDeviceFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.publish_fragment_container, mineDeviceFragment!!)
                .commitAllowingStateLoss()
        }
        if (minePublishFragment == null) {
            minePublishFragment = MinePublishFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.publish_fragment_container, minePublishFragment!!)
                .commitAllowingStateLoss()
        }
    }

}