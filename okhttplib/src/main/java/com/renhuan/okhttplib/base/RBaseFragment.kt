package com.renhuan.okhttplib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.rxLifeScope
import com.lxj.xpopup.XPopup
import com.renhuan.okhttplib.eventbus.REventBus
import com.renhuan.okhttplib.utils.Renhuan
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class RBaseFragment : Fragment() {

    protected val mmkv: MMKV by lazy { MMKV.defaultMMKV() }

    private val loading by lazy { XPopup.Builder(activity).asLoading() }

    /**
     * 判断是否已经懒加载
     */
    private var isLoaded = false

    private val mView by lazy { View.inflate(Renhuan.getContext(), inflaterLayout(), null) }

    protected abstract fun inflaterLayout(): Int

    protected abstract fun lazyLoad()

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
            REventBus.register(this)
        }
        initView(mView)
        initData()
        initListener()
        initRequest()
    }

    /**
     * base协程  处理loading 和 弹出异常message
     */
    protected fun rxScope(isShowLoading: Boolean = true, action: suspend (CoroutineScope) -> Unit) {
        rxLifeScope.launch(
                { action(this) },
                { Renhuan.toast(it.message) },
                { if (isShowLoading) loading.show() },
                { if (isShowLoading) loading.dismiss() }
        )
    }

    /**
     * 子类重写initView()
     */
    protected open fun initView(view: View) {}

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
    protected open val isRegisterEventBus: Boolean
        get() = false

    /**
     * 子类重写接收到分发到事件
     */
    protected open fun receiveEvent(event: Any) {}

    /**
     * 子类重写接受到分发的粘性事件
     */
    protected open fun receiveStickyEvent(event: Any) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    private fun onEventBus(event: Any?) {
        event?.let { receiveEvent(it) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    private fun onStickyEventBus(event: Any?) {
        event?.let { receiveStickyEvent(it) }
    }

    override fun onDestroyView() {
        if (isRegisterEventBus) {
            REventBus.unregister(this)
        }
        super.onDestroyView()
    }
}
