package com.example.okhttplib.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.okhttplib.config.RBaseOkHttpImp
import com.example.okhttplib.eventbus.Event
import com.example.okhttplib.eventbus.EventBusUtil
import com.example.okhttplib.utils.Renhuan
import com.lzy.okgo.OkGo
import com.tencent.mmkv.MMKV
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class RBaseActivity : AppCompatActivity(), RBaseOkHttpImp {

    val mmkv: MMKV by lazy { MMKV.defaultMMKV() }

    abstract fun inflaterLayout(): Int?

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //加载布局
        inflaterLayout()?.let { setContentView(it) }

        //注册eventbus
        if (isRegisterEventBus) {
            EventBusUtil.register(this)
        }

        //初始化数据
        init(savedInstanceState)
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    open val isRegisterEventBus: Boolean
        get() = false

    /**
     * 子类重写接收到分发到事件
     */
    open fun receiveEvent(event: Event<*>) {}

    /**
     * 子类重写接受到分发的粘性事件
     */
    open fun receiveStickyEvent(event: Event<*>) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventBusCome(event: Event<*>?) {
        event?.let { receiveEvent(it) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onStickyEventBusCome(event: Event<*>?) {
        event?.let { receiveStickyEvent(it) }
    }

    override fun onDestroy() {
        Renhuan.cancelHttp(this)
        if (isRegisterEventBus) {
            EventBusUtil.unregister(this)
        }
        super.onDestroy()
    }
}