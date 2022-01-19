package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.adapter.ProductDailyAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentManageDataBinding
import com.cloud.leasing.util.toast

class ManageDataFragment :
    BaseFragment<FragmentManageDataBinding>(FragmentManageDataBinding::inflate) {

    private var resumeId = 0

    private val viewModel: ManageDataViewModel by viewModels()

    override fun getPageName() = PageName.MANAGE_DATA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView() {
        resumeId = requireArguments().getInt("resumeId")
        viewModel.requestOfManageData(resumeId)
    }

    private fun viewModelObserve() {
        viewModel.apply {
            manageDataLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess {

                }
            })
            manageFileLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess {

                }
            })
        }
    }

    companion object {
        fun newInstance() = ManageDataFragment()
    }
}