package com.cloud.leasing.module.device

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.base.list.XRecyclerView
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.bean.*
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentDeviceBinding
import com.cloud.leasing.item.DeviceItemViewData
import com.cloud.leasing.module.device.resume.DeviceResumeActivity
import com.cloud.leasing.module.home.detail.DeviceDetailActivity
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar


class DeviceFragment : BaseFragment<FragmentDeviceBinding>(FragmentDeviceBinding::inflate) {

    private val viewModel: DeviceViewModel by viewModels()

    private var list = mutableListOf<DeviceItemViewData>()

    private lateinit var typeList: MutableList<DeviceType>

    private lateinit var brandList: MutableList<DeviceBrand>

    private lateinit var cutterList: MutableList<CutterType>

    private lateinit var datas: MutableList<Record>

    @PageName
    override fun getPageName() = PageName.DEVICE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        viewModel.requestOfDeviceType()
        viewBinding.deviceRecyclerview.init(
            XRecyclerView.Config()
                .setViewModel(viewModel)
                .setPullRefreshEnable(false)
                .setPullUploadMoreEnable(false)
                .setOnItemClickListener(object : XRecyclerView.OnItemClickListener {
                    override fun onItemClick(
                        parent: RecyclerView,
                        view: View,
                        viewData: BaseViewData<*>,
                        position: Int,
                        id: Long
                    ) {
                        val bean = viewData.value as Record
                        DeviceDetailActivity.startActivity(requireActivity(), bean.id)
                    }
                })
                .setOnItemChildViewClickListener(object :
                    XRecyclerView.OnItemChildViewClickListener {
                    override fun onItemChildViewClick(
                        parent: RecyclerView,
                        view: View,
                        viewData: BaseViewData<*>,
                        position: Int,
                        id: Long,
                        extra: Any?
                    ) {
                        val deviceId = (viewData.value as Record).id
                        DeviceResumeActivity.startActivity(requireActivity(), deviceId)
                    }
                })
        )
    }

    private fun viewModelObserve() {
        viewModel.apply {
            deviceLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                    viewModel.firstViewDataLiveData.postValue(LoadError)
                }.onSuccess { formBean ->
                    list.takeIf { it.size > 0 }?.apply { clear() }
                    datas = formBean.records
                    if (datas.isNotEmpty()) {
                        datas.forEach { record ->
                            typeList.forEach {
                                if (record.deviceType == it.value) {
                                    record.deviceTypeName = it.name
                                }
                            }
                            cutterList.forEach {
                                if (record.cutterType == it.value) {
                                    record.cutterTypeName = it.name
                                }
                            }
                            list.add(DeviceItemViewData(record))
                        }
                        viewModel.firstViewDataLiveData.postValue(list)
                    } else {
                        viewModel.firstViewDataLiveData.postValue(emptyList())
                    }
                }
            })
            deviceTypeLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess {
                    typeList = it.deviceType
                    brandList = it.deviceBrand
                    cutterList = it.cutterType
                    val typejson = JSON.toJSON(typeList).toString()
                    val brandjson = JSON.toJSON(brandList).toString()
                    val cutterjson = JSON.toJSON(cutterList).toString()
                    XKeyValue.putString(Constant.DEVICE_TYPE, typejson)
                    XKeyValue.putString(Constant.DEVICE_BRAND, brandjson)
                    XKeyValue.putString(Constant.DEVICE_CUTTER, cutterjson)
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initSystemBar()
    }
}