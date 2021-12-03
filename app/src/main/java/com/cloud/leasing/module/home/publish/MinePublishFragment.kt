package com.cloud.leasing.module.home.publish

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.base.list.XRecyclerView
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentMinePublishBinding

class MinePublishFragment :
    BaseFragment<FragmentMinePublishBinding>(FragmentMinePublishBinding::inflate),
    View.OnClickListener {

    companion object {
        fun newInstance() = MinePublishFragment()
    }

    private var isAllSelect = true

    private val viewModel: MinePublishViewModel by viewModels()

    override fun getPageName() = PageName.MINEPUBLISH

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mine_publish_edit_tv -> {
                viewBinding.minePublishEditTv.visibility = View.GONE
                viewBinding.minePublishDeleteCl.visibility = View.VISIBLE
            }
            R.id.mine_publish_selectall_tv -> {
                if (isAllSelect) {
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
            }
        }
    }

    private fun initView() {
        viewBinding.minePublishEditTv.setOnClickListener(this)
        viewBinding.minePublishCancelTv.setOnClickListener(this)
        viewBinding.minePublishDeleteallTv.setOnClickListener(this)
        viewBinding.minePublishSelectallTv.setOnClickListener(this)
        viewBinding.minePublishRecyclerview.init(
            XRecyclerView.Config()
                .setViewModel(viewModel)
                .setOnItemClickListener(object : XRecyclerView.OnItemClickListener {
                    override fun onItemClick(
                        parent: RecyclerView,
                        view: View,
                        viewData: BaseViewData<*>,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(
                            requireActivity(),
                            "条目点击: ${viewData.value}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                })
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
                        if (extra is String) {
                            Toast.makeText(
                                requireActivity(),
                                "条目子View点击: $extra",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        )
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e("***publish", hidden.toString())
        if (hidden) {
            viewBinding.minePublishEditTv.visibility = View.VISIBLE
            viewBinding.minePublishDeleteCl.visibility = View.GONE
        }
    }

}