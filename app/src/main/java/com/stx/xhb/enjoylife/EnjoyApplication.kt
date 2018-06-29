package com.stx.xhb.enjoylife

import android.app.Application
import com.stx.xhb.core.base.BaseApplication
import com.stx.xhb.core.utils.LoggerHelper
import com.tencent.bugly.Bugly

/**
 * @author: xiaohaibin.
 * @time: 2018/6/28
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class EnjoyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        /** 初始化日志工具类 */
        LoggerHelper.initLogger(true)
        Bugly.init(applicationContext, "784b642b7a", true)
    }
}