package com.cloud.leasing.module.mine.follow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.base.list.XRecyclerView
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.bean.FollowDeviceBean
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentEquipmentBinding
import com.cloud.leasing.item.EquipmentItemViewData
import com.cloud.leasing.util.toast

class EquipmentFragment :
    BaseFragment<FragmentEquipmentBinding>(FragmentEquipmentBinding::inflate) {

    companion object {
        fun newInstance() = EquipmentFragment()
    }

    private var data: FollowDeviceBean? = null

    private var list = mutableListOf<EquipmentItemViewData>()

    private val viewModel: EquipmentViewModel by viewModels()

    override fun getPageName() = PageName.EQUIPMENT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView() {
        viewBinding.equipmentRecyclerview.init(
            XRecyclerView.Config()
                .setViewModel(viewModel)
                .setPullUploadMoreEnable(false)
                .setOnItemClickListener(object : XRecyclerView.OnItemClickListener {
                    override fun onItemClick(
                        parent: RecyclerView,
                        view: View,
                        viewData: BaseViewData<*>,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(context, "条目点击: ${viewData.value}", Toast.LENGTH_SHORT)
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
                        data = extra as FollowDeviceBean
                        data?.let {
                            when (it.focusStatus) {
                                0 -> viewModel.requestOfAddFollow(it.id, it.deleted)
                                1 -> viewModel.requestOfUnfollow(it.id, it.deleted)
                            }
                        }
                    }
                })
        )
    }

    private fun viewModelObserve() {
        viewModel.apply {
            deviceLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code != 200) {
                        it.toString().toast(requireActivity())
                    } else {
                        viewModel.firstViewDataLiveData.postValue(emptyList())
                    }
                }.onSuccess { mutableList ->
                    list.takeIf { it.size > 0 }?.apply { clear() }
                    mutableList.forEach {
                        list.add(EquipmentItemViewData(it))
                    }
                    viewModel.firstViewDataLiveData.postValue(list)
                }
            })
            followLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "关注成功".toast(requireActivity())
                        data?.let {
                            list.forEach { viewData ->
                                if (viewData.value.id == it.id) {
                                    viewData.value.focusStatus = 1
                                    viewData.value.deleted = "0"
                                }
                            }
                            viewBinding.equipmentRecyclerview.setViewData(list)
                        }
                    } else {
                        it.toString().toast(requireActivity())
                    }
                }.onSuccess {
                    "关注成功".toast(requireActivity())
                    data?.let {
                        list.forEach { viewData ->
                            if (viewData.value.id == it.id) {
                                viewData.value.focusStatus = 1
                                viewData.value.deleted = "0"
                            }
                        }
                        viewBinding.equipmentRecyclerview.setViewData(list)
                    }
                }
            })
            unfollowLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "取消关注".toast(requireActivity())
                        data?.let {
                            list.forEach { viewData ->
                                if (viewData.value.id == it.id) {
                                    viewData.value.focusStatus = 0
                                    viewData.value.deleted = "1"
                                }
                            }
                            viewBinding.equipmentRecyclerview.setViewData(list)
                        }
                    } else {
                        it.toString().toast(requireActivity())
                    }
                }.onSuccess {
                    "取消关注".toast(requireActivity())
                    data?.let {
                        list.forEach { viewData ->
                            if (viewData.value.id == it.id) {
                                viewData.value.focusStatus = 0
                                viewData.value.deleted = "1"
                            }
                        }
                        viewBinding.equipmentRecyclerview.setViewData(list)
                    }
                }
            })
        }
    }

}