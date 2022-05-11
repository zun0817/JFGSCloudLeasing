package com.cloud.leasing.module.device.resume.repair

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.adapter.FaultDailyAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.RepairDaily
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentFaultDailyBinding
import com.cloud.leasing.util.toast

class FaultDailyFragment :
    BaseFragment<FragmentFaultDailyBinding>(FragmentFaultDailyBinding::inflate), View.OnClickListener {

    private var resumeId = 0

    private var list = mutableListOf<RepairDaily>()

    private lateinit var faultDailyAdapter: FaultDailyAdapter

    private val viewModel: FaultDailyViewModel by viewModels()

    override fun getPageName() = PageName.FAULT_DAILY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView() {
        viewBinding.faultDailyAddBtn.setOnClickListener(this)
        resumeId = requireArguments().getInt("resumeId")

        faultDailyAdapter = FaultDailyAdapter(requireActivity(), list)
        viewBinding.faultDailyListview.adapter = faultDailyAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestOfResumeFaultDaily(resumeId)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.fault_daily_add_btn  -> AddFaultDailyActivity.startActivity(requireActivity(), resumeId)
        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            faultDailyLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                    viewBinding.faultDailyErrorview.visibility = View.VISIBLE
                    viewBinding.faultDailyErrorview.showNetworkError({
                        viewModel.requestOfResumeFaultDaily(resumeId)
                    })
                }.onSuccess {
                    if (it.records.size > 0) {
                        viewBinding.faultDailyErrorview.visibility = View.GONE
                        faultDailyAdapter.refreshData(it.records)
                    } else {
                        viewBinding.faultDailyErrorview.visibility = View.VISIBLE
                        viewBinding.faultDailyErrorview.showEmpty()
                    }
                }
            })
        }
    }

    companion object {
        fun newInstance() = FaultDailyFragment()
    }

}