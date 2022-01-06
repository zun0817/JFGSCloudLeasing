package com.cloud.leasing.module.home.want

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.dialoglibrary.BaseDialog
import com.cloud.leasing.R
import com.cloud.leasing.adapter.AddRequireFileAdapter
import com.cloud.leasing.adapter.AddRequirePictureAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.CutterType
import com.cloud.leasing.bean.DeviceBrand
import com.cloud.leasing.bean.DeviceType
import com.cloud.leasing.bean.ProvinceBean
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddRequireBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.getTime
import com.cloud.leasing.util.toast
import com.cloud.pickerviewlibrary.builder.TimePickerBuilder
import com.cloud.pickerviewlibrary.view.TimePickerView
import com.cloud.popwindow.WheelView
import com.giftedcat.picture.lib.selector.MultiImageSelector
import com.gyf.immersionbar.ktx.immersionBar
import com.sky.filepicker.upload.Constants
import com.sky.filepicker.upload.LocalUpdateActivity
import java.util.*


class AddRequireActivity :
    BaseActivity<ActivityAddRequireBinding>(ActivityAddRequireBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, AddRequireActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val REQUEST_IMAGE = 1

    private var deviceType = ""

    private var deviceBrand = ""

    private var cutterType = ""

    private var projectLocation = ""

    private var province = ""

    private var usageTime = ""

    private var mPathList = mutableListOf<String>()

    private var mSelectList = mutableListOf<String>()

    private var dialog: BaseDialog? = null

    private lateinit var timePickerView: TimePickerView

    private lateinit var provinceList: MutableList<ProvinceBean>

    private lateinit var typeList: MutableList<DeviceType>

    private lateinit var brandList: MutableList<DeviceBrand>

    private lateinit var cutterList: MutableList<CutterType>

    private lateinit var addRequireFileAdapter: AddRequireFileAdapter

    private lateinit var addRequirePictureAdapter: AddRequirePictureAdapter

    private val viewModel: AddRequireViewModel by viewModels()

    override fun getPageName() = PageName.ADDREQUIRE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initDateDialog()
        viewModelObserve()
    }

    private fun initView() {
        viewBinding.addRequireNextBtn.setOnClickListener(this)
        viewBinding.addRequireBackImg.setOnClickListener(this)
        viewBinding.addRequirePreviousBtn.setOnClickListener(this)
        viewBinding.layoutAddRequireUpload.addRequireFileFl.setOnClickListener(this)

        viewBinding.layoutAddRequireInfo.addRequireBrandCl.setOnClickListener(this)
        viewBinding.layoutAddRequireInfo.addRequireTypeCl.setOnClickListener(this)
        viewBinding.layoutAddRequireInfo.addRequireSiteCl.setOnClickListener(this)
        viewBinding.layoutAddRequireInfo.addRequireUsetimeCl.setOnClickListener(this)
        viewBinding.layoutAddRequireInfo.addRequireKnifetypeCl.setOnClickListener(this)

        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addRequireBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddRequireInfo.addRequireBrandCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddRequireInfo.addRequireTypeCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddRequireInfo.addRequireSiteCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddRequireInfo.addRequireUsetimeCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddRequireInfo.addRequireKnifetypeCl)

        viewBinding.layoutAddRequireUpload.addRequirePictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addRequirePictureAdapter = AddRequirePictureAdapter(
            this,
            mSelectList,
            viewBinding.layoutAddRequireUpload.addRequirePictureRv
        )
        addRequirePictureAdapter.setMaxSize(5)
        viewBinding.layoutAddRequireUpload.addRequirePictureRv.adapter = addRequirePictureAdapter
        addRequirePictureAdapter.setOnAddPicturesListener {
            pickImage()
        }

        viewBinding.layoutAddRequireUpload.addRequireFileRv.layoutManager =
            LinearLayoutManager(this)
        addRequireFileAdapter = AddRequireFileAdapter(this, mPathList)
        viewBinding.layoutAddRequireUpload.addRequireFileRv.adapter = addRequireFileAdapter

        viewModel.requestOfDeviceSite()
        viewModel.requestOfDeviceType()
        viewBinding.addRequireLoadingview.visibility = View.VISIBLE
    }

    private fun viewModelObserve() {
        viewModel.apply {
            deviceTypeLiveData.observe(this@AddRequireActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddRequireActivity)
                }.onSuccess {
                    typeList = it.deviceType
                    brandList = it.deviceBrand
                    cutterList = it.cutterType
                }
            })
            provinceLiveData.observe(this@AddRequireActivity, { it ->
                viewBinding.addRequireLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@AddRequireActivity)
                }.onSuccess {
                    provinceList = it
                }
            })
            addRequireLiveData.observe(this@AddRequireActivity, { it ->
                viewBinding.addRequireLoadingview.visibility = View.GONE
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "添加需求成功".toast(this@AddRequireActivity)
                        this@AddRequireActivity.finish()
                    } else {
                        it.toString().toast(this@AddRequireActivity)
                    }
                }.onSuccess {
                    "添加需求成功".toast(this@AddRequireActivity)
                    this@AddRequireActivity.finish()
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

    private fun pickImage() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(5)
        selector.multi()
        selector.origin(mSelectList)
        selector.start(this, REQUEST_IMAGE)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                mSelectList.clear()
                mSelectList.addAll(select)
                addRequirePictureAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == Constants.UPLOAD_FILE_REQUEST && resultCode == Constants.UPLOAD_FILE_RESULT) {
            data?.let { it ->
                val list: MutableList<String> =
                    it.getStringArrayListExtra("pathList") as MutableList<String>
                mPathList.addAll(list)
                addRequireFileAdapter.notifyDataSetChanged()
                list.forEach {
                    Log.d("地址：", it)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_require_back_img -> this.finish()
            R.id.add_require_file_fl -> {
                val intent = Intent(this, LocalUpdateActivity::class.java)
                intent.putExtra("maxNum", 1)
                startActivityForResult(intent, Constants.UPLOAD_FILE_REQUEST)
            }
            R.id.add_require_next_btn -> {
                if (viewBinding.layoutAddRequireInfo.addRequireInfoScrollview.visibility ==
                    View.VISIBLE && !checkRequireInfoOfNull()
                ) {
                    viewBinding.layoutAddRequireInfo.addRequireInfoScrollview.visibility = View.GONE
                    viewBinding.layoutAddRequireUpload.addRequireUploadScrollview.visibility =
                        View.VISIBLE
                    viewBinding.addRequireCircleTv2.setBackgroundResource(R.drawable.shape_circle_0e64bc)
                    viewBinding.addRequireTxtTv2.setTextColor(resources.getColor(R.color.color_0E64BC))
                    viewBinding.addRequirePreviousBtn.visibility = View.VISIBLE
                    viewBinding.addRequireView1.setBackgroundResource(R.drawable.shape_view_0e64bc)
                    viewBinding.addRequireNextBtn.text = "确定"
                } else if (viewBinding.layoutAddRequireUpload.addRequireUploadScrollview.visibility == View.VISIBLE) {
                    requestOfAddRequire()
                }
            }
            R.id.add_require_previous_btn -> {
                viewBinding.layoutAddRequireInfo.addRequireInfoScrollview.visibility =
                    View.VISIBLE
                viewBinding.layoutAddRequireUpload.addRequireUploadScrollview.visibility =
                    View.GONE
                viewBinding.addRequireCircleTv2.setBackgroundResource(R.drawable.shape_circle_e2e2e2)
                viewBinding.addRequireTxtTv2.setTextColor(resources.getColor(R.color.color_BFBFBF))
                viewBinding.addRequireView1.setBackgroundResource(R.drawable.shape_view_eaeaea)
                viewBinding.addRequirePreviousBtn.visibility = View.GONE
                viewBinding.addRequireNextBtn.text = "下一步"
            }
            R.id.add_require_brand_cl -> {
                showBrandDialog()
            }
            R.id.add_require_type_cl -> {
                showTypeDialog()
            }
            R.id.add_require_site_cl -> {
                showCityDialog()
            }
            R.id.add_require_usetime_cl -> {
                timePickerView.show()
            }
            R.id.add_require_knifetype_cl -> {
                showCutterDialog()
            }
        }
    }

    private fun requestOfAddRequire() {
        val projectLength =
            viewBinding.layoutAddRequireInfo.addRequireLengthEt.text.trim().toString()
        val geologicalInfo =
            viewBinding.layoutAddRequireInfo.addRequireGeologyEt.text.trim().toString()
        val demandNum = viewBinding.layoutAddRequireInfo.addRequireAmountEt.text.trim().toString()
        val cutterDiam =
            viewBinding.layoutAddRequireInfo.addRequireDiameterEt.text.trim().toString()
        val turningRadius =
            viewBinding.layoutAddRequireInfo.addRequireRadiusEt.text.trim().toString()
        val outerDiameter =
            viewBinding.layoutAddRequireInfo.addRequireOuterEt.text.trim().toString()
        val openingRate =
            viewBinding.layoutAddRequireInfo.addRequireOpenrateEt.text.trim().toString()
        val drivingTorque =
            viewBinding.layoutAddRequireInfo.addRequireTorqueEt.text.trim().toString()
        val propulsiveForce =
            viewBinding.layoutAddRequireInfo.addRequireThrustEt.text.trim().toString()
        val limitedRange =
            viewBinding.layoutAddRequireInfo.addRequireMileageEt.text.trim().toString()

        viewModel.requestOfAddRequire(
            deviceBrand,
            deviceType,
            province,
            projectLocation,
            projectLength,
            geologicalInfo,
            demandNum,
            cutterDiam,
            turningRadius,
            outerDiameter,
            openingRate,
            usageTime,
            cutterType,
            drivingTorque,
            propulsiveForce,
            limitedRange
        )
        viewBinding.addRequireLoadingview.visibility = View.VISIBLE
    }

    private fun showBrandDialog() {
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
                dialog!!.dismiss()
            }.builder()
        val wheelview = dialog!!.getView<WheelView>(R.id.popwindow_wheelview)
        val sureview = dialog!!.getView<FrameLayout>(R.id.popwindow_sure_fl)
        val titleview = dialog!!.getView<TextView>(R.id.popwindow_title_tv)
        titleview.text = "设备品牌"
        wheelview.setTextAlign(WheelView.TEXT_ALIGN_CENTER)
        wheelview.setDataItems(brandList.map { it.name }.toMutableList())
        dialog?.show()
        sureview.setOnClickListener {
            dialog?.dismiss()
            deviceBrand = wheelview.getSelectedItemData().toString()
            viewBinding.layoutAddRequireInfo.addRequireBrandTv.text = deviceBrand
            viewBinding.layoutAddRequireInfo.addRequireBrandTv.setTextColor(resources.getColor(R.color.color_262626))
        }
    }

    private fun showTypeDialog() {
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
                dialog!!.dismiss()
            }.builder()
        val wheelview = dialog!!.getView<WheelView>(R.id.popwindow_wheelview)
        val sureview = dialog!!.getView<FrameLayout>(R.id.popwindow_sure_fl)
        val titleview = dialog!!.getView<TextView>(R.id.popwindow_title_tv)
        titleview.text = "设备类型"
        wheelview.setTextAlign(WheelView.TEXT_ALIGN_CENTER)
        wheelview.setDataItems(typeList.map { it.name }.toMutableList())
        dialog?.show()
        sureview.setOnClickListener {
            dialog?.dismiss()
            deviceType = wheelview.getSelectedItemData().toString()
            viewBinding.layoutAddRequireInfo.addRequireTypeTv.text = deviceType
            viewBinding.layoutAddRequireInfo.addRequireTypeTv.setTextColor(resources.getColor(R.color.color_262626))
        }
    }

    private fun showCutterDialog() {
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
                dialog!!.dismiss()
            }.builder()
        val wheelview = dialog!!.getView<WheelView>(R.id.popwindow_wheelview)
        val sureview = dialog!!.getView<FrameLayout>(R.id.popwindow_sure_fl)
        val titleview = dialog!!.getView<TextView>(R.id.popwindow_title_tv)
        titleview.text = "刀盘类型"
        wheelview.setTextAlign(WheelView.TEXT_ALIGN_CENTER)
        wheelview.setDataItems(cutterList.map { it.name }.toMutableList())
        dialog?.show()
        sureview.setOnClickListener {
            dialog?.dismiss()
            cutterType = wheelview.getSelectedItemData().toString()
            viewBinding.layoutAddRequireInfo.addRequireKnifetypeTv.text = cutterType
            viewBinding.layoutAddRequireInfo.addRequireKnifetypeTv.setTextColor(resources.getColor(R.color.color_262626))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showCityDialog() {
        val builder = BaseDialog.Builder(this)
        dialog = builder
            .setViewId(R.layout.layout_city_popwindow)
            .setWidthHeightpx(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            .setGravity(Gravity.BOTTOM)
            .setAnimation(R.style.Bottom_Top_aniamtion)
            .isOnTouchCanceled(false)
            .setPaddingdp(10, 10, 10, 10)
            .addViewOnClickListener(R.id.site_popwindow_close_fl) {
                dialog!!.dismiss()
            }.builder()
        val provincewheelview = dialog!!.getView<WheelView>(R.id.province_wheel_view)
        val citywheelview = dialog!!.getView<WheelView>(R.id.city_wheel_view)
        val sureview = dialog!!.getView<FrameLayout>(R.id.site_popwindow_sure_fl)
        val titleview = dialog!!.getView<TextView>(R.id.site_popwindow_title_tv)
        titleview.text = "设备位置"
        provincewheelview.setTextAlign(WheelView.TEXT_ALIGN_CENTER)
        provincewheelview.setDataItems(provinceList.map { it.label }.toMutableList())
        citywheelview.setTextAlign(WheelView.TEXT_ALIGN_CENTER)
        provincewheelview.setOnItemSelectedListener(object : WheelView.OnItemSelectedListener {
            override fun onItemSelected(wheelView: WheelView, data: Any, position: Int) {
                province = provinceList[position].value
                citywheelview.setDataItems(provinceList[position].children.map { it.label }
                    .toMutableList())
            }
        })
        citywheelview.setOnItemSelectedListener(object : WheelView.OnItemSelectedListener {
            override fun onItemSelected(wheelView: WheelView, data: Any, position: Int) {
                projectLocation = provinceList[position].children[position].value
            }
        })
        dialog?.show()
        sureview.setOnClickListener {
            dialog?.dismiss()
            viewBinding.layoutAddRequireInfo.addRequireSiteTv.text =
                provincewheelview.getSelectedItemData()
                    .toString() + citywheelview.getSelectedItemData().toString()
            viewBinding.layoutAddRequireInfo.addRequireSiteTv.setTextColor(resources.getColor(R.color.color_262626))
        }
    }

    private fun initDateDialog() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.time = Date(System.currentTimeMillis())
        val endDate = Calendar.getInstance()
        endDate.set(2029, 12, 31)
        timePickerView = TimePickerBuilder(this) { date, _ ->
            usageTime = getTime(date)
            viewBinding.layoutAddRequireInfo.addRequireUsetimeTv.text = getTime(date)
            viewBinding.layoutAddRequireInfo.addRequireUsetimeTv.setTextColor(resources.getColor(R.color.color_262626))
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

    private fun checkRequireInfoOfNull(): Boolean {
        when {
            deviceBrand.isBlank() -> {
                "请选择设备品牌".toast(this)
                return true
            }
            deviceType.isBlank() -> {
                "请选择设备类型".toast(this)
                return true
            }
            cutterType.isBlank() -> {
                "请选择刀盘类型".toast(this)
                return true
            }
            usageTime.isBlank() -> {
                "请选择预计使用时间".toast(this)
                return true
            }
            viewBinding.layoutAddRequireInfo.addRequireSiteTv.text.trim().toString().isBlank() -> {
                "请选择项目地点".toast(this)
                return true
            }
            viewBinding.layoutAddRequireInfo.addRequireLengthEt.text.trim().toString()
                .isBlank() -> {
                "请输入项目长度".toast(this)
                return true
            }
            viewBinding.layoutAddRequireInfo.addRequireGeologyEt.text.trim().toString()
                .isBlank() -> {
                "请输入地质信息".toast(this)
                return true
            }
            viewBinding.layoutAddRequireInfo.addRequireAmountEt.text.trim().toString()
                .isBlank() -> {
                "请输入需求数量".toast(this)
                return true
            }
            viewBinding.layoutAddRequireInfo.addRequireOuterEt.text.trim().toString().isBlank() -> {
                "请输入管片外径".toast(this)
                return true
            }
            viewBinding.layoutAddRequireInfo.addRequireMileageEt.text.trim().toString()
                .isBlank() -> {
                "请输入限定里程".toast(this)
                return true
            }
        }
        return false
    }

}