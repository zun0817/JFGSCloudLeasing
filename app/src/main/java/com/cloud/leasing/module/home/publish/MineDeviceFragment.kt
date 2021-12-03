package com.cloud.leasing.module.home.publish

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.base.list.XRecyclerView
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentMineDeviceBinding


class MineDeviceFragment :
    BaseFragment<FragmentMineDeviceBinding>(FragmentMineDeviceBinding::inflate),
    View.OnClickListener {

    companion object {
        fun newInstance() = MineDeviceFragment()
    }

    private var isAllSelect = true

    private val viewModel: MineDeviceViewModel by viewModels()

    override fun getPageName() = PageName.MINEDEVICE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mine_device_edit_tv -> {
                viewBinding.mineDeviceEditTv.visibility = View.GONE
                viewBinding.mineDeviceDeleteCl.visibility = View.VISIBLE
            }
            R.id.mine_device_selectall_tv -> {
                if (isAllSelect) {
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
            }
        }
    }

    private fun initView() {
        viewBinding.mineDeviceEditTv.setOnClickListener(this)
        viewBinding.mineDeviceCancelTv.setOnClickListener(this)
        viewBinding.mineDeviceDeleteallTv.setOnClickListener(this)
        viewBinding.mineDeviceSelectallTv.setOnClickListener(this)
        viewBinding.mineDeviceRecyclerview.init(
            XRecyclerView.Config()
                .setViewModel(viewModel)
                .setOnItemClickListener(object : XRecyclerView.OnItemClickListener {
                    override fun onItemClick(
                        parent: RecyclerView,
                        view: View,
                        viewData: BaseViewData<*>,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(
                            requireActivity(),
                            "条目点击: ${viewData.value}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
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
                        if (extra is String) {
                            Toast.makeText(
                                requireActivity(),
                                "条目子View点击: $extra",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        )
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e("***device", hidden.toString())
        if (hidden) {
            viewBinding.mineDeviceEditTv.visibility = View.VISIBLE
            viewBinding.mineDeviceDeleteCl.visibility = View.GONE
        }
    }

}