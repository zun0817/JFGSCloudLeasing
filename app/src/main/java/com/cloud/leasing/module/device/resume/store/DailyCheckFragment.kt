package com.cloud.leasing.module.device.resume.store

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentDailyCheckBinding

class DailyCheckFragment :
    BaseFragment<FragmentDailyCheckBinding>(FragmentDailyCheckBinding::inflate) {

    private val viewModel: DailyCheckViewModel by viewModels()

    override fun getPageName() = PageName.DAILY_CHECK

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = DailyCheckFragment()
    }
}