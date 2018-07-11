package com.stx.xhb.enjoylife.mvp.contract

import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel
import com.stx.xhb.enjoylife.data.entity.VideoResponse

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
interface GetVideoContract {

    interface Model : IModel {
        fun getVideoInfo(date: String, num: Int)
    }

    interface getVideoView : IBaseView {
        fun onResponse(response: VideoResponse)
    }
}