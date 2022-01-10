package com.cloud.leasing.module.home.search

import android.os.Bundle
import android.view.View
import com.cloud.leasing.adapter.SearchDeviceAdapter
import com.cloud.leasing.adapter.SearchRequireAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.Search
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentSearchRequireBinding


class SearchRequireFragment :
    BaseFragment<FragmentSearchRequireBinding>(FragmentSearchRequireBinding::inflate) {

    private lateinit var searchRequireAdapter: SearchRequireAdapter

    override fun getPageName() = PageName.SEARCH_REQUIRE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        searchRequireAdapter = SearchRequireAdapter(requireActivity(), mutableListOf())
        viewBinding.searchRequireListview.adapter = searchRequireAdapter
    }

    fun refreshData(list: MutableList<Search>) {
        if (list.size > 0) {
            searchRequireAdapter.refreshData(list)
            viewBinding.searchRequireListview.visibility = View.VISIBLE
            viewBinding.errorView.visibility = View.GONE
        } else {
            searchRequireAdapter.refreshData(list)
            viewBinding.searchRequireListview.visibility = View.GONE
            viewBinding.errorView.visibility = View.VISIBLE
            viewBinding.errorView.showEmpty()
        }
    }

    companion object {
        fun newInstance() = SearchRequireFragment()
    }
}