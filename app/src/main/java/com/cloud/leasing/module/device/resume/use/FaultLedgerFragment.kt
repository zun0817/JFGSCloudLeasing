package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentFaultLedgerBinding


class FaultLedgerFragment :
    BaseFragment<FragmentFaultLedgerBinding>(FragmentFaultLedgerBinding::inflate) {

    private val viewModel: FaultLedgerViewModel by viewModels()

    override fun getPageName() = PageName.FAULT_LEDGER

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun newInstance() = FaultLedgerFragment()
    }
}