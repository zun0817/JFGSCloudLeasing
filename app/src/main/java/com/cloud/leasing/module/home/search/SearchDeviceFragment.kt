package com.cloud.leasing.module.home.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.cloud.leasing.adapter.SearchDeviceAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.Search
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.callback.OnItemChildrenListener
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentSearchDeviceBinding
import com.cloud.leasing.module.home.detail.DeviceDetailActivity
import com.cloud.leasing.util.toast


class SearchDeviceFragment :
    BaseFragment<FragmentSearchDeviceBinding>(FragmentSearchDeviceBinding::inflate),
    AdapterView.OnItemClickListener {

    private var select = 0

    private lateinit var datas: MutableList<Search>

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
        viewBinding.searchDeviceListview.onItemClickListener = this
    }

    private fun viewModelObserve() {
        viewModel.apply {
            followLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "关注成功".toast(requireActivity())
                        datas.forEachIndexed { index, search ->
                            if (select == index) {
                                search.focusStatus = "1"
                            }
                        }
                        searchDeviceAdapter.refreshData(datas)
                    } else {
                        it.toString().toast(requireActivity())
                    }
                }.onSuccess {
                    "关注成功".toast(requireActivity())
                    datas.forEachIndexed { index, search ->
                        if (select == index) {
                            search.focusStatus = "1"
                        }
                    }
                    searchDeviceAdapter.refreshData(datas)
                }
            })
            unfollowLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "取消关注".toast(requireActivity())
                        datas.forEachIndexed { index, search ->
                            if (select == index) {
                                search.focusStatus = "0"
                            }
                        }
                        searchDeviceAdapter.refreshData(datas)
                    } else {
                        it.toString().toast(requireActivity())
                    }
                }.onSuccess {
                    "取消关注".toast(requireActivity())
                    datas.forEachIndexed { index, search ->
                        if (select == index) {
                            search.focusStatus = "0"
                        }
                    }
                    searchDeviceAdapter.refreshData(datas)
                }
            })
        }
    }

    fun refreshData(list: MutableList<Search>) {
        datas = list
        if (datas.size > 0) {
            searchDeviceAdapter.refreshData(list)
            viewBinding.searchDeviceListview.visibility = View.VISIBLE
            viewBinding.errorView.visibility = View.GONE
        } else {
            searchDeviceAdapter.refreshData(list)
            viewBinding.searchDeviceListview.visibility = View.GONE
            viewBinding.errorView.visibility = View.VISIBLE
            viewBinding.errorView.showEmpty()
        }
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

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        DeviceDetailActivity.startActivity(requireActivity(), datas[position].id)
    }
}