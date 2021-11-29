package com.cloud.leasing.module.smallvideo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentSmallVideoBinding

/**
 * 小视频
 */
class SmallVideoFragment : BaseFragment<FragmentSmallVideoBinding>(FragmentSmallVideoBinding::inflate) {

    private val viewModel: SmallVideoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewModel.helloWorldLiveData.observe(viewLifecycleOwner) {
            val video = it.getOrNull()
            if (null != video) {
                viewBinding.tvHello.text = "${video.id}-${video.title}"
            } else {
                viewBinding.tvHello.text = resources.getString(R.string.page_state_network_error)
            }
        }
        viewModel.requestVideoDetail("100")
    }

    @PageName
    override fun getPageName() = PageName.SMALL_VIDEO

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        // 这里可以添加页面打点
    }
}