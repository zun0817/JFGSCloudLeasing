package com.cloud.leasing.module.home.publish

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.adapter.MineRequireAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.MineRequire
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentMinePublishBinding
import com.cloud.leasing.module.home.detail.RequireDetailActivity
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.toast

class MinePublishFragment :
    BaseFragment<FragmentMinePublishBinding>(FragmentMinePublishBinding::inflate),
    View.OnClickListener, AdapterView.OnItemClickListener {

    companion object {
        fun newInstance() = MinePublishFragment()
    }

    private var userAuth: String = "0"

    private var isAllSelect = false

    private var isAllVisible = false

    private lateinit var list: MutableList<MineRequire>

    private lateinit var mineRequireAdapter: MineRequireAdapter

    private val viewModel: MinePublishViewModel by viewModels()

    override fun getPageName() = PageName.MINEPUBLISH

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObserve()
    }

    private fun viewModelObserve() {
        userAuth = XKeyValue.getString(Constant.USER_AUTH, "0")
        viewModel.apply {
            mineRequireLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess { mineRequireBean ->
                    if (mineRequireBean.list.isNotEmpty()) {
                        list = mineRequireBean.list
                        mineRequireAdapter =
                            MineRequireAdapter(requireActivity(), mineRequireBean.list)
                        viewBinding.minePublishListview.adapter = mineRequireAdapter
                        viewBinding.minePublishListview.visibility = View.VISIBLE
                        viewBinding.errorView.visibility = View.GONE
                    } else {
                        viewBinding.minePublishListview.visibility = View.GONE
                        viewBinding.errorView.visibility = View.VISIBLE
                        viewBinding.errorView.showEmpty()
                    }
                }
            })
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mine_publish_edit_tv -> {
                viewBinding.minePublishEditTv.visibility = View.GONE
                viewBinding.minePublishDeleteCl.visibility = View.VISIBLE
                isAllVisible = true
            }
            R.id.mine_publish_selectall_tv -> {
                if (!isAllSelect) {
                    val drawable = resources.getDrawable(R.mipmap.icon_select_yes)
                    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                    viewBinding.minePublishSelectallTv.setCompoundDrawables(
                        drawable,
                        null,
                        null,
                        null
                    )
                } else {
                    val drawable = resources.getDrawable(R.mipmap.icon_select_no)
                    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                    viewBinding.minePublishSelectallTv.setCompoundDrawables(
                        drawable,
                        null,
                        null,
                        null
                    )
                }
                isAllSelect = !isAllSelect
            }
            R.id.mine_publish_deleteall_tv -> {

            }
            R.id.mine_publish_cancel_tv -> {
                viewBinding.minePublishEditTv.visibility = View.VISIBLE
                viewBinding.minePublishDeleteCl.visibility = View.GONE
                isAllVisible = false
                isAllSelect = false
            }
        }
    }

    private fun initView() {
        viewBinding.minePublishEditTv.setOnClickListener(this)
        viewBinding.minePublishCancelTv.setOnClickListener(this)
        viewBinding.minePublishDeleteallTv.setOnClickListener(this)
        viewBinding.minePublishSelectallTv.setOnClickListener(this)
        viewBinding.minePublishListview.onItemClickListener = this
        viewModel.requestOfMineRequire()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e("***publish", hidden.toString())
        if (hidden) {
            viewBinding.minePublishEditTv.visibility = View.VISIBLE
            viewBinding.minePublishDeleteCl.visibility = View.GONE
            isAllVisible = false
            isAllSelect = false
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (userAuth == "0" || userAuth == "2") {
            "企业认证未通过，暂无权限查看详情".toast(requireActivity())
            return
        }
        RequireDetailActivity.startActivity(requireActivity(), list[position].id)
    }

}