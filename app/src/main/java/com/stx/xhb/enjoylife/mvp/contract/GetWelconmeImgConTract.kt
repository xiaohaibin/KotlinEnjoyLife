package com.stx.xhb.enjoylife.mvp.contract

import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel
import com.stx.xhb.enjoylife.data.entity.SplashImgResponse
import retrofit2.http.Query

/**
 * @author: xiaohaibin.
 * @time: 2018/12/25
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: GetWelconmeImgConTract
 */
interface GetWelconmeImgConTract {

    interface View : IBaseView {
        fun getSplashImgSuccess(data: SplashImgResponse)
        fun getSplashImgFailed(msg: String)
    }

    interface Model : IModel {
        fun getSplashImg(resolution: String, width: Int, height: Int);
    }

}