package com.cloud.leasing.module.device.resume.store

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddCheckDailyBinding
import com.cloud.leasing.util.getTime
import com.cloud.pickerviewlibrary.builder.TimePickerBuilder
import com.cloud.pickerviewlibrary.view.TimePickerView
import com.gyf.immersionbar.ktx.immersionBar
import java.util.*

class AddCheckDailyActivity :
    BaseActivity<ActivityAddCheckDailyBinding>(ActivityAddCheckDailyBinding::inflate) {

    companion object {
        fun startActivity(activity: Activity, resumeId: Int) {
            val intent = Intent()
            intent.setClass(activity, AddCheckDailyActivity::class.java)
            intent.putExtra("resumeId", resumeId)
            activity.startActivity(intent)
        }
    }

    private var resumeId = 0

    private var datetime = ""

    private lateinit var timePickerView: TimePickerView

    override fun getPageName() = PageName.STORE_CHECK_DAILY

    private val viewModel: AddCheckDailyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initSystemBar()
        initDateDialog()
    }

    private fun initView(){
        resumeId = intent.getIntExtra("resumeId", 0)
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
        startDate.time = Date(System.currentTimeMillis())
        val endDate = Calendar.getInstance()
        endDate.set(2029, 12, 31)
        timePickerView = TimePickerBuilder(this) { date, _ ->
            datetime = getTime(date)
            //viewBinding.layoutRepairDailyInfo.repairDailyPlantimeTv.text = getTime(date)
//            viewBinding.layoutRepairDailyInfo.repairDailyPlantimeTv.setTextColor(
//                resources.getColor(
//                    R.color.color_262626
//                )
//            )
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