package com.stx.xhb.enjoylife.mvp.presenter

import android.text.TextUtils.isEmpty
import com.stx.xhb.core.mvp.BasePresenter
import com.stx.xhb.enjoylife.data.entity.TuChongWallPaperResponse
import com.stx.xhb.enjoylife.data.http.ApiManager
import com.stx.xhb.enjoylife.mvp.contract.GetWallPaperContract

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class GetWallPaperPresenter(mvpView: GetWallPaperContract.View) : BasePresenter<TuChongWallPaperResponse, GetWallPaperContract.View>(), GetWallPaperContract.Model {

    init {
        attachView(mvpView)
    }

    override fun onCreate() {

    }

    override fun start() {

    }

    override fun destory() {
        detachView()
    }

    override fun getWallPaper(page: Int) {
        request(ApiManager.ApiFactory.createTuChongApi().getWallPaper(page), object : LoadTaskCallback<TuChongWallPaperResponse> {
            override fun onTaskLoaded(data: TuChongWallPaperResponse) {
                if (getView() != null && data.feedList != null && !data.feedList.isEmpty()) {
                    getView()?.onResponse(data.feedList, data.more)
                }
            }

            override fun onDataNotAvailable(msg: String) {
                if (getView() != null) {
                    getView()?.showMsg(msg)
                }
            }
        })
    }
}