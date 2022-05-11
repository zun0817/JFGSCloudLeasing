package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.adapter.ProductDailyAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.RecordBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentProductDailyBinding
import com.cloud.leasing.util.toast


class ProductDailyFragment :
    BaseFragment<FragmentProductDailyBinding>(FragmentProductDailyBinding::inflate), View.OnClickListener {

    private var resumeId = 0

    private var list = mutableListOf<RecordBean>()

    private lateinit var productDailyAdapter: ProductDailyAdapter

    private val viewModel: ProductDailyViewModel by viewModels()

    override fun getPageName() = PageName.PRODUCT_DAILY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView() {
        resumeId = requireArguments().getInt("resumeId")
        viewBinding.productDailyAddBtn.setOnClickListener(this)
        productDailyAdapter = ProductDailyAdapter(requireActivity(), list)
        viewBinding.productDailyListview.adapter = productDailyAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestOfResumeProductDaily(resumeId)
    }

    private fun viewModelObserve() {
        viewModel.apply {
            productDailyLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                    viewBinding.productDailyErrorview.visibility = View.VISIBLE
                    viewBinding.productDailyErrorview.showNetworkError({
                        viewModel.requestOfResumeProductDaily(resumeId)
                    })
                }.onSuccess {
                    if (it.records.size > 0) {
                        viewBinding.productDailyErrorview.visibility = View.GONE
                        productDailyAdapter.refreshData(it.records)
                    } else {
                        viewBinding.productDailyErrorview.visibility = View.VISIBLE
                        viewBinding.productDailyErrorview.showEmpty()
                    }
                }
            })
        }
    }

    companion object {
        fun newInstance() = ProductDailyFragment()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.product_daily_add_btn -> AddProductDailyActivity.startActivity(requireActivity(), resumeId)
        }
    }
}