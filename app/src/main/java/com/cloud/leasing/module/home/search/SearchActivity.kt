package com.cloud.leasing.module.home.search

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import com.cloud.dialoglibrary.BaseDialog
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.CutterType
import com.cloud.leasing.bean.DeviceBrand
import com.cloud.leasing.bean.DeviceType
import com.cloud.leasing.bean.ProvinceBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivitySearchBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.getTime
import com.cloud.leasing.util.hideKeyboard
import com.cloud.leasing.util.toast
import com.cloud.pickerviewlibrary.builder.TimePickerBuilder
import com.cloud.pickerviewlibrary.view.TimePickerView
import com.cloud.popwindow.WheelView
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar
import java.util.*

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate),
    View.OnClickListener, TabLayout.OnTabSelectedListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, SearchActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var isFlag = 1

    private var deviceType = ""

    private var deviceBrand = ""

    private var cutterType = ""

    private var deviceArea = ""

    private var drivingPosition = ""

    private var leasingdate = ""

    private var deviceStatus = ""

    private var tabPosition = 0

    private var dialog: BaseDialog? = null

    private lateinit var timePickerView: TimePickerView

    private lateinit var provinceList: MutableList<ProvinceBean>

    private lateinit var typeList: MutableList<DeviceType>

    private lateinit var brandList: MutableList<DeviceBrand>

    private lateinit var cutterList: MutableList<CutterType>

    private var searchDeviceFragment: SearchDeviceFragment? = null

    private var searchRequireFragment: SearchRequireFragment? = null

    private val viewModel: SearchViewModel by viewModels()

    override fun getPageName() = PageName.SEARCH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initFragment()
        initDateDialog()
        viewModelObserve()
    }

    private fun viewModelObserve() {
        viewModel.apply {
            searchDeviceLiveData.observe(this@SearchActivity, { it ->
                viewBinding.searchLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@SearchActivity)
                }.onSuccess {
                    viewBinding.searchDeviceTv.text = "待租设备(" + it.sbData.sbTotal + ")"
                    if (it.sbData.sbTotal == 0 && it.xqData.xqTotal == 0) {
                        viewBinding.searchTabLl.visibility = View.GONE
                        searchDeviceFragment?.apply {
                            refreshData(it.sbData.sbList)
                        }
                        searchRequireFragment?.apply {
                            refreshData(it.xqData.xqList)
                        }
                    } else if (it.sbData.sbTotal == 0 && it.xqData.xqTotal != 0) {
                        searchDeviceFragment?.apply {
                            refreshData(it.sbData.sbList)
                        }
                        searchRequireFragment?.apply {
                            refreshData(it.xqData.xqList)
                        }
                    } else {
                        viewBinding.searchTabLl.visibility = View.VISIBLE
                        val sbList = it.sbData.sbList
                        searchDeviceFragment?.apply {
                            refreshData(sbList)
                        }
                    }
                }
            })
            searchRequireLiveData.observe(this@SearchActivity, { it ->
                viewBinding.searchLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@SearchActivity)
                }.onSuccess {
                    viewBinding.searchRequireTv.text = "求租信息(" + it.xqData.xqTotal + ")"
                    if (it.sbData.sbTotal == 0 && it.xqData.xqTotal == 0) {
                        viewBinding.searchTabLl.visibility = View.GONE
                        searchDeviceFragment?.apply {
                            refreshData(it.sbData.sbList)
                        }
                        searchRequireFragment?.apply {
                            refreshData(it.xqData.xqList)
                        }
                    } else if (it.sbData.sbTotal != 0 && it.xqData.xqTotal == 0) {
                        searchDeviceFragment?.apply {
                            refreshData(it.sbData.sbList)
                        }
                        searchRequireFragment?.apply {
                            refreshData(it.xqData.xqList)
                        }
                    } else {
                        viewBinding.searchTabLl.visibility = View.VISIBLE
                        val xqList = it.xqData.xqList
                        searchRequireFragment?.apply {
                            refreshData(xqList)
                        }
                    }
                }
            })
            deviceTypeLiveData.observe(this@SearchActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@SearchActivity)
                }.onSuccess {
                    typeList = it.deviceType
                    brandList = it.deviceBrand
                    cutterList = it.cutterType
                }
            })
            provinceLiveData.observe(this@SearchActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@SearchActivity)
                }.onSuccess {
                    provinceList = it
                }
            })
        }
    }

    private fun initView() {
        viewBinding.searchBackImg.setOnClickListener(this)
        viewBinding.searchFilterTv.setOnClickListener(this)
        viewBinding.searchDeviceLl.setOnClickListener(this)
        viewBinding.searchRequireLl.setOnClickListener(this)
        viewBinding.searchTv.setOnClickListener(this)
        viewBinding.layoutDeviceFilter.deviceFilterCloseImg.setOnClickListener(this)
        viewBinding.layoutRequireFilter.requireFilterCloseImg.setOnClickListener(this)
        viewBinding.layoutDeviceFilter.deviceFilterResetTv.setOnClickListener(this)
        viewBinding.layoutDeviceFilter.deviceFilterSureTv.setOnClickListener(this)
        viewBinding.layoutRequireFilter.requireFilterResetTv.setOnClickListener(this)
        viewBinding.layoutRequireFilter.requireFilterSureTv.setOnClickListener(this)

        viewBinding.layoutDeviceFilter.deviceFilterCuttertypeTv.setOnClickListener(this)
        viewBinding.layoutDeviceFilter.deviceFilterBrandTv.setOnClickListener(this)
        viewBinding.layoutDeviceFilter.deviceFilterDevicetypeTv.setOnClickListener(this)
        viewBinding.layoutDeviceFilter.deviceFilterDevicestatusTv.setOnClickListener(this)
        viewBinding.layoutDeviceFilter.deviceFilterDevicesiteTv.setOnClickListener(this)

        viewBinding.layoutRequireFilter.requireFilterPlaceTv.setOnClickListener(this)
        viewBinding.layoutRequireFilter.requireFilterBrandTv.setOnClickListener(this)
        viewBinding.layoutRequireFilter.requireFilterDevicetypeTv.setOnClickListener(this)
        viewBinding.layoutRequireFilter.requireFilterUsageTv.setOnClickListener(this)
        viewBinding.layoutRequireFilter.requireFilterCuttertypeTv.setOnClickListener(this)

        viewBinding.searchTablayout.addOnTabSelectedListener(this)

        ViewTouchUtil.expandViewTouchDelegate(viewBinding.searchBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.searchFilterTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutDeviceFilter.deviceFilterCloseImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRequireFilter.requireFilterCloseImg)

        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutDeviceFilter.deviceFilterCuttertypeTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutDeviceFilter.deviceFilterBrandTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutDeviceFilter.deviceFilterDevicetypeTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutDeviceFilter.deviceFilterDevicestatusTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutDeviceFilter.deviceFilterDevicesiteTv)

        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRequireFilter.requireFilterPlaceTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRequireFilter.requireFilterBrandTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRequireFilter.requireFilterDevicetypeTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRequireFilter.requireFilterUsageTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRequireFilter.requireFilterCuttertypeTv)

        viewModel.requestOfDeviceType()
        viewModel.requestOfDeviceSite()
    }

    override fun onClick(v: View?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (v!!.id) {
            R.id.search_back_img -> this.finish()
            R.id.search_filter_tv -> {
                when (isFlag) {
                    1 -> viewBinding.layoutDeviceFilter.layoutDeviceFilterFrame.visibility =
                        View.VISIBLE
                    2 -> viewBinding.layoutRequireFilter.layoutRequireFilterFrame.visibility =
                        View.VISIBLE
                }
            }
            R.id.search_device_ll -> {
                isFlag = 1
                viewBinding.searchDeviceTv.setTextColor(resources.getColor(R.color.color_0E64BC))
                viewBinding.searchDeviceView.visibility = View.VISIBLE
                viewBinding.searchRequireTv.setTextColor(resources.getColor(R.color.color_999999))
                viewBinding.searchRequireView.visibility = View.GONE
                checkNull()
                fragmentTransaction.hide(searchRequireFragment!!)
                fragmentTransaction.show(searchDeviceFragment!!)
                fragmentTransaction.commitAllowingStateLoss()
            }
            R.id.search_require_ll -> {
                isFlag = 2
                viewBinding.searchRequireTv.setTextColor(resources.getColor(R.color.color_0E64BC))
                viewBinding.searchRequireView.visibility = View.VISIBLE
                viewBinding.searchDeviceTv.setTextColor(resources.getColor(R.color.color_999999))
                viewBinding.searchDeviceView.visibility = View.GONE
                checkNull()
                fragmentTransaction.hide(searchDeviceFragment!!)
                fragmentTransaction.show(searchRequireFragment!!)
                fragmentTransaction.commitAllowingStateLoss()
            }
            R.id.search_tv -> {
                viewBinding.searchSearchEt.hideKeyboard()
                var key = ""
                when (tabPosition) {
                    0 -> {
                        key = "keyWord"
                    }
                    1 -> {
                        key = "outerDiameter"
                    }
                    2 -> {
                        key = "deviceType"
                    }
                    3 -> {
                        key = "cutterType"
                    }
                    4 -> {
                        key = "applicableStratum"
                    }
                }
                val value = viewBinding.searchSearchEt.text.trim().toString()
                viewModel.requestOfQueryData(1, key, value)
                viewModel.requestOfQueryData(2, key, value)
                viewBinding.searchLoadingview.visibility = View.VISIBLE
            }
            R.id.device_filter_close_img -> {
                viewBinding.layoutDeviceFilter.layoutDeviceFilterFrame.visibility = View.GONE
            }
            R.id.require_filter_close_img -> {
                viewBinding.layoutRequireFilter.layoutRequireFilterFrame.visibility = View.GONE
            }
            R.id.device_filter_reset_tv -> {
                deviceFilterReset()
            }
            R.id.device_filter_sure_tv -> {
                requestOfQueryDataOfDevice()
                requestOfQueryDataOfRequire()
                viewBinding.layoutDeviceFilter.layoutDeviceFilterFrame.visibility = View.GONE
            }
            R.id.require_filter_reset_tv -> {
                requireFilterReset()
            }
            R.id.require_filter_sure_tv -> {
                requestOfQueryDataOfRequire()
                requestOfQueryDataOfDevice()
                viewBinding.layoutRequireFilter.layoutRequireFilterFrame.visibility = View.GONE
            }
            R.id.require_detail_place_tv -> {
                showCityDialog("2")
            }
            R.id.device_filter_devicesite_tv -> {
                showCityDialog("1")
            }
            R.id.require_filter_devicetype_tv -> {
                showTypeDialog("2")
            }
            R.id.device_filter_devicetype_tv -> {
                showTypeDialog("1")
            }
            R.id.require_filter_cuttertype_tv -> {
                showCutterDialog("2")
            }
            R.id.device_filter_cuttertype_tv -> {
                showCutterDialog("1")
            }
            R.id.require_filter_brand_tv -> {
                showBrandDialog("2")
            }
            R.id.device_filter_brand_tv -> {
                showBrandDialog("1")
            }
            R.id.require_filter_usage_tv -> {
                timePickerView.show()
            }
            R.id.device_filter_devicestatus_tv -> {
                showStatusDialog()
            }
        }
    }

    private fun requestOfQueryDataOfDevice() {
        val cutterDiamMin =
            viewBinding.layoutDeviceFilter.deviceFilterCutterMinEt.text.trim().toString()
        val cutterDiamMax =
            viewBinding.layoutDeviceFilter.deviceFilterCutterMaxEt.text.trim().toString()
        val outerDiameterMin =
            viewBinding.layoutDeviceFilter.deviceFilterOutterMinEt.text.trim().toString()
        val outerDiameterMax =
            viewBinding.layoutDeviceFilter.deviceFilterOutterMaxEt.text.trim().toString()
        val drivingTorqueMin =
            viewBinding.layoutDeviceFilter.deviceFilterDriveMinEt.text.trim().toString()
        val drivingTorqueMax =
            viewBinding.layoutDeviceFilter.deviceFilterDriveMaxEt.text.trim().toString()
        val drivingPowerMin =
            viewBinding.layoutDeviceFilter.deviceFilterPowerMinEt.text.trim().toString()
        val drivingPowerMax =
            viewBinding.layoutDeviceFilter.deviceFilterPowerMaxEt.text.trim().toString()
        val propulsiveForceMin =
            viewBinding.layoutDeviceFilter.deviceFilterPorpulMinEt.text.trim().toString()
        val propulsiveForceMax =
            viewBinding.layoutDeviceFilter.deviceFilterPorpulMaxEt.text.trim().toString()
        val openingRateMin =
            viewBinding.layoutDeviceFilter.deviceFilterOpenrateMinEt.text.trim().toString()
        val openingRateMax =
            viewBinding.layoutDeviceFilter.deviceFilterOpenrateMaxEt.text.trim().toString()
        val turningRadiusMin =
            viewBinding.layoutDeviceFilter.deviceFilterRadiusMinEt.text.trim().toString()
        val turningRadiusMax =
            viewBinding.layoutDeviceFilter.deviceFilterRadiusMaxEt.text.trim().toString()
        val beamNumMin =
            viewBinding.layoutDeviceFilter.deviceFilterBeamnumMinEt.text.trim().toString()
        val beamNumMax =
            viewBinding.layoutDeviceFilter.deviceFilterBeamnumMaxEt.text.trim().toString()
        viewModel.requestOfQueryDataOfDevice(
            deviceBrand,
            deviceType,
            cutterType,
            drivingPosition,
            deviceStatus,
            cutterDiamMin,
            cutterDiamMax,
            outerDiameterMin,
            outerDiameterMax,
            drivingTorqueMin,
            drivingTorqueMax,
            drivingPowerMin,
            drivingPowerMax,
            propulsiveForceMin,
            propulsiveForceMax,
            openingRateMin,
            openingRateMax,
            turningRadiusMin,
            turningRadiusMax,
            beamNumMin,
            beamNumMax
        )
        viewBinding.searchLoadingview.visibility = View.VISIBLE
    }

    private fun requestOfQueryDataOfRequire() {
        val cutterDiamMin =
            viewBinding.layoutRequireFilter.requireFilterCutterMinEt.text.trim().toString()
        val cutterDiamMax =
            viewBinding.layoutRequireFilter.requireFilterCutterMaxEt.text.trim().toString()
        val outerDiameterMin =
            viewBinding.layoutRequireFilter.requireFilterOutterMinEt.text.trim().toString()
        val outerDiameterMax =
            viewBinding.layoutRequireFilter.requireFilterOutterMaxEt.text.trim().toString()
        val drivingTorqueMin =
            viewBinding.layoutRequireFilter.requireFilterDriveMinEt.text.trim().toString()
        val drivingTorqueMax =
            viewBinding.layoutRequireFilter.requireFilterDriveMaxEt.text.trim().toString()
        val projectLengthMin =
            viewBinding.layoutRequireFilter.requireFilterLengthMinEt.text.trim().toString()
        val projectLengthMax =
            viewBinding.layoutRequireFilter.requireFilterLengthMaxEt.text.trim().toString()
        val propulsiveForceMin =
            viewBinding.layoutRequireFilter.requireFilterPorpulMinEt.text.trim().toString()
        val propulsiveForceMax =
            viewBinding.layoutRequireFilter.requireFilterPorpulMaxEt.text.trim().toString()
        val openingRateMin =
            viewBinding.layoutRequireFilter.requireFilterOpenrateMinEt.text.trim().toString()
        val openingRateMax =
            viewBinding.layoutRequireFilter.requireFilterOpenrateMaxEt.text.trim().toString()
        val turningRadiusMin =
            viewBinding.layoutRequireFilter.requireFilterRadiusMinEt.text.trim().toString()
        val turningRadiusMax =
            viewBinding.layoutRequireFilter.requireFilterRadiusMaxEt.text.trim().toString()
        val demandNumMin =
            viewBinding.layoutRequireFilter.requireFilterCountMinEt.text.trim().toString()
        val demandNumMax =
            viewBinding.layoutRequireFilter.requireFilterCountMaxEt.text.trim().toString()
        val geologicalInfo =
            viewBinding.layoutRequireFilter.requireFilterGeologyEt.text.trim().toString()

        viewModel.requestOfQueryDataOfRequire(
            deviceBrand,
            deviceType,
            cutterType,
            drivingPosition,
            geologicalInfo,
            cutterDiamMin,
            cutterDiamMax,
            outerDiameterMin,
            outerDiameterMax,
            drivingTorqueMin,
            drivingTorqueMax,
            leasingdate,
            projectLengthMin,
            projectLengthMax,
            propulsiveForceMin,
            propulsiveForceMax,
            openingRateMin,
            openingRateMax,
            turningRadiusMin,
            turningRadiusMax,
            demandNumMin,
            demandNumMax
        )
        viewBinding.searchLoadingview.visibility = View.VISIBLE
    }

    private fun initFragment() {
        searchDeviceFragment = SearchDeviceFragment.newInstance()
        searchRequireFragment = SearchRequireFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.search_frame, searchDeviceFragment!!)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction()
            .add(R.id.search_frame, searchRequireFragment!!)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(searchRequireFragment!!)
            .commitAllowingStateLoss()
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

    private fun checkNull() {
        if (searchDeviceFragment == null) {
            searchDeviceFragment = SearchDeviceFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.search_frame, searchDeviceFragment!!)
                .commitAllowingStateLoss()
        }
        if (searchRequireFragment == null) {
            searchRequireFragment = SearchRequireFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.search_frame, searchRequireFragment!!)
                .commitAllowingStateLoss()
        }
    }

    private fun deviceFilterReset() {
        viewBinding.layoutDeviceFilter.deviceFilterCutterMinEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterCutterMaxEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterOutterMinEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterOutterMaxEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterDriveMinEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterDriveMaxEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterPowerMinEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterPowerMaxEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterPorpulMinEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterPorpulMaxEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterOpenrateMinEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterOpenrateMaxEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterRadiusMinEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterRadiusMaxEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterBeamnumMinEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterBeamnumMaxEt.setText("")
        viewBinding.layoutDeviceFilter.deviceFilterCuttertypeTv.text = "选择"
        viewBinding.layoutDeviceFilter.deviceFilterBrandTv.text = "选择"
        viewBinding.layoutDeviceFilter.deviceFilterDevicetypeTv.text = "选择"
        viewBinding.layoutDeviceFilter.deviceFilterDevicestatusTv.text = "选择"
        viewBinding.layoutDeviceFilter.deviceFilterDevicesiteTv.text = "选择"
        viewBinding.layoutDeviceFilter.deviceFilterCuttertypeTv.text = "选择"
    }

    private fun requireFilterReset() {
        viewBinding.layoutRequireFilter.requireFilterCutterMinEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterCutterMaxEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterOutterMinEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterOutterMaxEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterDriveMinEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterDriveMaxEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterLengthMinEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterLengthMaxEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterPorpulMinEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterPorpulMaxEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterOpenrateMinEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterOpenrateMaxEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterRadiusMinEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterRadiusMaxEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterCountMinEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterCountMaxEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterGeologyEt.setText("")
        viewBinding.layoutRequireFilter.requireFilterCuttertypeTv.text = "选择"
        viewBinding.layoutRequireFilter.requireFilterBrandTv.text = "选择"
        viewBinding.layoutRequireFilter.requireFilterDevicetypeTv.text = "选择"
        viewBinding.layoutRequireFilter.requireFilterPlaceTv.text = "选择"
        viewBinding.layoutRequireFilter.requireFilterUsageTv.text = "选择"
        viewBinding.layoutRequireFilter.requireFilterCuttertypeTv.text = "选择"
    }

    private fun showBrandDialog(type: String) {
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
                deviceBrand = brandList[position].name
            }
        })
        sureview.setOnClickListener {
            dialog?.dismiss()
            when (type) {
                "1" -> {
                    viewBinding.layoutDeviceFilter.deviceFilterBrandTv.text =
                        wheelview.getSelectedItemData().toString()
                    viewBinding.layoutDeviceFilter.deviceFilterBrandTv.setTextColor(
                        resources.getColor(
                            R.color.color_262626
                        )
                    )
                }
                "2" -> {
                    viewBinding.layoutRequireFilter.requireFilterBrandTv.text =
                        wheelview.getSelectedItemData().toString()
                    viewBinding.layoutRequireFilter.requireFilterBrandTv.setTextColor(
                        resources.getColor(
                            R.color.color_262626
                        )
                    )
                }
            }
        }
    }

    private fun showTypeDialog(type: String) {
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
                deviceType = typeList[position].name
            }
        })
        sureview.setOnClickListener {
            dialog?.dismiss()
            when (type) {
                "1" -> {
                    viewBinding.layoutDeviceFilter.deviceFilterDevicetypeTv.text =
                        wheelview.getSelectedItemData().toString()
                    viewBinding.layoutDeviceFilter.deviceFilterDevicetypeTv.setTextColor(
                        resources.getColor(
                            R.color.color_262626
                        )
                    )
                }
                "2" -> {
                    viewBinding.layoutRequireFilter.requireFilterDevicetypeTv.text =
                        wheelview.getSelectedItemData().toString()
                    viewBinding.layoutRequireFilter.requireFilterDevicetypeTv.setTextColor(
                        resources.getColor(
                            R.color.color_262626
                        )
                    )
                }
            }
        }
    }

    private fun showCutterDialog(type: String) {
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
                cutterType = cutterList[position].name
            }
        })
        sureview.setOnClickListener {
            dialog?.dismiss()
            when (type) {
                "1" -> {
                    viewBinding.layoutDeviceFilter.deviceFilterCuttertypeTv.text =
                        wheelview.getSelectedItemData().toString()
                    viewBinding.layoutDeviceFilter.deviceFilterCuttertypeTv.setTextColor(
                        resources.getColor(
                            R.color.color_262626
                        )
                    )
                }
                "2" -> {
                    viewBinding.layoutRequireFilter.requireFilterCuttertypeTv.text =
                        wheelview.getSelectedItemData().toString()
                    viewBinding.layoutRequireFilter.requireFilterCuttertypeTv.setTextColor(
                        resources.getColor(
                            R.color.color_262626
                        )
                    )
                }
            }
        }
    }

    private fun showCityDialog(type: String) {
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
            when (type) {
                "1" -> {
                    viewBinding.layoutDeviceFilter.deviceFilterDevicesiteTv.text =
                        provincewheelview.getSelectedItemData()
                            .toString() + citywheelview.getSelectedItemData().toString()
                    viewBinding.layoutDeviceFilter.deviceFilterDevicesiteTv.setTextColor(
                        resources.getColor(
                            R.color.color_262626
                        )
                    )
                }
                "2" -> {
                    viewBinding.layoutRequireFilter.requireFilterPlaceTv.text =
                        provincewheelview.getSelectedItemData()
                            .toString() + citywheelview.getSelectedItemData().toString()
                    viewBinding.layoutRequireFilter.requireFilterPlaceTv.setTextColor(
                        resources.getColor(
                            R.color.color_262626
                        )
                    )
                }
            }
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
            when (wheelview.getSelectedItemData().toString()) {
                "维修" -> deviceStatus = "1"
                "使用" -> deviceStatus = "2"
                "存放" -> deviceStatus = "3"
            }
            viewBinding.layoutDeviceFilter.deviceFilterDevicestatusTv.text =
                wheelview.getSelectedItemData().toString()
            viewBinding.layoutDeviceFilter.deviceFilterDevicestatusTv.setTextColor(
                resources.getColor(
                    R.color.color_262626
                )
            )
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
            viewBinding.layoutRequireFilter.requireFilterUsageTv.text = getTime(date)
            viewBinding.layoutRequireFilter.requireFilterUsageTv.setTextColor(resources.getColor(R.color.color_262626))
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

    override fun onTabSelected(tab: TabLayout.Tab) {
        tabPosition = tab.position
        when (tab.position) {
            0 -> {
                viewBinding.searchSearchEt.hint = "请输入搜索关键词"
            }
            1 -> {
                viewBinding.searchSearchEt.hint = "请输入适用管片"
            }
            2 -> {
                viewBinding.searchSearchEt.hint = "请输入设备类型"
            }
            3 -> {
                viewBinding.searchSearchEt.hint = "请输入刀盘类型"
            }
            4 -> {
                viewBinding.searchSearchEt.hint = "请输入适用地层"
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}