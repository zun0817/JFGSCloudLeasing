package com.cloud.leasing.module.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.R
import com.cloud.leasing.adapter.SimpleAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.base.list.XRecyclerView
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.EventName
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentHomeBinding
import com.cloud.leasing.eventbus.XEventBus
import com.cloud.leasing.module.home.have.HaveActivity
import com.cloud.leasing.module.home.want.WantActivity
import com.gyf.immersionbar.ktx.immersionBar



@RequiresApi(Build.VERSION_CODES.M)
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    View.OnScrollChangeListener, View.OnClickListener {

    private var mPictureList: MutableList<Int> = ArrayList()

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSystemBar()
        initView()
        initViewPager()
    }

    private fun initViewPager() {
        viewBinding.mainBannerView.setLifecycleRegistry(lifecycle)
            .setAdapter(SimpleAdapter())
            .setIndicatorHeight(6)
            .setIndicatorSliderGap(8)
            .setIndicatorSliderRadius(10)
            .setIndicatorSliderWidth(36)
            .create(getPicList())
    }

    private fun initSystemBar() {
        immersionBar {
            statusBarColor(R.color.color_0e62B8)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.home_want_tv -> WantActivity.startActivity(requireActivity())
            R.id.home_have_tv -> HaveActivity.startActivity(requireActivity())
        }
    }

    private fun getPicList(): MutableList<Int> {
        for (i in 0..3) {
            val drawable =
                resources.getIdentifier("icon_banner", "mipmap", requireActivity().packageName)
            mPictureList.add(drawable)
        }
        return mPictureList;
    }

    private fun initView() {
        viewBinding.homeWantTv.setOnClickListener(this)
        viewBinding.homeHaveTv.setOnClickListener(this)
        viewBinding.homePublishTv.setOnClickListener(this)
        viewBinding.homeServiceTv.setOnClickListener(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            viewBinding.mainScrollview.setOnScrollChangeListener(this)
        }
        viewBinding.rvList.init(
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
                        Toast.makeText(context, "条目点击: ${viewData.value}", Toast.LENGTH_SHORT)
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
                            Toast.makeText(context, "条目子View点击: $extra", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        )

        XEventBus.observe(viewLifecycleOwner, EventName.REFRESH_HOME_LIST) { message: String ->
            viewBinding.rvList.refreshList()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        XEventBus.observe(viewLifecycleOwner, EventName.TEST) { message: String ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onScrollChange(
        v: View?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (scrollY >= 150) {
            viewBinding.mainSearchImg.visibility = View.VISIBLE
        } else if (scrollY < 150) {
            viewBinding.mainSearchImg.visibility = View.GONE
        }
    }


    override fun onPause() {
        super.onPause()
        viewBinding.mainBannerView.stopLoop()
    }

    override fun onResume() {
        super.onResume()
        viewBinding.mainBannerView.startLoop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding.mainBannerView.stopLoop()
    }

    @PageName
    override fun getPageName() = PageName.HOME

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initSystemBar()
    }

}