package com.renhuan.okhttplib.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.rxLifeScope
import com.blankj.utilcode.util.ActivityUtils
import com.lxj.xpopup.XPopup
import com.renhuan.okhttplib.utils.Renhuan
import com.renhuan.okhttplib.utils.msg
import kotlinx.coroutines.CoroutineScope

/**
 * created by renhuan
 * time : 2020/9/24 15:31
 * describe :
 */
abstract class RBaseViewModel : ViewModel() {

    abstract fun showLoading()

    abstract fun dismissLoading()

    /**
     * base协程  处理loading 和 弹出异常message
     */
    protected fun rxScope(
        isShowLoading: Boolean = true,
        onError: ((Throwable) -> Unit)? = null,
        action: suspend (CoroutineScope) -> Unit
    ) {
        rxLifeScope.launch(
            { action(this) },
            {
                if (!it.msg.isBlank()) {
                    Renhuan.toast(it.msg)
                }
                onError?.let { its -> its(it) }
                it.printStackTrace()
            },
            { if (isShowLoading) showLoading() },
            { if (isShowLoading) dismissLoading() }
        )
    }
}