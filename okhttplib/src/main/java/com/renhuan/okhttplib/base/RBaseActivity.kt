package com.renhuan.okhttplib.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.rxLifeScope
import com.lxj.xpopup.XPopup
import com.renhuan.okhttplib.eventbus.REventBus
import com.renhuan.okhttplib.utils.Renhuan
import com.renhuan.okhttplib.utils.msg
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import rxhttp.wrapper.exception.ParseException


abstract class RBaseActivity : AppCompatActivity() {

    protected val mmkv: MMKV by lazy { MMKV.defaultMMKV() }

    private val loading by lazy { XPopup.Builder(this).dismissOnTouchOutside(false).asLoading() }

    protected abstract fun inflaterLayout(): Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //加载布局
        inflaterLayout()?.let { setContentView(it) }

        //注册eventbus
        if (isRegisterEventBus) {
            REventBus.register(this)
        }

        //初始化数据
        initView(savedInstanceState)
        initData()
        initListener()
        initRequest()
    }

    /**
     * base协程  处理loading 和 弹出异常message
     */
    protected fun rxScope(isShowLoading: Boolean = true, onError: ((Throwable) -> Unit)? = null, action: suspend (CoroutineScope) -> Unit) {
        rxLifeScope.launch(
                { action(this) },
                {
                    Renhuan.toast(it.msg)
                    onError?.let { its -> its(it) }
                },
                { if (isShowLoading) loading.show() },
                { if (isShowLoading) loading.smartDismiss() }
        )
    }

    /**
     * 子类重写initView()
     */
    protected open fun initView(savedInstanceState: Bundle?) {}

    /**
     * 子类重写initData()
     */
    protected open fun initData() {}

    /**
     * 子类重写initListener()
     */
    protected open fun initListener() {}

    /**
     * 子类重写initRequest()
     */
    protected open fun initRequest() {}

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

    override fun onDestroy() {
        if (isRegisterEventBus) {
            REventBus.unregister(this)
        }
        super.onDestroy()
    }
}