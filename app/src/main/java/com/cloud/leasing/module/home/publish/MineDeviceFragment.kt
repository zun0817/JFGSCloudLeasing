package com.cloud.leasing.module.home.publish

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.cloud.leasing.JFGSApplication
import com.cloud.leasing.R
import com.cloud.leasing.adapter.MineDeviceAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.MineDevice
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentMineDeviceBinding
import com.cloud.leasing.module.home.detail.DeviceDetailActivity
import com.cloud.leasing.util.toast


class MineDeviceFragment :
    BaseFragment<FragmentMineDeviceBinding>(FragmentMineDeviceBinding::inflate),
    View.OnClickListener, AdapterView.OnItemClickListener {

    companion object {
        fun newInstance() = MineDeviceFragment()
    }

    private var isAllSelect = false

    private var isAllVisible = false

    private lateinit var mineDeviceAdapter: MineDeviceAdapter

    private lateinit var list: MutableList<MineDevice>

    private val viewModel: MineDeviceViewModel by viewModels()

    override fun getPageName() = PageName.MINEDEVICE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun viewModelObserve() {
        viewModel.apply {
            mineDeviceLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess { mineDeviceBean ->
                    if (mineDeviceBean.pageInfo.list.isNotEmpty()) {
                        list = mineDeviceBean.pageInfo.list
                        mineDeviceAdapter =
                            MineDeviceAdapter(requireActivity(), mineDeviceBean.pageInfo.list)
                        viewBinding.mineDeviceListview.adapter = mineDeviceAdapter
                        viewBinding.mineDeviceListview.visibility = View.VISIBLE
                        viewBinding.errorView.visibility = View.GONE
                    } else {
                        viewBinding.mineDeviceListview.visibility = View.GONE
                        viewBinding.errorView.visibility = View.VISIBLE
                        viewBinding.errorView.showEmpty()
                    }
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mine_device_edit_tv -> {
                viewBinding.mineDeviceEditTv.visibility = View.GONE
                viewBinding.mineDeviceDeleteCl.visibility = View.VISIBLE
                isAllVisible = true
            }
            R.id.mine_device_selectall_tv -> {
                if (!isAllSelect) {
                    val drawable = resources.getDrawable(R.mipmap.icon_select_yes)
                    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                    viewBinding.mineDeviceSelectallTv.setCompoundDrawables(
                        drawable,
                        null,
                        null,
                        null
                    )
                } else {
                    val drawable = resources.getDrawable(R.mipmap.icon_select_no)
                    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                    viewBinding.mineDeviceSelectallTv.setCompoundDrawables(
                        drawable,
                        null,
                        null,
                        null
                    )
                }
                isAllSelect = !isAllSelect
            }
            R.id.mine_device_deleteall_tv -> {

            }
            R.id.mine_device_cancel_tv -> {
                viewBinding.mineDeviceEditTv.visibility = View.VISIBLE
                viewBinding.mineDeviceDeleteCl.visibility = View.GONE
                isAllVisible = false
                isAllSelect = false
            }
        }
    }

    private fun initView() {
        viewBinding.mineDeviceEditTv.setOnClickListener(this)
        viewBinding.mineDeviceCancelTv.setOnClickListener(this)
        viewBinding.mineDeviceDeleteallTv.setOnClickListener(this)
        viewBinding.mineDeviceSelectallTv.setOnClickListener(this)
        viewBinding.mineDeviceListview.onItemClickListener = this
        viewModel.requestOfMineDevice()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            viewBinding.mineDeviceEditTv.visibility = View.VISIBLE
            viewBinding.mineDeviceDeleteCl.visibility = View.GONE
            isAllVisible = false
            isAllSelect = false
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        DeviceDetailActivity.startActivity(requireActivity(), list[position].id)
    }

}