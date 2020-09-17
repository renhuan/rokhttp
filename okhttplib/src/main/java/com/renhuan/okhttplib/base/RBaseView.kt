package com.renhuan.okhttplib.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.lxj.xpopup.XPopup
import com.renhuan.okhttplib.eventbus.REventBus
import com.renhuan.okhttplib.utils.Renhuan
import com.renhuan.okhttplib.utils.msg
import com.rxlife.coroutine.RxLifeScope
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * created by renhuan
 * time : 2020/9/10 17:57
 * describe :
 */

abstract class RBaseView(context: Context, attributeSet: AttributeSet?) : FrameLayout(context, attributeSet) {

    protected val mmkv: MMKV by lazy { MMKV.defaultMMKV() }

    private val loading by lazy { XPopup.Builder(context).dismissOnTouchOutside(false).asLoading() }

    protected abstract fun inflaterLayout(): Int?

    private val mView by lazy { inflaterLayout()?.let { View.inflate(context, it, this) } }

    init {
        //初始化数据
        initView(mView)
        initData()
        initListener()
        initRequest()
    }

    /**
     * base协程  处理loading 和 弹出异常message
     */
    private var job: Job? = null
    protected fun rxScope(
        isShowLoading: Boolean = true,
        onError: ((Throwable) -> Unit)? = null,
        action: suspend (CoroutineScope) -> Unit
    ) {
        job = RxLifeScope().launch(
            { action(this) },
            {
                if (!it.msg.isBlank()) {
                    Renhuan.toast(it.msg)
                }
                onError?.let { its -> its(it) }
                it.printStackTrace()
            },
            { if (isShowLoading) loading.show() },
            { if (isShowLoading) loading.smartDismiss() }
        )
    }

    /**
     * 子类重写initView()
     */
    abstract fun initView(view: View?)

    /**
     * 子类重写initData()
     */
    abstract fun initData()

    /**
     * 子类重写initListener()
     */
    abstract fun initListener()

    /**
     * 子类重写initRequest()
     */
    abstract fun initRequest()

    /**
     * 子类重写是否注册事件分发
     */
    open val isRegisterEventBus: Boolean
        get() = false

    /**
     * 子类重写接收到分发到事件
     */
    open fun receiveEvent(event: Any) {}

    /**
     * 子类重写接受到分发的粘性事件
     */
    open fun receiveStickyEvent(event: Any) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventBus(event: Any?) {
        event?.let { receiveEvent(it) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onStickyEventBus(event: Any?) {
        event?.let { receiveStickyEvent(it) }
    }

    /**
     * 停止EventBus
     */
    fun onStop() {
        if (isRegisterEventBus) {
            REventBus.unregister(this)
        }
    }

    /**
     * 启动EventBus
     */
    fun onStart() {
        if (isRegisterEventBus) {
            REventBus.register(this)
        }
    }

    /**
     * 取消网络请求
     */
    fun onDestroy() {
        job?.let {
            it.cancel()
        }
    }
}