package com.cloud.leasing.module.home.more

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.adapter.MoreDeviceAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.HomeDeviceBean
import com.cloud.leasing.bean.Search
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityMoreDeviceBinding
import com.cloud.leasing.module.home.detail.DeviceDetailActivity
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class MoreDeviceActivity :
    BaseActivity<ActivityMoreDeviceBinding>(ActivityMoreDeviceBinding::inflate),
    View.OnClickListener, AdapterView.OnItemClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, MoreDeviceActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val viewModel: MoreDeviceViewModel by viewModels()

    private lateinit var moreDeviceAdapter: MoreDeviceAdapter

    private lateinit var list: MutableList<Search>

    override fun getPageName() = PageName.MORE_DEVICE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initSystemBar()
        viewModelObserve()
    }

    private fun initView() {
        viewBinding.moreDeviceBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.moreDeviceBackImg)
        list = mutableListOf()
        moreDeviceAdapter = MoreDeviceAdapter(this, list)
        viewBinding.moreDeviceListview.adapter = moreDeviceAdapter
        //viewModel.requestOfHomeDevices()
        viewModel.requestOfQueryData(1)
        viewBinding.moreDeviceLoadingview.visibility = View.VISIBLE
        viewBinding.moreDeviceListview.onItemClickListener = this
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

    private fun viewModelObserve() {
        viewModel.apply {
//            deviceLiveData.observe(
//                this@MoreDeviceActivity, { it ->
//                    viewBinding.moreDeviceLoadingview.visibility = View.GONE
//                    it.onFailure {
//                        it.toString().toast(this@MoreDeviceActivity)
//                    }.onSuccess {
//                        list = it
//                        moreDeviceAdapter.refreshData(it)
//                    }
//                })
            searchDeviceLiveData.observe(this@MoreDeviceActivity, { it ->
                viewBinding.moreDeviceLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@MoreDeviceActivity)
                }.onSuccess {
                    list = it.sbData.sbList
                    moreDeviceAdapter.refreshData(list)
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.more_device_back_img -> this.finish()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        DeviceDetailActivity.startActivity(this, list[position].id)
    }
}