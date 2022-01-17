package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.adapter.MaintenanceAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.Maintenance
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentMaintenanceBinding
import com.cloud.leasing.util.toast


class MaintenanceFragment :
    BaseFragment<FragmentMaintenanceBinding>(FragmentMaintenanceBinding::inflate) {

    private var resumeId = 0

    private var list = mutableListOf<Maintenance>()

    private lateinit var maintenanceAdapter: MaintenanceAdapter

    private val viewModel: MaintenanceViewModel by viewModels()

    override fun getPageName() = PageName.MAINTENANCE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView() {
        resumeId = requireArguments().getInt("resumeId")
        viewModel.requestOfResumeMaintenance(resumeId)

        maintenanceAdapter = MaintenanceAdapter(requireActivity(), list)
        viewBinding.maintenanceListview.adapter = maintenanceAdapter
    }

    private fun viewModelObserve() {
        viewModel.apply {
            maintenanceLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                    viewBinding.maintenanceErrorview.visibility = View.VISIBLE
                    viewBinding.maintenanceErrorview.showNetworkError({
                        viewModel.requestOfResumeMaintenance(resumeId)
                    })
                }.onSuccess {
                    if (it.records.isNotEmpty()) {
                        viewBinding.maintenanceErrorview.visibility = View.GONE
                        maintenanceAdapter.refreshData(it.records)
                    } else {
                        viewBinding.maintenanceErrorview.visibility = View.VISIBLE
                        viewBinding.maintenanceErrorview.showEmpty()
                    }
                }
            })
        }
    }

    companion object {

        fun newInstance() = MaintenanceFragment()
    }
}