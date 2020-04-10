package com.example.okhttplib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.okhttplib.config.RBaseOkHttpImp
import com.example.okhttplib.eventbus.Event
import com.example.okhttplib.eventbus.EventBusUtil
import com.lzy.okgo.OkGo
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class RBaseFragment : Fragment(), RBaseOkHttpImp {

    internal var view: View? = null

    abstract fun inflaterLayout(): Int

    abstract fun init(view: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(inflaterLayout(), container, false)
        if (isRegisterEventBus) {
            EventBusUtil.register(this)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
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

    override fun onDestroyView() {
        OkGo.getInstance().cancelTag(activity)
        if (isRegisterEventBus) {
            EventBusUtil.unregister(this)
        }
        super.onDestroyView()
    }
}
