package com.example.okhttplib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.okhttplib.config.RBaseOkHttpImp
import com.example.okhttplib.eventbus.Event
import com.example.okhttplib.eventbus.EventBusUtil
import com.example.okhttplib.utils.Renhuan
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class RBaseFragment : Fragment(), RBaseOkHttpImp {

    /**
     * 判断是否已经懒加载
     */
    private var isLoaded = false

    private val mView by lazy { View.inflate(Renhuan.getContext(), inflaterLayout(), null) }

    abstract fun inflaterLayout(): Int

    abstract fun init(view: View)

    abstract fun lazyLoad()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mView
    }

    /***
     * FragmentPagerAdapter 要设置BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
     */
    override fun onResume() {
        super.onResume()
        if (!isLoaded) {
            isLoaded = true
            lazyLoad()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isRegisterEventBus) {
            EventBusUtil.register(this)
        }
        init(mView)
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
        Renhuan.cancelHttp(activity)
        if (isRegisterEventBus) {
            EventBusUtil.unregister(this)
        }
        super.onDestroyView()
    }
}
