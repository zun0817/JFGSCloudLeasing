package com.cloud.leasing.module.mine.follow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.base.list.XRecyclerView
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.bean.FollowRequireBean
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentRequireBinding
import com.cloud.leasing.item.RequireItemViewData
import com.cloud.leasing.module.home.detail.RequireDetailActivity
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.toast


class RequireFragment : BaseFragment<FragmentRequireBinding>(FragmentRequireBinding::inflate) {

    companion object {
        fun newInstance() = RequireFragment()
    }

    private var userAuth: String = "0"

    private var list = mutableListOf<RequireItemViewData>()

    private val viewModel: RequireViewModel by viewModels()

    override fun getPageName() = PageName.REQUIRE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun initView() {
        userAuth = XKeyValue.getString(Constant.USER_AUTH, "0")
        viewBinding.requireRecyclerview.init(
            XRecyclerView.Config()
                .setViewModel(viewModel)
                .setPullUploadMoreEnable(false)
                .setOnItemChildViewClickListener(object :
                    XRecyclerView.OnItemChildViewClickListener {
                    override fun onItemChildViewClick(
                        parent: RecyclerView,
                        view: View,
                        viewData: BaseViewData<*>,
                        position: Int,
                        id: Long,
                        extra: Any?
                    ) {
                        val bean = viewData.value as FollowRequireBean
                        if (userAuth == "0" || userAuth == "2") {
                            "企业认证未通过，暂无权限查看详情".toast(requireActivity())
                            return
                        }
                        RequireDetailActivity.startActivity(requireActivity(), bean.id)
                    }
                })
        )
    }

    private fun viewModelObserve() {
        viewModel.apply {
            requireLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code != 200) {
                        it.toString().toast(requireActivity())
                    } else {
                        viewModel.firstViewDataLiveData.postValue(emptyList())
                    }
                }.onSuccess {mutableList ->
                    list.takeIf { it.size > 0 }?.apply { clear() }
                    mutableList.forEach {
                        list.add(RequireItemViewData(it))
                    }
                    viewModel.firstViewDataLiveData.postValue(list)
                }
            })
        }
    }

}