package com.cloud.leasing.module.home.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.adapter.SearchDeviceAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.Search
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.callback.OnItemChildrenListener
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentSearchDeviceBinding
import com.cloud.leasing.util.toast


class SearchDeviceFragment :
    BaseFragment<FragmentSearchDeviceBinding>(FragmentSearchDeviceBinding::inflate) {

    private var select = 0

    private var datas: MutableList<Search>? = null

    private lateinit var searchDeviceAdapter: SearchDeviceAdapter

    private val viewModel: SearchDeviceViewModel by viewModels()

    override fun getPageName() = PageName.SEARCH_DEVICE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView() {
        searchDeviceAdapter = SearchDeviceAdapter(requireActivity(), mutableListOf())
        viewBinding.searchDeviceListview.adapter = searchDeviceAdapter
        searchDeviceAdapter.onItemChildrenListener = onItemChildrenListener
    }

    private fun viewModelObserve() {
        viewModel.apply {
            followLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "关注成功".toast(requireActivity())
                        datas?.forEachIndexed { index, search ->
                            if (select == index){
                                search.focusStatus = "1"
                            }
                        }
                        datas?.apply { searchDeviceAdapter.refreshData(this) }
                    } else {
                        it.toString().toast(requireActivity())
                    }
                }.onSuccess {
                    "关注成功".toast(requireActivity())
                    datas?.forEachIndexed { index, search ->
                        if (select == index){
                            search.focusStatus = "1"
                        }
                    }
                    datas?.apply { searchDeviceAdapter.refreshData(this) }
                }
            })
            unfollowLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "取消关注".toast(requireActivity())
                        datas?.forEachIndexed { index, search ->
                            if (select == index){
                                search.focusStatus = "0"
                            }
                        }
                        datas?.apply { searchDeviceAdapter.refreshData(this) }
                    } else {
                        it.toString().toast(requireActivity())
                    }
                }.onSuccess {
                    "取消关注".toast(requireActivity())
                    datas?.forEachIndexed { index, search ->
                        if (select == index){
                            search.focusStatus = "0"
                        }
                    }
                    datas?.apply { searchDeviceAdapter.refreshData(this) }
                }
            })
        }
    }

    fun refreshData(list: MutableList<Search>) {
        datas = list
        searchDeviceAdapter.refreshData(list)
    }

    private val onItemChildrenListener =
        OnItemChildrenListener { position, data ->
            select = position
            val bean = data as Search
            when (bean.focusStatus) {
                "0" -> viewModel.requestOfAddFollow(bean.id, "1")
                "1" -> viewModel.requestOfUnfollow(bean.id, "0")
            }
        }

    companion object {
        fun newInstance() = SearchDeviceFragment()
    }
}