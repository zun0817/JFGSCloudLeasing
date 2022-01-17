package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.adapter.FaultLedgerAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.FaultRecord
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentFaultLedgerBinding
import com.cloud.leasing.util.toast


class FaultLedgerFragment :
    BaseFragment<FragmentFaultLedgerBinding>(FragmentFaultLedgerBinding::inflate) {

    private var resumeId = 0

    private var list = mutableListOf<FaultRecord>()

    private lateinit var faultLedgerAdapter: FaultLedgerAdapter

    private val viewModel: FaultLedgerViewModel by viewModels()

    override fun getPageName() = PageName.FAULT_LEDGER

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView(){
        resumeId = requireArguments().getInt("resumeId")
        viewModel.requestOfResumeFaultLedger(resumeId)

        faultLedgerAdapter = FaultLedgerAdapter(requireActivity(), list)
        viewBinding.faultLedgerListview.adapter = faultLedgerAdapter
    }

    private fun viewModelObserve() {
        viewModel.apply {
            faultLedgerLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                    viewBinding.faultLedgerErrorview.visibility = View.VISIBLE
                    viewBinding.faultLedgerErrorview.showNetworkError({
                        viewModel.requestOfResumeFaultLedger(resumeId)
                    })
                }.onSuccess {
                    if (it.produceDailyFaultList.records.isNotEmpty()) {
                        viewBinding.faultLedgerErrorview.visibility = View.GONE
                        faultLedgerAdapter.refreshData(it.produceDailyFaultList.records)
                    } else {
                        viewBinding.faultLedgerErrorview.visibility = View.VISIBLE
                        viewBinding.faultLedgerErrorview.showEmpty()
                    }
                }
            })
        }
    }

    companion object {

        fun newInstance() = FaultLedgerFragment()
    }
}