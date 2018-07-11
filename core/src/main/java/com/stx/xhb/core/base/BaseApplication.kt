package com.stx.xhb.core.base

import android.app.Application
import android.content.Context
import com.tencent.bugly.Bugly.applicationContext

/**
 * @author: xiaohaibin.
 * @time: 2018/6/28
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
open class BaseApplication : Application() {


    companion object {

        private var mContext: Context? = null

        fun getContext(): Context? {
            return mContext
        }

    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }


}