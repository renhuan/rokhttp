package com.example.okhttplib.base

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.example.okhttplib.config.RBaseOkHttpImp
import com.example.okhttplib.eventbus.Event
import com.example.okhttplib.eventbus.EventBusUtil
import com.example.okhttplib.utils.GlideRequestOptionsUtils
import com.noober.background.BackgroundLibrary
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class RBaseActivity : AppCompatActivity(), RBaseOkHttpImp {

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    open val isRegisterEventBus: Boolean
        get() = false

    abstract fun inflaterLayout(): Int?

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(this)
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
    private fun receiveStickyEvent(event: Event<*>) {}

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

    override fun onDestroy() {
        if (isRegisterEventBus) {
            EventBusUtil.unregister(this)
        }
        super.onDestroy()
    }
}