package com.stx.xhb.enjoylife.mvp.contract

import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel
import com.stx.xhb.enjoylife.data.entity.ZhiHuNewsResponse

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
interface GetZhiHuNewsContract {

    interface Model : IModel {
        fun getNews(url: String)
        fun getNewsBefore(time: String)
    }


    interface View : IBaseView {
        fun onResponse(zhiHuNewsResponse: ZhiHuNewsResponse)
    }
}