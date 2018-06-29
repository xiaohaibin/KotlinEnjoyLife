package com.stx.xhb.core.mvp

/**
 * @author: xiaohaibin.
 * @time: 2018/6/26
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:IBaseView
 */
interface IBaseView {

    //显示loading
    fun showLoading()

    //关闭loading
    fun hideLoading()

    //显示吐司
    fun showToast(msg: String)

}