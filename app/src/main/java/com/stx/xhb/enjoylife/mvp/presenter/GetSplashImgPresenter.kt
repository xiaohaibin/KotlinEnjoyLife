package com.stx.xhb.enjoylife.mvp.presenter

import com.stx.xhb.core.mvp.BasePresenter
import com.stx.xhb.enjoylife.data.entity.SplashImgResponse
import com.stx.xhb.enjoylife.data.http.ApiManager
import com.stx.xhb.enjoylife.mvp.contract.GetWelconmeImgConTract

/**
 * @author: xiaohaibin.
 * @time: 2018/12/25
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: GetSplashImgPresenter
 */
class GetSplashImgPresenter(mvpView: GetWelconmeImgConTract.View) : BasePresenter<SplashImgResponse, GetWelconmeImgConTract.View>(), GetWelconmeImgConTract.Model {

    init {
        attachView(mvpView)
    }

    override fun getSplashImg(resolution: String, width: Int, height: Int) {
        request(ApiManager.ApiFactory.createTuChongApi().getSplashImg(resolution, width, height), object : LoadTaskCallback<SplashImgResponse> {
            override fun onTaskLoaded(data: SplashImgResponse) {
                if (data.result.equals("SUCCESS")) {
                    getView()?.getSplashImgSuccess(data)
                }else{
                    getView()?.getSplashImgFailed(data.result)
                }
            }

            override fun onDataNotAvailable(msg: String) {
                getView()?.getSplashImgFailed(msg)
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