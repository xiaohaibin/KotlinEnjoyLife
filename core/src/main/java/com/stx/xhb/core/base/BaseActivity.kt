package com.stx.xhb.core.base

import com.stx.xhb.core.rx.RxAppCompatActivity

/**
 * @author: xiaohaibin.
 * @time: 2018/6/25
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:BaseActivity
 */
abstract class  BaseActivity: RxAppCompatActivity() {

    protected abstract fun getLayoutResource(): Int


}