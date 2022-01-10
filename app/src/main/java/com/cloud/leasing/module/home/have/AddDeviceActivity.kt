package com.cloud.leasing.module.home.have

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.dialoglibrary.BaseDialog
import com.cloud.leasing.R
import com.cloud.leasing.adapter.*
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.*
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddDeviceBinding
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
import java.io.File
import java.util.*


class AddDeviceActivity :
    BaseActivity<ActivityAddDeviceBinding>(ActivityAddDeviceBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, AddDeviceActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var deviceType = ""

    private var deviceBrand = ""

    private var cutterType = ""

    private var deviceSite = ""

    private var deviceArea = ""

    private var drivingPosition = ""

    private var articulate = ""

    private var deviceRentStatus = ""

    private var leasingdate = ""

    private var dialog: BaseDialog? = null

    private lateinit var timePickerView: TimePickerView

    private val REQUEST_IMAGE_DEVICE = 1

    private val REQUEST_IMAGE_MACHINE = 2

    private val REQUEST_IMAGE_HOST = 3

    private val REQUEST_IMAGE_CUTTER = 4

    private var mPathList = mutableListOf<String>()

    private var mDevicePicList = mutableListOf<String>()

    private var mMachinePicList = mutableListOf<String>()

    private var mHostPicList = mutableListOf<String>()

    private var mCutterPicList = mutableListOf<String>()

    private var rentDeviceFileList = mutableListOf<UploadFileBean>()

    private lateinit var addDeviceFileAdapter: AddDeviceFileAdapter

    private lateinit var addDevicePictureAdapter: AddDevicePictureAdapter

    private lateinit var addMachinePictureAdapter: AddMachinePictureAdapter

    private lateinit var addHostPictureAdapter: AddHostPictureAdapter

    private lateinit var addCutterPictureAdapter: AddCutterPictureAdapter

    private lateinit var provinceList: MutableList<ProvinceBean>

    private lateinit var typeList: MutableList<DeviceType>

    private lateinit var brandList: MutableList<DeviceBrand>

    private lateinit var cutterList: MutableList<CutterType>

    private val viewModel: AddDeviceViewModel by viewModels()

    override fun getPageName() = PageName.ADDDEVICE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initDateDialog()
        viewModelObserve()
    }

    private fun initView() {
        viewBinding.addDeviceBackImg.setOnClickListener(this)
        viewBinding.addDeviceNextBtn.setOnClickListener(this)
        viewBinding.addDevicePreviousBtn.setOnClickListener(this)
        viewBinding.layoutAddDeviceUpload.addDeviceFileFl.setOnClickListener(this)
        viewBinding.layoutAddDeviceInfo.addDeviceBrandCl.setOnClickListener(this)
        viewBinding.layoutAddDeviceInfo.addDeviceTypeCl.setOnClickListener(this)
        viewBinding.layoutAddDeviceInfo.addDeviceSiteCl.setOnClickListener(this)
        viewBinding.layoutAddDeviceInfo.addDeviceKnifetypeCl.setOnClickListener(this)
        viewBinding.layoutAddDeviceInfo.addDeviceLeasetimeCl.setOnClickListener(this)
        viewBinding.layoutAddDeviceInfo.addDeviceArticulateCl.setOnClickListener(this)
        viewBinding.layoutAddDeviceInfo.addDeviceStatusCl.setOnClickListener(this)

        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addDeviceBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDeviceInfo.addDeviceBrandCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDeviceInfo.addDeviceTypeCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDeviceInfo.addDeviceSiteCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDeviceInfo.addDeviceKnifetypeCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDeviceInfo.addDeviceLayerCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDeviceInfo.addDeviceLeasetimeCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDeviceInfo.addDeviceArticulateCl)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutAddDeviceInfo.addDeviceStatusCl)

        viewBinding.layoutAddDeviceUpload.addDeviceFileRv.layoutManager =
            LinearLayoutManager(this)
        addDeviceFileAdapter = AddDeviceFileAdapter(this, mPathList)
        viewBinding.layoutAddDeviceUpload.addDeviceFileRv.adapter = addDeviceFileAdapter

        initDeviceForm()
        initMachineForm()
        initHostForm()
        initCutterForm()
        viewModel.requestOfDeviceSite()
        viewModel.requestOfDeviceType()
        viewBinding.addDeviceLoadingview.visibility = View.VISIBLE

        addDeviceFileAdapter.setOnDeleteFileListener {

        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            deviceTypeLiveData.observe(this@AddDeviceActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddDeviceActivity)
                }.onSuccess {
                    typeList = it.deviceType
                    brandList = it.deviceBrand
                    cutterList = it.cutterType
                }
            })
            provinceLiveData.observe(this@AddDeviceActivity, { it ->
                viewBinding.addDeviceLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@AddDeviceActivity)
                }.onSuccess {
                    provinceList = it
                }
            })
            addDeviceLiveData.observe(this@AddDeviceActivity, { it ->
                viewBinding.addDeviceLoadingview.visibility = View.GONE
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "添加设备成功".toast(this@AddDeviceActivity)
                        this@AddDeviceActivity.finish()
                    } else {
                        it.toString().toast(this@AddDeviceActivity)
                    }
                }.onSuccess {
                    "添加设备成功".toast(this@AddDeviceActivity)
                    this@AddDeviceActivity.finish()
                }
            })
            fileLiveData.observe(this@AddDeviceActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddDeviceActivity)
                }.onSuccess {
                    "上传成功".toast(this@AddDeviceActivity)
                }
            })
        }
    }

    private fun initDeviceForm() {
        viewBinding.layoutAddDeviceUpload.addDevicePictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addDevicePictureAdapter = AddDevicePictureAdapter(
            this,
            mDevicePicList,
            viewBinding.layoutAddDeviceUpload.addDevicePictureRv
        )
        addDevicePictureAdapter.setMaxSize(5)
        viewBinding.layoutAddDeviceUpload.addDevicePictureRv.adapter = addDevicePictureAdapter
        addDevicePictureAdapter.setOnAddPicturesListener {
            pickImageOfDevice()
        }
        addDevicePictureAdapter.setOnDeleteFileListener {

        }
    }

    private fun initMachineForm() {
        viewBinding.layoutAddDeviceUpload.addMachinePictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addMachinePictureAdapter = AddMachinePictureAdapter(
            this,
            mMachinePicList,
            viewBinding.layoutAddDeviceUpload.addMachinePictureRv
        )
        addMachinePictureAdapter.setMaxSize(3)
        viewBinding.layoutAddDeviceUpload.addMachinePictureRv.adapter = addMachinePictureAdapter
        addMachinePictureAdapter.setOnAddPicturesListener {
            pickImageOfMachine()
        }
    }

    private fun initHostForm() {
        viewBinding.layoutAddDeviceUpload.addHostPictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addHostPictureAdapter = AddHostPictureAdapter(
            this,
            mHostPicList,
            viewBinding.layoutAddDeviceUpload.addHostPictureRv
        )
        addHostPictureAdapter.setMaxSize(3)
        viewBinding.layoutAddDeviceUpload.addHostPictureRv.adapter = addHostPictureAdapter
        addHostPictureAdapter.setOnAddPicturesListener {
            pickImageOfHost()
        }
    }

    private fun initCutterForm() {
        viewBinding.layoutAddDeviceUpload.addCutterPictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addCutterPictureAdapter = AddCutterPictureAdapter(
            this,
            mCutterPicList,
            viewBinding.layoutAddDeviceUpload.addCutterPictureRv
        )
        addCutterPictureAdapter.setMaxSize(3)
        viewBinding.layoutAddDeviceUpload.addCutterPictureRv.adapter = addCutterPictureAdapter
        addCutterPictureAdapter.setOnAddPicturesListener {
            pickImageOfCutter()
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

    private fun pickImageOfDevice() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(1)
        selector.single()
        selector.origin(mDevicePicList)
        selector.start(this, REQUEST_IMAGE_DEVICE)
    }

    private fun pickImageOfMachine() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(1)
        selector.single()
        selector.origin(mMachinePicList)
        selector.start(this, REQUEST_IMAGE_MACHINE)
    }

    private fun pickImageOfHost() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(1)
        selector.single()
        selector.origin(mHostPicList)
        selector.start(this, REQUEST_IMAGE_HOST)
    }

    private fun pickImageOfCutter() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(1)
        selector.single()
        selector.origin(mCutterPicList)
        selector.start(this, REQUEST_IMAGE_CUTTER)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_DEVICE && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                //mDevicePicList.clear()
                mDevicePicList.addAll(select)
                addDevicePictureAdapter.notifyDataSetChanged()
                val file = File(select[0])
                viewModel.requestOfUploadFile(file, "30")
            }
        } else if (requestCode == REQUEST_IMAGE_MACHINE && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                //mMachinePicList.clear()
                mMachinePicList.addAll(select)
                addMachinePictureAdapter.notifyDataSetChanged()
                val file = File(select[0])
                viewModel.requestOfUploadFile(file, "30")
            }
        } else if (requestCode == REQUEST_IMAGE_HOST && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                //mHostPicList.clear()
                mHostPicList.addAll(select)
                addHostPictureAdapter.notifyDataSetChanged()
                val file = File(select[0])
                viewModel.requestOfUploadFile(file, "30")
            }
        } else if (requestCode == REQUEST_IMAGE_CUTTER && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                //mCutterPicList.clear()
                mCutterPicList.addAll(select)
                addCutterPictureAdapter.notifyDataSetChanged()
                val file = File(select[0])
                viewModel.requestOfUploadFile(file, "30")
            }
        } else if (requestCode == Constants.UPLOAD_FILE_REQUEST && resultCode == Constants.UPLOAD_FILE_RESULT) {
            data?.let { it ->
                val list: MutableList<String> =
                    it.getStringArrayListExtra("pathList") as MutableList<String>
                mPathList.addAll(list)
                addDeviceFileAdapter.notifyDataSetChanged()
                list.forEach {
                    Log.d("地址：", it)
                    val file = File(it)
                    viewModel.requestOfUploadFile(file, "30")
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_device_back_img -> this.finish()
            R.id.add_device_file_fl -> {
                val intent = Intent(this, LocalUpdateActivity::class.java)
                intent.putExtra("maxNum", 1)
                startActivityForResult(intent, Constants.UPLOAD_FILE_REQUEST)
            }
            R.id.add_device_next_btn -> {
                if (viewBinding.layoutAddDeviceInfo.addDeviceInfoScrollview.visibility ==
                    View.VISIBLE && !checkDeviceInfoOfNull()
                ) {
                    viewBinding.layoutAddDeviceInfo.addDeviceInfoScrollview.visibility = View.GONE
                    viewBinding.layoutAddDeviceUpload.addDeviceUploadScrollview.visibility =
                        View.VISIBLE
                    viewBinding.addDeviceCircleTv2.setBackgroundResource(R.drawable.shape_circle_0e64bc)
                    viewBinding.addDeviceTxtTv2.setTextColor(resources.getColor(R.color.color_0E64BC))
                    viewBinding.addDevicePreviousBtn.visibility = View.VISIBLE
                    viewBinding.addDeviceView1.setBackgroundResource(R.drawable.shape_view_0e64bc)
                    viewBinding.addDeviceNextBtn.text = "确定"

                } else if (viewBinding.layoutAddDeviceUpload.addDeviceUploadScrollview.visibility == View.VISIBLE) {
                    requestOfAddDevice()
                }
            }
            R.id.add_device_previous_btn -> {
                viewBinding.layoutAddDeviceInfo.addDeviceInfoScrollview.visibility =
                    View.VISIBLE
                viewBinding.layoutAddDeviceUpload.addDeviceUploadScrollview.visibility =
                    View.GONE
                viewBinding.addDeviceCircleTv2.setBackgroundResource(R.drawable.shape_circle_e2e2e2)
                viewBinding.addDeviceTxtTv2.setTextColor(resources.getColor(R.color.color_BFBFBF))
                viewBinding.addDeviceView1.setBackgroundResource(R.drawable.shape_view_eaeaea)
                viewBinding.addDevicePreviousBtn.visibility = View.GONE
                viewBinding.addDeviceNextBtn.text = "下一步"
            }
            R.id.add_device_brand_cl -> {
                showBrandDialog()
            }
            R.id.add_device_type_cl -> {
                showTypeDialog()
            }
            R.id.add_device_site_cl -> {
                showCityDialog()
            }
            R.id.add_device_knifetype_cl -> {
                showCutterDialog()
            }
            R.id.add_device_leasetime_cl -> {
                timePickerView.show()
            }
            R.id.add_device_articulate_cl -> {
                showJiaojieDialog()
            }
            R.id.add_device_status_cl -> {
                showStatusDialog()
            }
        }
    }

    private fun requestOfAddDevice() {
        val deviceNo = viewBinding.layoutAddDeviceInfo.addDeviceDevicenoEt.text.trim().toString()
        val cutterDiam = viewBinding.layoutAddDeviceInfo.addDeviceDiameterEt.text.trim().toString()
        val beamNum = viewBinding.layoutAddDeviceInfo.addDeviceBeamEt.text.trim().toString()
        val propulsiveForce =
            viewBinding.layoutAddDeviceInfo.addDevicePropelEt.text.trim().toString()
        val drivingTorque = viewBinding.layoutAddDeviceInfo.addDeviceTorqueEt.text.trim().toString()
        val drivingPower = viewBinding.layoutAddDeviceInfo.addDevicePowerEt.text.trim().toString()
        val assetOwnership =
            viewBinding.layoutAddDeviceInfo.addDeviceAscriptionEt.text.trim().toString()
        val applicableStratum =
            viewBinding.layoutAddDeviceInfo.addDeviceLayerEt.text.trim().toString()
        val outerDiameter = viewBinding.layoutAddDeviceInfo.addDeviceOuterEt.text.trim().toString()
        val deviceModel =
            viewBinding.layoutAddDeviceInfo.addDeviceDrivemodelEt.text.trim().toString()
        val drivingForm =
            viewBinding.layoutAddDeviceInfo.addDeviceDriveformEt.text.trim().toString()
        val openingRate = viewBinding.layoutAddDeviceInfo.addDeviceOpenrateEt.text.trim().toString()
        val turningRadius = viewBinding.layoutAddDeviceInfo.addDeviceRadiusEt.text.trim().toString()
        val mileageUsed = viewBinding.layoutAddDeviceInfo.addDeviceMileageEt.text.trim().toString()
        val deviceResume = viewBinding.layoutAddDeviceInfo.addDeviceResumeEt.text.trim().toString()
        val remarks = viewBinding.layoutAddDeviceInfo.addDeviceRemarkEt.text.trim().toString()
        val workingDiam = ""
        val propertyOwner = ""
        val deviceStatus = "0"
        viewModel.requestOfAddDevice(
            deviceNo,
            deviceBrand,
            deviceType,
            deviceArea,
            cutterDiam,
            beamNum,
            propulsiveForce,
            articulate,
            cutterType,
            propertyOwner,
            drivingTorque,
            drivingPower,
            assetOwnership,
            applicableStratum,
            outerDiameter,
            deviceModel,
            drivingForm,
            openingRate,
            turningRadius,
            mileageUsed,
            drivingPosition,
            leasingdate,
            deviceRentStatus,
            deviceResume,
            deviceStatus,
            workingDiam,
            remarks
        )
        viewBinding.addDeviceLoadingview.visibility = View.VISIBLE
    }

    private fun checkDeviceInfoOfNull(): Boolean {
        when {
            deviceBrand.isBlank() -> {
                "请选择设备品牌".toast(this)
                return true
            }
            deviceType.isBlank() -> {
                "请选择设备类型".toast(this)
                return true
            }
            deviceSite.isBlank() -> {
                "请选择设备位置".toast(this)
                return true
            }
            cutterType.isBlank() -> {
                "请选择刀盘类型".toast(this)
                return true
            }
            leasingdate.isBlank() -> {
                "请选择可租赁时间".toast(this)
                return true
            }
            articulate.isBlank() -> {
                "请选择铰接形式".toast(this)
                return true
            }
            deviceRentStatus.isBlank() -> {
                "请选择设备状态".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceInfo.addDeviceDevicenoEt.text.trim().toString()
                .isBlank() -> {
                "请输入设备编号".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceInfo.addDeviceDiameterEt.text.trim().toString()
                .isBlank() -> {
                "请输入刀盘直径".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceInfo.addDeviceBeamEt.text.trim().toString().isBlank() -> {
                "请输入主梁数量".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceInfo.addDevicePropelEt.text.trim().toString().isBlank() -> {
                "请输入总推进力".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceInfo.addDeviceBeamEt.text.trim().toString().isBlank() -> {
                "请输入主梁数量".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceInfo.addDeviceTorqueEt.text.trim().toString().isBlank() -> {
                "请输入驱动扭矩".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceInfo.addDevicePowerEt.text.trim().toString().isBlank() -> {
                "请输入驱动功率".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceInfo.addDeviceLayerEt.text.trim().toString()
                .isBlank() -> {
                "请输入适用地质".toast(this)
                return true
            }
        }
        return false
    }

    private fun checkUserInfoOfNull(): Boolean {
        when {
            viewBinding.layoutAddDeviceUser.addDeviceUsernameEt.text.trim().toString()
                .isBlank() -> {
                "请填写联系人".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceUser.addDevicePhoneEt.text.trim().toString().isBlank() -> {
                "请填写手机号码".toast(this)
                return true
            }
            viewBinding.layoutAddDeviceUser.addDevicePlaceEt.text.trim().toString().isBlank() -> {
                "请填写工作单位".toast(this)
                return true
            }
        }
        return false
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
        wheelview.setOnItemSelectedListener(object : WheelView.OnItemSelectedListener {
            override fun onItemSelected(wheelView: WheelView, data: Any, position: Int) {
                deviceBrand = brandList[position].value
            }
        })
        sureview.setOnClickListener {
            dialog?.dismiss()
            viewBinding.layoutAddDeviceInfo.addDeviceBrandTv.text =
                wheelview.getSelectedItemData().toString()
            viewBinding.layoutAddDeviceInfo.addDeviceBrandTv.setTextColor(resources.getColor(R.color.color_262626))
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
        wheelview.setOnItemSelectedListener(object : WheelView.OnItemSelectedListener {
            override fun onItemSelected(wheelView: WheelView, data: Any, position: Int) {
                deviceType = typeList[position].value
            }
        })
        sureview.setOnClickListener {
            dialog?.dismiss()
            viewBinding.layoutAddDeviceInfo.addDeviceTypeTv.text =
                wheelview.getSelectedItemData().toString()
            viewBinding.layoutAddDeviceInfo.addDeviceTypeTv.setTextColor(resources.getColor(R.color.color_262626))
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
        wheelview.setOnItemSelectedListener(object : WheelView.OnItemSelectedListener {
            override fun onItemSelected(wheelView: WheelView, data: Any, position: Int) {
                cutterType = cutterList[position].value
            }
        })
        sureview.setOnClickListener {
            dialog?.dismiss()
            viewBinding.layoutAddDeviceInfo.addDeviceKnifetypeTv.text =
                wheelview.getSelectedItemData().toString()
            viewBinding.layoutAddDeviceInfo.addDeviceKnifetypeTv.setTextColor(resources.getColor(R.color.color_262626))
        }
    }

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
                deviceArea = provinceList[position].value
                citywheelview.setDataItems(provinceList[position].children.map { it.label }
                    .toMutableList())
            }
        })
        citywheelview.setOnItemSelectedListener(object : WheelView.OnItemSelectedListener {
            override fun onItemSelected(wheelView: WheelView, data: Any, position: Int) {
                drivingPosition = provinceList[position].children[position].value
            }
        })
        dialog?.show()
        sureview.setOnClickListener {
            dialog?.dismiss()
            deviceSite = provincewheelview.getSelectedItemData()
                .toString() + citywheelview.getSelectedItemData().toString()
            viewBinding.layoutAddDeviceInfo.addDeviceSiteTv.text = deviceSite
            viewBinding.layoutAddDeviceInfo.addDeviceSiteTv.setTextColor(resources.getColor(R.color.color_262626))
        }
    }

    private fun showJiaojieDialog() {
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
        titleview.text = "铰接形式"
        wheelview.setTextAlign(WheelView.TEXT_ALIGN_CENTER)
        val list = resources.getStringArray(R.array.jiaojie)
        wheelview.setDataItems(list.toMutableList())
        dialog?.show()
        sureview.setOnClickListener {
            dialog?.dismiss()
            articulate = wheelview.getSelectedItemData().toString()
            viewBinding.layoutAddDeviceInfo.addDeviceArticulateTv.text = articulate
            viewBinding.layoutAddDeviceInfo.addDeviceArticulateTv.setTextColor(resources.getColor(R.color.color_262626))
        }
    }

    private fun showStatusDialog() {
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
        titleview.text = "设备状态"
        wheelview.setTextAlign(WheelView.TEXT_ALIGN_CENTER)
        val list = resources.getStringArray(R.array.devicestatus)
        wheelview.setDataItems(list.toMutableList())
        dialog?.show()
        sureview.setOnClickListener {
            dialog?.dismiss()
            deviceRentStatus = wheelview.getSelectedItemData().toString()
            when (wheelview.getSelectedItemData().toString()) {
                "维修" -> deviceRentStatus = "1"
                "使用" -> deviceRentStatus = "2"
                "存放" -> deviceRentStatus = "3"
            }
            viewBinding.layoutAddDeviceInfo.addDeviceStatusTv.text =
                wheelview.getSelectedItemData().toString()
            viewBinding.layoutAddDeviceInfo.addDeviceStatusTv.setTextColor(resources.getColor(R.color.color_262626))
        }
    }

    private fun initDateDialog() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.time = Date(System.currentTimeMillis())
        val endDate = Calendar.getInstance()
        endDate.set(2029, 12, 31)
        timePickerView = TimePickerBuilder(this) { date, _ ->
            leasingdate = getTime(date)
            viewBinding.layoutAddDeviceInfo.addDeviceLeasetimeTv.text = getTime(date)
            viewBinding.layoutAddDeviceInfo.addDeviceLeasetimeTv.setTextColor(resources.getColor(R.color.color_262626))
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