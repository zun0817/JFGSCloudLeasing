package com.cloud.leasing.module.device.resume.store

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.adapter.ManageDataAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.ManageDataBean
import com.cloud.leasing.callback.OnChildItemClickListener
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentStoreDataBinding
import com.cloud.leasing.module.device.resume.use.ManageDataFileActivity
import com.cloud.leasing.util.toast

class StoreDataFragment :
    BaseFragment<FragmentStoreDataBinding>(FragmentStoreDataBinding::inflate),
    OnChildItemClickListener {

    private var resumeId = 0

    private var list = arrayListOf<ManageDataBean>()

    private lateinit var manageDataAdapter: ManageDataAdapter

    private val viewModel: StoreDataViewModel by viewModels()

    override fun getPageName() = PageName.STORE_DATA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView() {
        resumeId = requireArguments().getInt("resumeId")
        viewModel.requestOfManageData(resumeId)
        manageDataAdapter = ManageDataAdapter(requireActivity(), list)
        viewBinding.storeDataRecycleview.setAdapter(manageDataAdapter)
        viewBinding.storeDataRecycleview.setGroupIndicator(null)
        manageDataAdapter.setOnChildItemClickListener(this)
    }

    private fun viewModelObserve() {
        viewModel.apply {
            manageDataLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess {
                    list = it as ArrayList<ManageDataBean>
                    if (it.size > 0) {
                        manageDataAdapter.refreshData(it)
                        viewBinding.storeDataRecycleview.expandGroup(0)
                    }
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

    override fun onChildClick(groupPosition: Int, childPosition: Int) {
        ManageDataFileActivity.startActivity(
            requireActivity(),
            list[groupPosition].childrenList[childPosition].resumeId,
            list[groupPosition].childrenList[childPosition].parentId,
            list[groupPosition].childrenList[childPosition].id,
            list[groupPosition].childrenList[childPosition].manageName
        )
    }

    companion object {
        fun newInstance() = StoreDataFragment()
    }
}