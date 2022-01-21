package com.cloud.leasing.module.device.resume.store

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.cloud.dialoglibrary.BaseDialog
import com.cloud.leasing.R
import com.cloud.leasing.adapter.AddDailyCheckAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.CheckDailyItemBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddCheckDailyBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.getTime
import com.cloud.leasing.util.toast
import com.cloud.pickerviewlibrary.builder.TimePickerBuilder
import com.cloud.pickerviewlibrary.view.TimePickerView
import com.gyf.immersionbar.ktx.immersionBar
import java.util.*

class AddCheckDailyActivity :
    BaseActivity<ActivityAddCheckDailyBinding>(ActivityAddCheckDailyBinding::inflate),
    View.OnClickListener, AdapterView.OnItemClickListener {

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

    private var list = mutableListOf<CheckDailyItemBean>()

    private lateinit var timePickerView: TimePickerView

    private lateinit var addDailyCheckAdapter: AddDailyCheckAdapter

    override fun getPageName() = PageName.STORE_CHECK_DAILY

    private val viewModel: AddCheckDailyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        viewModelObserve()
        initSystemBar()
        initDateDialog()
    }

    private fun initView() {
        viewBinding.checkBackImg.setOnClickListener(this)
        viewBinding.checkSaveBtn.setOnClickListener(this)
        viewBinding.checkDatetimeFrame.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.checkBackImg)
        resumeId = intent.getIntExtra("resumeId", 0)
        viewModel.requestOfStoreCheck(resumeId)
        addDailyCheckAdapter = AddDailyCheckAdapter(this, list)
        viewBinding.checkListview.adapter = addDailyCheckAdapter
        viewBinding.checkListview.onItemClickListener = this
    }

    private fun viewModelObserve() {
        viewModel.apply {
            checkItemLiveData.observe(this@AddCheckDailyActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddCheckDailyActivity)
                }.onSuccess {
                    list = it
                    addDailyCheckAdapter.refreshData(list)
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.check_back_img -> this.finish()
            R.id.check_datetime_frame -> timePickerView.show()
            R.id.check_save_btn -> {
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        initCheckDialog(position)
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
            viewBinding.checkDatetimeTv.text = getTime(date)
            viewBinding.checkDatetimeTv.setTextColor(resources.getColor(R.color.color_262626))
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

    private fun initCheckDialog(position: Int) {
        val builder = BaseDialog.Builder(this)
        var dialog = builder
            .setViewId(R.layout.layout_check_popwindow)
            .setWidthHeightpx(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            .setGravity(Gravity.CENTER)
            .setAnimation(R.style.Bottom_Top_aniamtion)
            .isOnTouchCanceled(false)
            .setPaddingdp(10, 10, 10, 10)
            .builder()
        dialog.show()
        val sureview = dialog.getView<FrameLayout>(R.id.check_popwindow_sure_fl)
        val closeview = dialog.getView<FrameLayout>(R.id.check_popwindow_close_fl)
        val check_rg = dialog.getView<RadioGroup>(R.id.check_rg)
        val check_yes_rb = dialog.getView<RadioButton>(R.id.check_yes_rb)
        val check_no_rb = dialog.getView<RadioButton>(R.id.check_no_rb)
        val check_desc_et = dialog.getView<EditText>(R.id.check_desc_et)
        val check_remark_et = dialog.getView<EditText>(R.id.check_remark_et)
        check_desc_et.text =
            Editable.Factory.getInstance().newEditable(list[position].coordinateMatter)
        check_remark_et.text =
            Editable.Factory.getInstance().newEditable(list[position].exceptionDetails)
        var deviceStatus = list[position].deviceStatus
        when (deviceStatus) {
            1 -> check_yes_rb.isChecked = true
            2 -> check_no_rb.isChecked = true
        }
        check_rg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.check_yes_rb -> deviceStatus = 1
                R.id.check_no_rb -> deviceStatus = 2
            }
        }
        closeview.setOnClickListener {
            dialog.dismiss()
            dialog = null
        }
        sureview.setOnClickListener {
            val coordinateMatter = check_desc_et.text.trim().toString()
            val exceptionDetails = check_remark_et.text.trim().toString()
            if (coordinateMatter.isBlank()) {
                "请输入异常描述".toast(this)
                return@setOnClickListener
            }
            list[position].deviceStatus = deviceStatus
            list[position].coordinateMatter = coordinateMatter
            list[position].exceptionDetails = exceptionDetails
            addDailyCheckAdapter.refreshData(list)
            dialog.dismiss()
        }
    }

}