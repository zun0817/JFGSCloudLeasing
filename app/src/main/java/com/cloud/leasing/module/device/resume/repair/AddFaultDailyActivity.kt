package com.cloud.leasing.module.device.resume.repair

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.cloud.leasing.R
import com.cloud.leasing.adapter.AddDevicePictureAdapter
import com.cloud.leasing.adapter.AddFaultAdapter
import com.cloud.leasing.adapter.AddRepairPictureAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.CompanyFileBean
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddFaultDailyBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.getTime
import com.cloud.leasing.util.toast
import com.cloud.pickerviewlibrary.builder.TimePickerBuilder
import com.cloud.pickerviewlibrary.view.TimePickerView
import com.giftedcat.picture.lib.selector.MultiImageSelector
import com.gyf.immersionbar.ktx.immersionBar
import java.io.File
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

    private var beginTime = ""

    private var planTime = ""

    private val REQUEST_IMAGE_DEVICE = 1

    private var deleteDeviceIndex = 0

    private var dailyImageUrl = ""

    private var faultList = mutableListOf<CompanyFileBean>()

    private var faultPicList = mutableListOf<String>()

    private lateinit var timePickerView: TimePickerView

    private lateinit var begintimePickerView: TimePickerView

    private lateinit var plantimePickerView: TimePickerView

    private lateinit var addRepairPictureAdapter: AddRepairPictureAdapter

    private val viewModel: AddFaultDailyViewModel by viewModels()

    override fun getPageName() = PageName.ADD_FAULT_DAILY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initDateDialog()
        initBeginDateDialog()
        initPlanDateDialog()
        viewModelObserve()
    }

    private fun initView() {
        resumeId = intent.getIntExtra("resumeId", 0)
        viewBinding.addFaultBackImg.setOnClickListener(this)
        viewBinding.addFaultSaveBtn.setOnClickListener(this)
        viewBinding.layoutRepairDailyInfo.repairDailyDatetimeCl.setOnClickListener(this)
        viewBinding.layoutRepairDailyInfo.repairDailyBegintimeCl.setOnClickListener(this)
        viewBinding.layoutRepairDailyInfo.repairDailyPlantimeCl.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addFaultBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRepairDailyInfo.repairDailyDatetimeCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRepairDailyInfo.repairDailyBegintimeCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRepairDailyInfo.repairDailyPlantimeCl)
        initRepairForm()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_fault_back_img -> this.finish()
            R.id.repair_daily_datetime_cl -> timePickerView.show()
            R.id.repair_daily_begintime_cl -> begintimePickerView.show()
            R.id.repair_daily_plantime_cl -> plantimePickerView.show()
            R.id.add_fault_save_btn -> saveData()
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
            fileLiveData.observe(this@AddFaultDailyActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddFaultDailyActivity)
                }.onSuccess {
                    "上传成功".toast(this@AddFaultDailyActivity)
                    faultList.add(it)
                }
            })
            deleteFileLiveData.observe(this@AddFaultDailyActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddFaultDailyActivity)
                }.onSuccess {
                    "删除文件成功".toast(this@AddFaultDailyActivity)
                    faultList.removeAt(deleteDeviceIndex)
                }
            })
        }
    }

    private fun saveData() {
        val ratio = viewBinding.layoutRepairDailyInfo.repairDailyRatioEt.text.trim().toString()
        val jobcontent =
            viewBinding.layoutRepairDailyInfo.repairDailyJobcontentEt.text.trim().toString()
        val safetyaccident =
            viewBinding.layoutRepairDailyInfo.repairDailySafetyaccidentEt.text.trim().toString()
        val assistmatter =
            viewBinding.layoutRepairDailyInfo.repairDailyAssistmatterEt.text.trim().toString()
        val remarks = viewBinding.layoutRepairDailyInfo.repairDailyRemarksEt.text.trim().toString()
        if (datetime.isBlank()) {
            "请选择录入时间".toast(this)
            return
        }
        if (beginTime.isBlank()) {
            "请选择开始时间".toast(this)
            return
        }
        if (planTime.isBlank()) {
            "请选择计划时间".toast(this)
            return
        }
        if (ratio.isBlank()) {
            "请输入完成比例".toast(this)
            return
        }
        if (jobcontent.isBlank()) {
            "请输入工作内容".toast(this)
            return
        }
        if (faultList.size <= 0) {
            "请上传图片".toast(this)
            return
        }
        faultList.forEach {
            dailyImageUrl = dailyImageUrl + "," + it.filePath
        }
        viewModel.requestOfAddProductDaily(
            resumeId,
            datetime,
            ratio,
            safetyaccident,
            jobcontent,
            assistmatter,
            remarks,
            dailyImageUrl,
            beginTime,
            planTime
        )
    }

    private fun initRepairForm() {
        val gridLayoutManager = GridLayoutManager(this, 3)
        viewBinding.layoutRepairDailyUpload.repairDailyPictureRv.layoutManager = gridLayoutManager
        gridLayoutManager.isAutoMeasureEnabled = true
        addRepairPictureAdapter = AddRepairPictureAdapter(
            this,
            faultPicList,
            viewBinding.layoutRepairDailyUpload.repairDailyPictureRv
        )
        addRepairPictureAdapter.setMaxSize(5)
        viewBinding.layoutRepairDailyUpload.repairDailyPictureRv.adapter = addRepairPictureAdapter
        addRepairPictureAdapter.setOnAddPicturesListener {
            pickImageOfDevice()
        }
        addRepairPictureAdapter.setOnDeleteFileListener {
            deleteDeviceIndex = it - 1
            viewModel.requestOfDeleteFile(faultList[deleteDeviceIndex].filePath)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_DEVICE && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                faultPicList.addAll(select)
                addRepairPictureAdapter.notifyDataSetChanged()
                val file = File(select[0])
                viewModel.requestOfUploadFile(file, "90")
            }
        }
    }

    private fun pickImageOfDevice() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(1)
        selector.single()
        selector.origin(faultPicList)
        selector.start(this, REQUEST_IMAGE_DEVICE)
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

    private fun initBeginDateDialog() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.time = Date(System.currentTimeMillis())
        val endDate = Calendar.getInstance()
        endDate.set(2029, 12, 31)
        begintimePickerView = TimePickerBuilder(this) { date, _ ->
            beginTime = getTime(date)
            viewBinding.layoutRepairDailyInfo.repairDailyBegintimeTv.text = getTime(date)
            viewBinding.layoutRepairDailyInfo.repairDailyBegintimeTv.setTextColor(
                resources.getColor(
                    R.color.color_262626
                )
            )
        }.setDate(selectedDate).setRangDate(startDate, endDate)
            .setLayoutRes(R.layout.layout_date_popwindow) { v ->
                val tvSubmit = v.findViewById<FrameLayout>(R.id.date_popwindow_sure_fl)
                val ivCancel = v.findViewById<FrameLayout>(R.id.date_popwindow_close_fl)
                tvSubmit.setOnClickListener {
                    begintimePickerView.returnData()
                    begintimePickerView.dismiss()
                }
                ivCancel.setOnClickListener { begintimePickerView.dismiss() }
            }
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setLineSpacingMultiplier(2.2f)
            .isCenterLabel(false)
            .setOutSideCancelable(false)
            .setDividerColor(Color.WHITE)
            .build()
    }

    private fun initPlanDateDialog() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.time = Date(System.currentTimeMillis())
        val endDate = Calendar.getInstance()
        endDate.set(2029, 12, 31)
        plantimePickerView = TimePickerBuilder(this) { date, _ ->
            planTime = getTime(date)
            viewBinding.layoutRepairDailyInfo.repairDailyPlantimeTv.text = getTime(date)
            viewBinding.layoutRepairDailyInfo.repairDailyPlantimeTv.setTextColor(
                resources.getColor(
                    R.color.color_262626
                )
            )
        }.setDate(selectedDate).setRangDate(startDate, endDate)
            .setLayoutRes(R.layout.layout_date_popwindow) { v ->
                val tvSubmit = v.findViewById<FrameLayout>(R.id.date_popwindow_sure_fl)
                val ivCancel = v.findViewById<FrameLayout>(R.id.date_popwindow_close_fl)
                tvSubmit.setOnClickListener {
                    plantimePickerView.returnData()
                    plantimePickerView.dismiss()
                }
                ivCancel.setOnClickListener { plantimePickerView.dismiss() }
            }
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setLineSpacingMultiplier(2.2f)
            .isCenterLabel(false)
            .setOutSideCancelable(false)
            .setDividerColor(Color.WHITE)
            .build()
    }

}