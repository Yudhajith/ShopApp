package com.example.shopapp.utils

import android.annotation.SuppressLint
import android.content.Context

class ContextUtil {
    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var loginContext: Context
        @SuppressLint("StaticFieldLeak")
        private lateinit var registerContext: Context

        fun setLoginContext(context: Context?) {
            loginContext = context!!
        }

        fun getLoginContext(): Context {
            return loginContext
        }

        fun setRegisterContext(context: Context?) {
            registerContext = context!!
        }

        fun getRegisterContext(): Context {
            return registerContext
        }
    }
}