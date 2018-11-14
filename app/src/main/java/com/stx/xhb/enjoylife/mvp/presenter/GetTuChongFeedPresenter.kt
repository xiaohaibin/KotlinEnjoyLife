package com.stx.xhb.enjoylife.mvp.presenter

import android.text.TextUtils
import com.stx.xhb.core.mvp.BasePresenter
import com.stx.xhb.enjoylife.data.entity.feed.TuChongFeedResponse
import com.stx.xhb.enjoylife.data.http.ApiManager
import com.stx.xhb.enjoylife.mvp.contract.GeTuChongFeedContract
import java.util.*

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class GetTuChongFeedPresenter(mvpView: GeTuChongFeedContract.View) : BasePresenter<TuChongFeedResponse, GeTuChongFeedContract.View>(), GeTuChongFeedContract.Model {

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

    override fun getFeed(page: Int, type: String, posId: String) {
        val map = HashMap<String, String>()
        map["page"] = page.toString()
        map["type"] = type
        if (!TextUtils.isEmpty(posId)) {
            map["post_id"] = posId
        }
        request(ApiManager.ApiFactory.createTuChongApi().getFeedApp(map), object : LoadTaskCallback<TuChongFeedResponse> {
            override fun onTaskLoaded(data: TuChongFeedResponse) {
                if (!data.feedList.isEmpty()) {
                    getView()?.onResponse(data.feedList, data.more)
                }
            }

            override fun onDataNotAvailable(msg: String) {
                getView()?.showMsg(msg)
            }
        })
    }
}