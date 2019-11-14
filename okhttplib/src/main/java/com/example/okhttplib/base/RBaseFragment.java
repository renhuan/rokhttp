package com.example.okhttplib.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.okhttplib.config.MyOkHttpImp;
import com.example.okhttplib.eventbus.Event;
import com.example.okhttplib.eventbus.EventBusUtil;
import com.example.okhttplib.utils.GlideRequestOptionsUtils;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public abstract class RBaseFragment extends Fragment implements MyOkHttpImp {


    public abstract int inflaterLayout();

    public abstract void init(View view);

    View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(inflaterLayout(), container, false);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void glide(String url, ImageView imageView) {
        Glide.with(imageView).load(url).apply(GlideRequestOptionsUtils.requestOptions()).into(imageView);
    }

    public void checkNet() {
        if (!NetworkUtils.isConnected()) {
            toast("当前无网络连接，请检查网络");
        }
    }

    public String getEditText(TextView editText) {
        return editText.getText().toString().trim();
    }


    public void toast(String s) {
        ToastUtils.showShort(s);
    }

    public void startActivity(Class cls) {
        startActivity(cls, null);
    }

    /**
     * 启动activity  一个参数
     */
    public void startActivity(Class cls, String key, Object value) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(key, value);
        startActivity(cls, hashMap);
    }

    /**
     * 启动activity
     *
     * @param cls
     * @param hashMap
     */
    public void startActivity(Class cls, HashMap<String, ? extends Object> hashMap) {
        Intent intent = new Intent(getActivity(), cls);
        if (hashMap != null) {
            Iterator<?> iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                @SuppressWarnings("unchecked")
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
                        .next();
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    intent.putExtra(key, (String) value);
                }
                if (value instanceof Boolean) {
                    intent.putExtra(key, (boolean) value);
                }
                if (value instanceof Integer) {
                    intent.putExtra(key, (int) value);
                }
                if (value instanceof Float) {
                    intent.putExtra(key, (float) value);
                }
                if (value instanceof Double) {
                    intent.putExtra(key, (double) value);
                }
                if (value instanceof Serializable) {
                    intent.putExtra(key, (Serializable) value);
                }
            }
        }
        ActivityUtils.startActivity(intent);
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 子类重写接收到分发到事件
     */
    protected void receiveEvent(Event event) {
    }

    /**
     * 子类重写接受到分发的粘性事件
     */
    protected void receiveStickyEvent(Event event) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    @Override
    public void onDestroyView() {
        OkGo.getInstance().cancelTag(getActivity());
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        super.onDestroyView();
    }
}
