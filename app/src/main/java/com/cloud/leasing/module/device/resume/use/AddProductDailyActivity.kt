package com.cloud.leasing.module.device.resume.use

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.dialoglibrary.BaseDialog
import com.cloud.leasing.R
import com.cloud.leasing.adapter.AddFaultAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.ProductDailyFaultBean
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddProductDailyBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.getTime
import com.cloud.leasing.util.toast
import com.cloud.pickerviewlibrary.builder.TimePickerBuilder
import com.cloud.pickerviewlibrary.view.TimePickerView
import com.cloud.popwindow.WheelView
import com.gyf.immersionbar.ktx.immersionBar
import java.util.*

class AddProductDailyActivity :
    BaseActivity<ActivityAddProductDailyBinding>(ActivityAddProductDailyBinding::inflate),
    View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    companion object {
        fun startActivity(activity: Activity, resumeId: Int) {
            val intent = Intent()
            intent.setClass(activity, AddProductDailyActivity::class.java)
            intent.putExtra("resumeId", resumeId)
            activity.startActivity(intent)
        }
    }

    private var isFault = 0

    private var resumeId = 0

    private var drivingType = ""

    private var enterTime = ""

    private lateinit var dialog: BaseDialog

    private lateinit var timePickerView: TimePickerView

    private lateinit var addFaultAdapter: AddFaultAdapter

    private var faultlist = mutableListOf<ProductDailyFaultBean>()

    private val viewModel: AddProductDailyViewModel by viewModels()

    override fun getPageName() = PageName.ADD_PRODUCT_DAILY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initTypeDialog()
        initDateDialog()
        viewModelObserve()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_daily_back_img -> this.finish()
            R.id.add_daily_paramtype_cl -> dialog.show()
            R.id.add_daily_creattime_cl -> timePickerView.show()
            R.id.add_daily_save_btn -> requestOfAddProductDaily()
            R.id.add_daily_fault_fl -> AddFaultActivity.startActivityForResult(this, resumeId)
        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            addDailyLiveData.observe(this@AddProductDailyActivity, { it ->
                viewBinding.addDailyLoadingview.visibility = View.GONE
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "添加日报成功".toast(this@AddProductDailyActivity)
                        this@AddProductDailyActivity.finish()
                    } else {
                        it.toString().toast(this@AddProductDailyActivity)
                    }
                }.onSuccess {
                    "添加日报成功".toast(this@AddProductDailyActivity)
                    this@AddProductDailyActivity.finish()
                }
            })
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.add_daily_yes_rb -> {
                isFault = 1
                viewBinding.layoutAddDailyFault.addDailyDowntimeCl.visibility = View.VISIBLE
                viewBinding.layoutAddDailyFault.addDailyFaultFl.visibility = View.VISIBLE
                viewBinding.layoutAddDailyFault.addDailyRv.visibility = View.VISIBLE
            }
            R.id.add_daily_no_rb -> {
                isFault = 0
                viewBinding.layoutAddDailyFault.addDailyDowntimeCl.visibility = View.GONE
                viewBinding.layoutAddDailyFault.addDailyFaultFl.visibility = View.GONE
                viewBinding.layoutAddDailyFault.addDailyRv.visibility = View.GONE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddFaultActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            data?.let {
                val bean = it.getSerializableExtra("bean") as ProductDailyFaultBean
                faultlist.add(bean)
                addFaultAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun requestOfAddProductDaily() {
        val tunneMeters =
            viewBinding.layoutAddDailyInfo.addDailyTunnemetersEt.text.trim().toString()
        val currentGeology =
            viewBinding.layoutAddDailyInfo.addDailyGeologyEt.text.trim().toString()
        val progressRatio =
            viewBinding.layoutAddDailyInfo.addDailyProgressTv.text.trim().toString()
        val dailyRings =
            viewBinding.layoutAddDailyInfo.addDailyDayringsEt.text.trim().toString()
        val maxDailyRings =
            viewBinding.layoutAddDailyInfo.addDailyMaxringsEt.text.trim().toString()
        val cumRings =
            viewBinding.layoutAddDailyInfo.addDailyTotalringsEt.text.trim().toString()
        val dailyRecord =
            viewBinding.layoutAddDailyInfo.addDailyLogEt.text.trim().toString()
        val produceMatter =
            viewBinding.layoutAddDailyInfo.addDailyMatterEt.text.trim().toString()
        val peopleStaffing =
            viewBinding.layoutAddDailyInfo.addDailyPersonEt.text.trim().toString()
        val deviceStatusDescribe =
            viewBinding.layoutAddDailyInfo.addDailyDescEt.text.trim().toString()
        val faultTime = viewBinding.layoutAddDailyFault.addDailyDowntimeEt.text.trim().toString()
        if (tunneMeters.isBlank()) {
            "请输入当前掘进米数".toast(this)
            return
        }
        if (currentGeology.isBlank()) {
            "请输入当前地质".toast(this)
            return
        }
        if (dailyRings.isBlank()) {
            "请输入日环数".toast(this)
            return
        }
        if (maxDailyRings.isBlank()) {
            "请输入最高日环数".toast(this)
            return
        }
        if (cumRings.isBlank()) {
            "请输入当前累计环数".toast(this)
            return
        }
        if (enterTime.isBlank()) {
            "请选择录入时间".toast(this)
            return
        }
        if (drivingType.isBlank()) {
            "请选择参数类型".toast(this)
            return
        }
        viewModel.requestOfAddProductDaily(
            resumeId,
            enterTime,
            tunneMeters,
            currentGeology,
            progressRatio,
            dailyRings,
            maxDailyRings,
            cumRings,
            dailyRecord,
            produceMatter,
            peopleStaffing,
            deviceStatusDescribe,
            isFault,
            drivingType,
            faultTime,
            faultlist
        )
        viewBinding.addDailyLoadingview.visibility = View.VISIBLE
    }

    private fun initView() {
        resumeId = intent.getIntExtra("resumeId", 0)
        viewBinding.addDailyBackImg.setOnClickListener(this)
        viewBinding.addDailySaveBtn.setOnClickListener(this)
        viewBinding.layoutAddDailyFault.addDailyFaultFl.setOnClickListener(this)
        viewBinding.layoutAddDailyInfo.addDailyCreattimeCl.setOnClickListener(this)
        viewBinding.layoutAddDailyInfo.addDailyParamtypeCl.setOnClickListener(this)
        viewBinding.layoutAddDailyFault.addDailyRg.setOnCheckedChangeListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addDailyBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDailyInfo.addDailyCreattimeCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDailyInfo.addDailyParamtypeCl)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.isAutoMeasureEnabled = true
        viewBinding.layoutAddDailyFault.addDailyRv.layoutManager = linearLayoutManager
        addFaultAdapter = AddFaultAdapter(this, faultlist)
        viewBinding.layoutAddDailyFault.addDailyRv.adapter = addFaultAdapter

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

    private fun initTypeDialog() {
        val builder = BaseDialog.Builder(this)
        dialog = builder
            .setViewId(R.layout.layout_single_popwindow)
            .setWidthHeightpx(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            .setGravity(Gravity.BOTTOM)
            .setAnimation(R.style.Bottom_Top_aniamtion)
            .isOnTouchCanceled(false)
            .setPaddingdp(10, 10, 10, 10)
            .addViewOnClickListener(R.id.popwindow_close_fl) {
                dialog.dismiss()
            }.builder()
        val wheelview = dialog.getView<WheelView>(R.id.popwindow_wheelview)
        val sureview = dialog.getView<FrameLayout>(R.id.popwindow_sure_fl)
        val titleview = dialog.getView<TextView>(R.id.popwindow_title_tv)
        titleview.text = "参数类型"
        wheelview.setTextAlign(WheelView.TEXT_ALIGN_CENTER)
        val list = resources.getStringArray(R.array.drivingtype)
        wheelview.setDataItems(list.toMutableList())
        sureview.setOnClickListener {
            dialog.dismiss()
            drivingType = "0" + (wheelview.getSelectedItemPosition() + 1).toString()
            viewBinding.layoutAddDailyInfo.addDailyParamtypeTv.text =
                wheelview.getSelectedItemData().toString()
            viewBinding.layoutAddDailyInfo.addDailyParamtypeTv.setTextColor(resources.getColor(R.color.color_262626))
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
            enterTime = getTime(date)
            viewBinding.layoutAddDailyInfo.addDailyCreattimeTv.text = getTime(date)
            viewBinding.layoutAddDailyInfo.addDailyCreattimeTv.setTextColor(resources.getColor(R.color.color_262626))
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