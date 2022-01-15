package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentManageDataBinding

class ManageDataFragment :
    BaseFragment<FragmentManageDataBinding>(FragmentManageDataBinding::inflate) {

    private val viewModel: ManageDataViewModel by viewModels()

    override fun getPageName() = PageName.MANAGE_DATA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance() = ManageDataFragment()
    }
}