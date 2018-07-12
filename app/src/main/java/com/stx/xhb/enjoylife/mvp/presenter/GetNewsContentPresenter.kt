package com.stx.xhb.enjoylife.mvp.presenter

import com.stx.xhb.core.mvp.BasePresenter
import com.stx.xhb.enjoylife.data.entity.ZhiHuNewsContentResponse
import com.stx.xhb.enjoylife.data.http.ApiManager
import com.stx.xhb.enjoylife.mvp.contract.GetNewsContentContract

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class GetNewsContentPresenter(mvpView: GetNewsContentContract.View) : BasePresenter<ZhiHuNewsContentResponse, GetNewsContentContract.View>(), GetNewsContentContract.Model {

    init {
        attachView(mvpView)
    }

    override fun getNewsContent(id: String) {
        getView()?.showLoading()
        request(ApiManager.ApiFactory.creatZhiHuApi().getZhiHuNewsContent(id), object : LoadTaskCallback<ZhiHuNewsContentResponse> {
            override fun onTaskLoaded(data: ZhiHuNewsContentResponse) {
                getView()?.hideLoading()
                getView()?.onResponse(data)
            }

            override fun onDataNotAvailable(msg: String) {
                getView()?.hideLoading()
                getView()?.showMsg(msg)
            }
        })
    }

    override fun onCreate() {

    }

    override fun start() {

    }

    override fun destory() {
        detachView()
    }
}