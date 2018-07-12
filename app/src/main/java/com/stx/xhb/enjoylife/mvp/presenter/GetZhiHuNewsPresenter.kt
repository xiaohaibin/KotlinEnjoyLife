package com.stx.xhb.enjoylife.mvp.presenter

import com.stx.xhb.core.mvp.BasePresenter
import com.stx.xhb.enjoylife.data.entity.ZhiHuNewsResponse
import com.stx.xhb.enjoylife.data.http.ApiManager
import com.stx.xhb.enjoylife.mvp.contract.GetZhiHuNewsContract

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class GetZhiHuNewsPresenter(mvpView: GetZhiHuNewsContract.View) : BasePresenter<ZhiHuNewsResponse, GetZhiHuNewsContract.View>(), GetZhiHuNewsContract.Model {

    init {
        attachView(mvpView)
    }

    override fun getNews(url: String) {
        request(ApiManager.ApiFactory.creatZhiHuApi().getZhiHuNews(url), object : LoadTaskCallback<ZhiHuNewsResponse> {
            override fun onTaskLoaded(data: ZhiHuNewsResponse) {
                getView()?.onResponse(data)
            }

            override fun onDataNotAvailable(msg: String) {
                getView()?.showMsg(msg)
            }
        })
    }

    override fun getNewsBefore(time: String) {
        request(ApiManager.ApiFactory.creatZhiHuApi().getZhiHuNewsBefore(time), object : LoadTaskCallback<ZhiHuNewsResponse> {
            override fun onTaskLoaded(data: ZhiHuNewsResponse) {
                    getView()?.onResponse(data)
            }

            override fun onDataNotAvailable(msg: String) {
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