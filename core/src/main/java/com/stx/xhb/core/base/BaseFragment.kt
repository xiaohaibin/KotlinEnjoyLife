package com.stx.xhb.core.base

import com.stx.xhb.core.rx.RxFragment

/**
 * @author: xiaohaibin.
 * @time: 2018/6/26
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: BaseFragment
 */
abstract class BaseFragment : RxFragment() {
    protected abstract fun getLayoutResource(): Int
}