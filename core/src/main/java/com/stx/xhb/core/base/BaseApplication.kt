package com.stx.xhb.core.base

import android.app.Application
import android.content.Context

/**
 * @author: xiaohaibin.
 * @time: 2018/6/28
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
open class BaseApplication:Application() {

    private var mContext: Context? = null

    fun getContext(): Context? {
        return mContext
    }
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }
}