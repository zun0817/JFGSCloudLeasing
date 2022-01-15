package com.cloud.leasing.module.device.resume.repair

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentFaultDailyBinding

class FaultDailyFragment :
    BaseFragment<FragmentFaultDailyBinding>(FragmentFaultDailyBinding::inflate) {

    private val viewModel: FaultDailyViewModel by viewModels()

    override fun getPageName() = PageName.FAULT_DAILY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun newInstance() = FaultDailyFragment()
    }
}