package com.cloud.leasing.module.device.resume.use

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.leasing.R
import com.cloud.leasing.adapter.AddMaintenanceAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.AddMaintenanceBean
import com.cloud.leasing.bean.ParamMaintenanceBean
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddMaintenanceBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.getTime
import com.cloud.leasing.util.toast
import com.cloud.pickerviewlibrary.builder.TimePickerBuilder
import com.cloud.pickerviewlibrary.view.TimePickerView
import com.gyf.immersionbar.ktx.immersionBar
import java.util.*

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

    private var dateTime = ""

    private var resumeId = 0

    private lateinit var timePickerView: TimePickerView

    private var usedItemList = mutableListOf<AddMaintenanceBean>()

    private var paramList = mutableListOf<ParamMaintenanceBean>()

    private lateinit var addMaintenanceAdapter: AddMaintenanceAdapter

    private val viewModel: AddMaintenanceViewModel by viewModels()

    override fun getPageName() = PageName.ADD_MAINTENANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initDateDialog()
        viewModelObserve()
    }

    private fun initView() {
        resumeId = intent.getIntExtra("resumeId", 0)
        viewBinding.addMaintenanceBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addMaintenanceBackImg)
        viewModel.requestOfAddMaintenance(resumeId)
        viewBinding.addMaintenanceSubmitBtn.setOnClickListener(this)
        viewBinding.addMaintenanceCreattimeCl.setOnClickListener(this)
        val linearLayoutManager = LinearLayoutManager(this)
        viewBinding.addMaintenanceRecycleview.layoutManager = linearLayoutManager

    }

    private fun viewModelObserve() {
        viewModel.apply {
            addMaintenanceLiveData.observe(this@AddMaintenanceActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddMaintenanceActivity)
                }.onSuccess { list ->
                    usedItemList = list
                    usedItemList.forEach {
                        it.exceptionDetails = ""
                        it.isAbnormal = 0
                        it.realPicture = ""
                    }
                    addMaintenanceAdapter =
                        AddMaintenanceAdapter(this@AddMaintenanceActivity, usedItemList)
                    viewBinding.addMaintenanceRecycleview.adapter = addMaintenanceAdapter

                    addMaintenanceAdapter.setOnItemChildrenListener { _, data ->
                        val bean = data as AddMaintenanceBean
                        EditMaintenanceActivity.startActivity(this@AddMaintenanceActivity, bean)
                    }
                }
            })
            submitLiveData.observe(this@AddMaintenanceActivity, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "提交成功".toast(this@AddMaintenanceActivity)
                        this@AddMaintenanceActivity.finish()
                    } else {
                        it.toString().toast(this@AddMaintenanceActivity)
                    }
                }.onSuccess {
                    "提交成功".toast(this@AddMaintenanceActivity)
                    this@AddMaintenanceActivity.finish()
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
            R.id.add_maintenance_creattime_cl -> timePickerView.show()
            R.id.add_maintenance_submit_btn -> {
                if (dateTime.isBlank()) {
                    "请选择日期".toast(this)
                    return
                }
                usedItemList.forEach {
                    val bean = ParamMaintenanceBean()
                    bean.createTime = it.createTime
                    bean.exceptionDetails = it.exceptionDetails.toString()
                    bean.id = it.id
                    bean.isAbnormal = it.isAbnormal.toString()
                    bean.mtnceName = it.mtnceName
                    bean.mtncePosition = it.mtncePosition
                    bean.mtnceSystem = it.mtnceSystem
                    bean.needingAttention = it.needingAttention
                    bean.realPicture = it.realPicture.toString()
                    bean.refPicture = it.refPicture
                    bean.resumeId = it.resumeId
                    paramList.add(bean)
                }
                viewModel.requestOfSubmitMaintenance(resumeId, dateTime, paramList)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE && resultCode == RESULT_OK) {
            data?.let { it ->
                val bean = it.getSerializableExtra("param") as AddMaintenanceBean
                usedItemList.forEach {
                    if (it.id == bean.id) {
                        it.exceptionDetails = bean.exceptionDetails
                        it.isAbnormal = bean.isAbnormal
                        it.realPicture = bean.realPicture
                    }
                }
            }
        }
    }

    private fun initDateDialog() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.time = Date(System.currentTimeMillis())
        val endDate = Calendar.getInstance()
        endDate.set(2029, 12, 31)
        timePickerView = TimePickerBuilder(this) { date, _ ->
            dateTime = getTime(date)
            viewBinding.addMaintenanceCreattimeTv.text = getTime(date)
            viewBinding.addMaintenanceCreattimeTv.setTextColor(resources.getColor(R.color.color_262626))
        }.setDate(selectedDate).setRangDate(startDate, endDate)
            .setLayoutRes(R.layout.layout_date_popwindow) { v ->
                val tvSubmit = v.findViewById<FrameLayout>(R.id.date_popwindow_sure_fl)
                val ivCancel = v.findViewById<FrameLayout>(R.id.date_popwindow_close_fl)
                tvSubmit.setOnClickListener {
                    timePickerView.returnData()
                    timePickerView.dismiss()
                }
                ivCancel.setOnClickListener { timePickerView.dismiss() }
            }
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setLineSpacingMultiplier(2.2f)
            .isCenterLabel(false)
            .setOutSideCancelable(false)
            .setDividerColor(Color.WHITE)
            .build()
    }
}