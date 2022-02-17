package com.cloud.leasing.module.device.resume.use

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.leasing.R
import com.cloud.leasing.adapter.AddMaintenanceAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.AddMaintenanceBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddMaintenanceBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class AddMaintenanceActivity :
    BaseActivity<ActivityAddMaintenanceBinding>(ActivityAddMaintenanceBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity, resumeId: Int) {
            val intent = Intent()
            intent.setClass(activity, AddMaintenanceActivity::class.java)
            intent.putExtra("resumeId", resumeId)
            activity.startActivity(intent)
        }
    }

    private var list = mutableListOf<AddMaintenanceBean>()

    private lateinit var addMaintenanceAdapter: AddMaintenanceAdapter

    private val viewModel: AddMaintenanceViewModel by viewModels()

    override fun getPageName() = PageName.ADD_MAINTENANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        val resumeId = intent.getIntExtra("resumeId", 0)
        viewBinding.addMaintenanceBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addMaintenanceBackImg)
        viewModel.requestOfAddMaintenance(resumeId)

        val linearLayoutManager = LinearLayoutManager(this)
        viewBinding.addMaintenanceRecycleview.layoutManager = linearLayoutManager

    }

    private fun viewModelObserve() {
        viewModel.apply {
            addMaintenanceLiveData.observe(this@AddMaintenanceActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddMaintenanceActivity)
                }.onSuccess {
                    list = it
                    addMaintenanceAdapter = AddMaintenanceAdapter(this@AddMaintenanceActivity, list)
                    viewBinding.addMaintenanceRecycleview.adapter = addMaintenanceAdapter

                    addMaintenanceAdapter.setOnItemChildrenListener { _, data ->
                        val bean = data as AddMaintenanceBean
                        EditMaintenanceActivity.startActivity(this@AddMaintenanceActivity, bean)
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
            R.id.add_maintenance_back_img -> this.finish()
        }
    }
}