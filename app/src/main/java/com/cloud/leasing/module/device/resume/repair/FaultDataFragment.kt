package com.cloud.leasing.module.device.resume.repair

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentFaultDataBinding


class FaultDataFragment :
    BaseFragment<FragmentFaultDataBinding>(FragmentFaultDataBinding::inflate) {

    private val viewModel: FaultDataViewModel by viewModels()

    override fun getPageName() = PageName.FAULT_DATA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = FaultDataFragment()
    }
}