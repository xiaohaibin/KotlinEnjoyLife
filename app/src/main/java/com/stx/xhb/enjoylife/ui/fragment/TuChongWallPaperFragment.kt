package com.stx.xhb.enjoylife.ui.fragment

import com.stx.xhb.core.base.BaseFragment
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.data.entity.Feed
import com.stx.xhb.enjoylife.mvp.contract.GetWallPaperContract
import com.stx.xhb.enjoylife.mvp.presenter.GetWallPaperPresenter

/**
 * @author: xiaohaibin.
 * @time: 2018/6/29
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: 图虫摄影
 */
class TuChongWallPaperFragment:BaseFragment(),GetWallPaperContract.View{

    var getWallPaperPresenter:GetWallPaperPresenter?=null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_common
    }

    override fun initView() {

    }

    override fun initData() {
        getWallPaperPresenter = GetWallPaperPresenter(this)
        getWallPaperPresenter?.getWallPaper(1)
    }

    override fun setListener() {

    }

    override fun onResponse(feedList: List<Feed>, isMore: Boolean) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMsg(msg: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        getWallPaperPresenter?.destory()
    }
}