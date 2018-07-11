package com.stx.xhb.core.mvp

/**
 * @author: xiaohaibin.
 * @time: 2018/7/3
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
interface IPresenter<V> {
     fun attachView(mvpView: V)
     fun detachView()
}