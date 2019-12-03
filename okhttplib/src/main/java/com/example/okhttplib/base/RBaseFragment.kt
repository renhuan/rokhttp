package com.example.okhttplib.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.example.okhttplib.config.RBaseOkHttpImp
import com.example.okhttplib.eventbus.Event
import com.example.okhttplib.eventbus.EventBusUtil
import com.example.okhttplib.utils.GlideRequestOptionsUtils
import com.lzy.okgo.OkGo

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import java.io.Serializable
import java.util.HashMap


abstract class RBaseFragment : Fragment(), RBaseOkHttpImp {

    internal var view: View? = null

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    open val isRegisterEventBus: Boolean
        get() = false


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
     * textview的值
     */
    fun TextView.text(): String {
        return this.text.toString().trim()
    }

    /**
     * 加载图片
     */
    fun ImageView.loadUrl(url: String) {
        Glide.with(this).load(url).apply(GlideRequestOptionsUtils.requestOptions()).into(this)
    }

    fun toast(s: String) {
        ToastUtils.showShort(s)
    }

    /**
     * 子类重写接收到分发到事件
     */
    open fun receiveEvent(event: Event<*>) {}

    /**
     * 子类重写接受到分发的粘性事件
     */
    protected fun receiveStickyEvent(event: Event<*>) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventBusCome(event: Event<*>?) {
        if (event != null) {
            receiveEvent(event)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onStickyEventBusCome(event: Event<*>?) {
        if (event != null) {
            receiveStickyEvent(event)
        }
    }

    override fun onDestroyView() {
        OkGo.getInstance().cancelTag(activity)
        if (isRegisterEventBus) {
            EventBusUtil.unregister(this)
        }
        super.onDestroyView()
    }
}
