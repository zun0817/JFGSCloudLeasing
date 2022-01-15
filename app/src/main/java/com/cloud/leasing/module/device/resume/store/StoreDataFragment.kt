package com.cloud.leasing.module.device.resume.store

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentStoreDataBinding

class StoreDataFragment :
    BaseFragment<FragmentStoreDataBinding>(FragmentStoreDataBinding::inflate) {

    private val viewModel: StoreDataViewModel by viewModels()

    override fun getPageName() = PageName.STORE_DATA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = StoreDataFragment()
    }
}