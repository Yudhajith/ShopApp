package com.example.shopapp.utils

import android.annotation.SuppressLint
import android.content.Context

class ContextUtil {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var loginContext: Context
        @SuppressLint("StaticFieldLeak")
        private lateinit var registerContext: Context
        @SuppressLint("StaticFieldLeak")
        private lateinit var homeContext: Context
        @SuppressLint("StaticFieldLeak")
        private lateinit var subcategoryContext: Context
        @SuppressLint("StaticFieldLeak")
        private lateinit var productContext: Context

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

        fun setHomeContext(context: Context?) {
            homeContext = context!!
        }

        fun getHomeContext(): Context {
            return homeContext
        }

        fun setSubcategoryContext(context: Context?) {
            subcategoryContext = context!!
        }

        fun getSubcategoryContext(): Context {
            return subcategoryContext
        }

        fun setProductContext(context: Context?) {
            productContext = context!!
        }

        fun getProductContext(): Context {
            return productContext
        }
    }
}