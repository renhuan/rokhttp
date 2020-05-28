package com.renhuan.administrator.myokhttp

import android.os.Bundle
import android.util.Log
import com.renhuan.okhttplib.base.RBaseActivity
import com.renhuan.okhttplib.utils.*

class MmkvTestActivity : RBaseActivity() {

    private var boolean by mmkv.boolean("boolean")
    private var int by mmkv.int(key = "int", defaultValue = 0)
    private var long by mmkv.long("long", 0L)
    private var float by mmkv.float(key = "float", defaultValue = 0.0F)
    private var double by mmkv.double(key = "double", defaultValue = 0.0)
    private var byteArray by mmkv.byteArray(key = "byteArray")
    private var string by mmkv.string(key = "string")
    private var stringSet by mmkv.stringSet(key = "stringSet")
    private var parcelable by mmkv.parcelable<UserData>("parcelable")


    override fun inflaterLayout(): Int? {
        return null
    }

    override fun init(savedInstanceState: Bundle?) {
        boolean = true
//        int = 100
//        long = 100L
//        float = 100F
//        double = 100.0
//        byteArray = ByteArray(100).apply {
//            for (i in 0 until 100) {
//                set(i, i.toByte())
//            }
//        }
//        string = "谭嘉俊"
//        stringSet = HashSet<String>().apply {
//            for (i in 0 until 100) {
//                add("第($i)个")
//            }
//        }
//
//        Log.i(TAG, "parcelable:${parcelable}")
//
//        parcelable = UserData(age = 0, name = "任欢huanann", gender = "男").apply {
//
//        }
        Log.i(TAG, "boolean:$boolean")
        Log.i(TAG, "int:$int")
        Log.i(TAG, "long:$long")
        Log.i(TAG, "float:$float")
        Log.i(TAG, "double:$double")
        Log.i(TAG, "byteArray:$byteArray")
        Log.i(TAG, "string:$string")
        Log.i(TAG, "stringSet:$stringSet")
        Log.i(TAG, "parcelable:${parcelable}")

        /**
         * int:100
        long:100
        float:100.0
        double:100.0
        byteArray:[B@8c22473
        string:谭嘉俊
        stringSet:[第(96)个, 第(12)个, 第(35)个, 第(54)个, 第(77)个, ...
        parcelable:UserData(name='谭嘉俊', gender='男', age=0)
         */
    }

    override fun <T> onSuccess(data: T) {
    }

    override fun onError() {
        TODO("Not yet implemented")
    }


    private companion object {
        const val TAG = "TanJiaJun"
    }


}
