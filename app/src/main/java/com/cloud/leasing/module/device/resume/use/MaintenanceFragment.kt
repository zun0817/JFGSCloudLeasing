package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.adapter.MaintenanceAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.Maintenance
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentMaintenanceBinding
import com.cloud.leasing.util.toast


class MaintenanceFragment :
    BaseFragment<FragmentMaintenanceBinding>(FragmentMaintenanceBinding::inflate), View.OnClickListener {

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
        maintenanceAdapter = MaintenanceAdapter(requireActivity(), list)
        viewBinding.maintenanceListview.adapter = maintenanceAdapter
        viewBinding.maintenanceFl.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestOfResumeMaintenance(resumeId)
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

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.maintenance_fl -> AddMaintenanceActivity.startActivity(requireActivity(), resumeId)
        }
    }

    companion object {
        fun newInstance() = MaintenanceFragment()
    }
}