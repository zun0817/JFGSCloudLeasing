package com.cloud.leasing.module.device.resume.repair

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddFaultDailyBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.getTime
import com.cloud.leasing.util.toast
import com.cloud.pickerviewlibrary.builder.TimePickerBuilder
import com.cloud.pickerviewlibrary.view.TimePickerView
import com.gyf.immersionbar.ktx.immersionBar
import java.util.*

class AddFaultDailyActivity :
    BaseActivity<ActivityAddFaultDailyBinding>(ActivityAddFaultDailyBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity, resumeId: Int) {
            val intent = Intent()
            intent.setClass(activity, AddFaultDailyActivity::class.java)
            intent.putExtra("resumeId", resumeId)
            activity.startActivity(intent)
        }
    }

    private var resumeId = 0

    private var datetime = ""

    private lateinit var timePickerView: TimePickerView

    private val viewModel: AddFaultDailyViewModel by viewModels()

    override fun getPageName() = PageName.ADD_FAULT_DAILY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initDateDialog()
        viewModelObserve()
    }

    private fun initView() {
        resumeId = intent.getIntExtra("resumeId", 0)
        viewBinding.addFaultBackImg.setOnClickListener(this)
        viewBinding.addFaultSaveBtn.setOnClickListener(this)
        viewBinding.layoutRepairDailyInfo.repairDailyDatetimeCl.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addFaultBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRepairDailyInfo.repairDailyDatetimeCl)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_fault_back_img -> this.finish()
            R.id.repair_daily_datetime_cl -> timePickerView.show()
            R.id.add_fault_save_btn -> {
            }
        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            addDailyLiveData.observe(this@AddFaultDailyActivity, { it ->
                viewBinding.addFaultLoadingview.visibility = View.GONE
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "添加日报成功".toast(this@AddFaultDailyActivity)
                        this@AddFaultDailyActivity.finish()
                    } else {
                        it.toString().toast(this@AddFaultDailyActivity)
                    }
                }.onSuccess {
                    "添加日报成功".toast(this@AddFaultDailyActivity)
                    this@AddFaultDailyActivity.finish()
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

    private fun initDateDialog() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        val date = Date(System.currentTimeMillis())
        startDate.time = date
        startDate.add(Calendar.DATE, -2)
        endDate.time = Date(System.currentTimeMillis())
        timePickerView = TimePickerBuilder(this) { date, _ ->
            datetime = getTime(date)
            viewBinding.layoutRepairDailyInfo.repairDailyDatetimeTv.text = getTime(date)
            viewBinding.layoutRepairDailyInfo.repairDailyDatetimeTv.setTextColor(
                resources.getColor(
                    R.color.color_262626
                )
            )
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