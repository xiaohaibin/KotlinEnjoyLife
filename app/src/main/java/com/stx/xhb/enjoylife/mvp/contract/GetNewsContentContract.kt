package com.stx.xhb.enjoylife.mvp.contract

import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel
import com.stx.xhb.enjoylife.data.entity.ZhiHuNewsContentResponse

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
interface GetNewsContentContract {

    interface Model : IModel {
        fun getNewsContent(id: String)
    }

    interface View : IBaseView {

        fun onResponse(response: ZhiHuNewsContentResponse)

        fun onFailed(msg: String)

        fun showLoading()

        fun hideLoading()
    }
}