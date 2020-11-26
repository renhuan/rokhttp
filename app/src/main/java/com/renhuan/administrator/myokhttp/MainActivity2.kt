package com.renhuan.administrator.myokhttp

import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity


class MainActivity2 : AppCompatActivity() {
    private val smsHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentResolver.registerContentObserver(
                Uri.parse("content://sms/"),
                true,
                SmsObserver(smsHandler)
        )
    }

    class SmsObserver(handler: Handler) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            if (uri.toString() == "content://sms/raw") {
                return
            }
            uri
            println("---------uri--" + uri?.encodedUserInfo)

        }
    }

}