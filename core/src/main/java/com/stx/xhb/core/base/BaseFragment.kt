package com.stx.xhb.core.base

import android.content.Context
import android.view.View
import com.stx.xhb.core.rx.RxFragment

/**
 * @author: xiaohaibin.
 * @time: 2018/6/26
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: BaseFragment
 */
abstract class BaseFragment : RxFragment() {

    protected var rootView: View? = null
    protected var mContext: Context? = null
    //是否可见
    protected var isViable = false

    // 标志位，标志Fragment已经初始化完成。
    protected var isPrepared = false

    //标记已加载完成，保证懒加载只能加载一次
    protected var hasLoaded = false

    protected abstract fun getLayoutResource(): Int
}