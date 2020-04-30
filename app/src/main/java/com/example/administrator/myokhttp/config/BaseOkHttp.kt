import android.text.TextUtils
import com.example.okhttplib.config.RBaseOkHttp
import com.example.okhttplib.config.RBaseOkHttpImp
import com.lzy.okgo.model.HttpHeaders
import com.lzy.okgo.model.Response
import java.util.*

/**
 * http请求Base类
 */

class BaseOkHttp : RBaseOkHttp() {

    override fun onRSuccess(response: Response<String>?, rBaseOkHttpImp: RBaseOkHttpImp?, requestCode: Int) {
//        val topActivity = ActivityManager.getInstance().currentActivity() as BaseActivity
//        response?.body()?.let {
//            val jsonObject = JSONObject(it)
//            val code = jsonObject.getInt("code")
//            if (code == 200) {
//                rBaseOkHttpImp?.onSuccess(it, requestCode)
//            } else {
//                val msg = jsonObject.getString("message")
//                topActivity.toast(msg)
//                rBaseOkHttpImp?.onError(it, requestCode)
//            }
//        }

    }

//    private var xPopup: LoadingPopupView? = null

    override fun setHttpHead(httpHeaders: HttpHeaders): HttpHeaders {
//        httpHeaders.put(Constants.HEAD, App.token)
//        httpHeaders.put(Constants.IMEI, DeviceUtils.getAndroidID())
//        httpHeaders.put(Constants.MAC, DeviceUtils.getMacAddress())
        return httpHeaders
    }

//    override fun setParameter(hashMap: HashMap<String, String?>): RBaseOkHttp {
//        hashMap["ts"] = System.currentTimeMillis().toString()
//        hashMap["sign"] = EncryptUtils.encryptMD5ToString(getSign(hashMap) + Constants.KEY)
//        return super.setParameter(hashMap)
//    }

    override fun showLoading() {
//        if (xPopup == null) {
//            xPopup = XPopup.Builder(ActivityUtils.getTopActivity())
//                    .dismissOnTouchOutside(false)
//                    .hasShadowBg(false)
//                    .asLoading()
//                    .bindLayout(R.layout.loading_lottie)
//        }
//        xPopup?.show()
    }

    override fun hideLoading() {
//        xPopup?.dismiss()
    }

    companion object {
        fun newInstance(): BaseOkHttp {
            return BaseOkHttp()
        }

        fun getSign(hashMap: HashMap<String, String?>): String {
            val map = TreeMap<String, String?> { obj1, obj2 -> obj1.compareTo(obj2) }
            map.putAll(hashMap)
            val url = StringBuilder()
            for ((key, value) in map) {
                if (!TextUtils.isEmpty(value)) {
                    url.append(key).append("=").append(value).append("&")
                }
            }
            return url.substring(0, url.length - 1)
        }
    }
}
