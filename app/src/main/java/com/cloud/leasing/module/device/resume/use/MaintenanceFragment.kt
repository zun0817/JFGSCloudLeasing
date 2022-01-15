package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentMaintenanceBinding


class MaintenanceFragment :
    BaseFragment<FragmentMaintenanceBinding>(FragmentMaintenanceBinding::inflate) {

    private val viewModel: MaintenanceViewModel by viewModels()

    override fun getPageName() = PageName.MAINTENANCE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun newInstance() = MaintenanceFragment()
    }
}