package com.cloud.leasing.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.cloud.leasing.constant.AppManager
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.toast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

/**
 * Activity基类
 */
abstract class BaseActivity<T : ViewBinding>(val inflater: (inflater: LayoutInflater) -> T) :
    SwipeBackActivity(),
    IGetPageName {

    var userAuth = "0"
    private var pressedTime = 0L
    protected lateinit var viewBinding: T
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = inflater(layoutInflater)
        setContentView(viewBinding.root)
        setSwipeBackEnable(swipeBackEnable())
        //AppManager.getInstance().addActivity(this)
        userAuth = XKeyValue.getString(Constant.USER_AUTH, "0")
    }

    override fun onStart() {
        super.onStart()
        // 这里可以添加页面打点
    }

    override fun onStop() {
        super.onStop()
        // 这里可以添加页面打点
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    /**
     * 默认开启左滑返回,如果需要禁用,请重写此方法
     */
    protected open fun swipeBackEnable() = true

    /**
     * 添加Disposable
     */
    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}