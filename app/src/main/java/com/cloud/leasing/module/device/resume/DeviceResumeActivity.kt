package com.cloud.leasing.module.device.resume

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.adapter.DeviceResumeAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.DeviceResumeBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityDeviceResumeBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class DeviceResumeActivity :
    BaseActivity<ActivityDeviceResumeBinding>(ActivityDeviceResumeBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity, deviceId: Int) {
            val intent = Intent()
            intent.setClass(activity, DeviceResumeActivity::class.java)
            intent.putExtra("id", deviceId)
            activity.startActivity(intent)
        }
    }

    private var deviceId = 0

    private lateinit var list: MutableList<DeviceResumeBean>

    private lateinit var deviceResumeAdapter: DeviceResumeAdapter

    private val viewModel: DeviceResumeViewModel by viewModels()

    override fun getPageName() = PageName.DEVICE_RESUME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        viewModelObserve()
        initView()
    }

    private fun initView() {
        deviceId = intent.getIntExtra("id", 0)
        viewBinding.deviceResumeBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.deviceResumeBackImg)
        deviceResumeAdapter = DeviceResumeAdapter(this, mutableListOf())
        viewBinding.deviceResumeListview.adapter = deviceResumeAdapter
        viewModel.requestOfDeviceResume(deviceId)
        viewBinding.deviceResumeLoadingview.visibility = View.VISIBLE
    }

    private fun viewModelObserve() {
        viewModel.apply {
            resumeLiveData.observe(this@DeviceResumeActivity, { it ->
                viewBinding.deviceResumeLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@DeviceResumeActivity)
                    viewBinding.deviceResumeErrorview.showNetworkError({
                        viewBinding.deviceResumeLoadingview.visibility = View.VISIBLE
                        viewModel.requestOfDeviceResume(deviceId)
                    })
                }.onSuccess {
                    if (it.size > 0) {
                        list = it
                        deviceResumeAdapter.refreshData(it)
                    } else {
                        viewBinding.deviceResumeErrorview.showEmpty()
                    }
                }
            })
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.device_resume_back_img -> this.finish()
        }
    }
}