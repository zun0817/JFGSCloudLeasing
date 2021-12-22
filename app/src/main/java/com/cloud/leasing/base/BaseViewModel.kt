package com.cloud.leasing.base

import androidx.lifecycle.ViewModel
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.persistence.XKeyValue
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * ViewModel基类
 */
abstract class BaseViewModel : ViewModel(), IGetPageName {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    /**
     * 添加Disposable
     */
    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}