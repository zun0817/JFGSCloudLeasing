package com.cloud.leasing.module.device.resume.store

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.adapter.DailyCheckAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.RepairDaily
import com.cloud.leasing.bean.StoreDaily
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentDailyCheckBinding
import com.cloud.leasing.util.toast

class DailyCheckFragment :
    BaseFragment<FragmentDailyCheckBinding>(FragmentDailyCheckBinding::inflate), View.OnClickListener {

    private var resumeId = 0

    private var list = mutableListOf<StoreDaily>()

    private lateinit var dailyCheckAdapter: DailyCheckAdapter

    private val viewModel: DailyCheckViewModel by viewModels()

    override fun getPageName() = PageName.DAILY_CHECK

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView(){
        viewBinding.dailyCheckAddBtn.setOnClickListener(this)
        resumeId = requireArguments().getInt("resumeId")
        dailyCheckAdapter = DailyCheckAdapter(requireActivity(), list)
        viewBinding.dailyCheckListview.adapter = dailyCheckAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestOfResumeStoreDaily(resumeId)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.daily_check_add_btn -> AddCheckDailyActivity.startActivity(requireActivity(), resumeId)
        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            storeDailyLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                    viewBinding.dailyCheckErrorview.visibility = View.VISIBLE
                    viewBinding.dailyCheckErrorview.showNetworkError({
                        viewModel.requestOfResumeStoreDaily(resumeId)
                    })
                }.onSuccess {
                    if (it.records.size > 0) {
                        viewBinding.dailyCheckErrorview.visibility = View.GONE
                        dailyCheckAdapter.refreshData(it.records)
                    } else {
                        viewBinding.dailyCheckErrorview.visibility = View.VISIBLE
                        viewBinding.dailyCheckErrorview.showEmpty()
                    }
                }
            })
        }
    }

    companion object {
        fun newInstance() = DailyCheckFragment()
    }

}