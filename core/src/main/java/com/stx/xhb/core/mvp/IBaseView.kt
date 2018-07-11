package com.stx.xhb.core.mvp

/**
 * @author: xiaohaibin.
 * @time: 2018/6/26
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:IBaseView
 */
interface IBaseView {
    fun showLoading()
    fun hideLoading()
    fun showMsg(msg:String)
}