package com.stx.xhb.enjoylife.mvp.presenter

import android.text.TextUtils
import com.stx.xhb.core.mvp.BasePresenter
import com.stx.xhb.enjoylife.data.entity.VideoResponse
import com.stx.xhb.enjoylife.data.http.ApiManager
import com.stx.xhb.enjoylife.mvp.contract.GetVideoContract
import java.util.HashMap

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class GetVideoPresenter(mvpView: GetVideoContract.getVideoView) : BasePresenter<VideoResponse, GetVideoContract.getVideoView>(), GetVideoContract.Model {

    init {
        attachView(mvpView)
    }

    override fun getVideoInfo(date: String, num: Int) {
        val map = HashMap<String, String>()
        map["num"] = "2"
        if (!TextUtils.isEmpty(date)) {
            map["date"] = date
        }
        request(ApiManager.ApiFactory.createVideoApi().getVideoList(map), object : LoadTaskCallback<VideoResponse> {
            override fun onTaskLoaded(data: VideoResponse) {
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