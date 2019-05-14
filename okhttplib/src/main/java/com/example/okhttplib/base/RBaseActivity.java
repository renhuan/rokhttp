package com.example.okhttplib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.okhttplib.config.MyOkHttpImp;
import com.example.okhttplib.eventbus.Event;
import com.example.okhttplib.eventbus.EventBusUtil;
import com.lzy.okgo.OkGo;
import com.noober.background.BackgroundLibrary;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public abstract class RBaseActivity extends AppCompatActivity implements MyOkHttpImp {

    public abstract int inflaterLayout();

    public abstract void init(Bundle savedInstanceState);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);

        //加载布局
        if (inflaterLayout() != 0) {
            setContentView(inflaterLayout());
        }

        //注册eventbus
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }

        //初始化数据
        init(savedInstanceState);
    }

    /***
     *                    _ooOoo_
     *                   o8888888o
     *                   88" . "88
     *                   (| -_- |)
     *                    O\ = /O
     *                ____/`---'\____
     *              .   ' \\| |// `.
     *               / \\||| : |||// \
     *             / _||||| -:- |||||- \
     *               | | \\\ - /// | |
     *             | \_| ''\---/'' | |
     *              \ .-\__ `-` ___/-. /
     *           ___`. .' /--.--\ `. . __
     *        ."" '< `.___\_<|>_/___.' >'"".
     *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
     *         \ \ `-. \_ __\ /__ _/ .-` / /
     * ======`-.____`-.___\_____/___.-`____.-'======
     *                    `=---='
     *
     * .............................................
     *          activity的一些用于工具  start
     */


    public void toast(String s) {
        ToastUtils.showShort(s);
    }


    /**
     * 关闭当前activity
     */
    public void finishActivity() {
        ActivityUtils.finishActivity(this);
    }

    /**
     * 启动activity  无参数
     */
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
     * 启动activity  多个参数
     */
    public void startActivity(Class cls, HashMap<String, ? extends Object> hashMap) {
        Intent intent = new Intent(this, cls);
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
    /***
     * _ooOoo_
     * o8888888o
     * 88" . "88
     * (| -_- |)
     *  O\ = /O
     * ___/`---'\____
     * .   ' \\| |// `.
     * / \\||| : |||// \
     * / _||||| -:- |||||- \
     * | | \\\ - /// | |
     * | \_| ''\---/'' | |
     * \ .-\__ `-` ___/-. /
     * ___`. .' /--.--\ `. . __
     * ."" '< `.___\_<|>_/___.' >'"".
     * | | : `- \`.;`\ _ /`;.`/ - ` : | |
     * \ \ `-. \_ __\ /__ _/ .-` / /
     * ======`-.____`-.___\_____/___.-`____.-'======
     * `=---='
     * ............................................
     *           activity的一些用于工具  end
     */

    /***
     *                    _ooOoo_
     *                   o8888888o
     *                   88" . "88
     *                   (| -_- |)
     *                    O\ = /O
     *                ____/`---'\____
     *              .   ' \\| |// `.
     *               / \\||| : |||// \
     *             / _||||| -:- |||||- \
     *               | | \\\ - /// | |
     *             | \_| ''\---/'' | |
     *              \ .-\__ `-` ___/-. /
     *           ___`. .' /--.--\ `. . __
     *        ."" '< `.___\_<|>_/___.' >'"".
     *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
     *         \ \ `-. \_ __\ /__ _/ .-` / /
     * ======`-.____`-.___\_____/___.-`____.-'======
     *                    `=---='
     *
     * .............................................
     *          eventbus  start
     */
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

    /***
     * _ooOoo_
     * o8888888o
     * 88" . "88
     * (| -_- |)
     *  O\ = /O
     * ___/`---'\____
     * .   ' \\| |// `.
     * / \\||| : |||// \
     * / _||||| -:- |||||- \
     * | | \\\ - /// | |
     * | \_| ''\---/'' | |
     * \ .-\__ `-` ___/-. /
     * ___`. .' /--.--\ `. . __
     * ."" '< `.___\_<|>_/___.' >'"".
     * | | : `- \`.;`\ _ /`;.`/ - ` : | |
     * \ \ `-. \_ __\ /__ _/ .-` / /
     * ======`-.____`-.___\_____/___.-`____.-'======
     * `=---='
     * ............................................
     *           eventbus  end
     */
    @Override
    protected void onDestroy() {
        OkGo.getInstance().cancelTag(this);
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        super.onDestroy();
    }
}
