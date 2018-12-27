package com.stx.xhb.enjoylife.mvp.contract

import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel
import com.stx.xhb.enjoylife.data.entity.feed.FeedListBean

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
interface GeTuChongFeedContract {

    interface Model : IModel {
        fun getFeed(page: Int, type: String, posId: String)
    }

    interface View : IBaseView {
        fun onResponse(feedList: List<FeedListBean>, isMore: Boolean)
    }

}