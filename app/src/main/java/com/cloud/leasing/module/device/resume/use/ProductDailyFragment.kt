package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentProductDailyBinding


class ProductDailyFragment :
    BaseFragment<FragmentProductDailyBinding>(FragmentProductDailyBinding::inflate) {

    private val viewModel: ProductDailyViewModel by viewModels()

    override fun getPageName() = PageName.PRODUCT_DAILY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = ProductDailyFragment()
    }
}