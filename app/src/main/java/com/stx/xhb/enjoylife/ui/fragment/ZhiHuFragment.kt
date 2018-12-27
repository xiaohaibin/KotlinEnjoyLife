package com.stx.xhb.enjoylife.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.stx.xhb.core.base.BaseFragment
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.data.entity.ZhiHuNewsResponse
import com.stx.xhb.enjoylife.mvp.contract.GetZhiHuNewsContract
import com.stx.xhb.enjoylife.mvp.presenter.GetZhiHuNewsPresenter
import com.stx.xhb.enjoylife.ui.adapter.NewsAdapter

/**
 * @author: xiaohaibin.
 * @time: 2018/6/29
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:知乎日报
 */
class ZhiHuFragment : BaseFragment(), GetZhiHuNewsContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var mReclyView: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var date = ""
    /**是否是第一次加载数据*/
    private var isFirstLoad = false
    private var mNewsAdapter: NewsAdapter? = null
    private var zhiHuNewsPresenter: GetZhiHuNewsPresenter? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_common
    }

    override fun initView() {
        mReclyView = getView(R.id.recly_view)
        mSwipeRefreshLayout = getView(R.id.refresh_layout)
        mReclyView?.setLayoutManager(LinearLayoutManager(activity))
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary)
        mNewsAdapter = NewsAdapter(R.layout.list_item_news)
        mSwipeRefreshLayout?.setOnRefreshListener(this)

        mReclyView?.adapter = mNewsAdapter
        mNewsAdapter?.openLoadAnimation()
    }

    override fun initData() {
        zhiHuNewsPresenter=GetZhiHuNewsPresenter(this)
    }

    override fun onVisible() {
        super.onVisible()
        mSwipeRefreshLayout?.setRefreshing(true)
        onRefresh()
    }

    private fun onLoadMore() {
        if (!TextUtils.isEmpty(date)) {
            isFirstLoad = false
            zhiHuNewsPresenter?.getNewsBefore(date)
        }
    }

    private fun onLoadComplete() {
        if ("latest".equals(date,true)) {
            mNewsAdapter?.setNewData(null)
            mSwipeRefreshLayout?.setRefreshing(false)
        } else {
            mNewsAdapter?.loadMoreComplete()
        }
    }

    override fun setListener() {
        mNewsAdapter?.setOnLoadMoreListener({ onLoadMore() }, mReclyView)
    }

    override fun onResponse(zhiHuNewsResponse: ZhiHuNewsResponse) {
        onLoadComplete()
        mNewsAdapter?.addData(zhiHuNewsResponse.stories)
        date = zhiHuNewsResponse.date
        if (isFirstLoad) {
            onLoadMore()
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMsg(msg: String) {
        showToast(msg)
        onLoadComplete()
    }

    override fun onRefresh() {
        date = "latest"
        isFirstLoad = true
        zhiHuNewsPresenter?.getNews(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSwipeRefreshLayout?.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        zhiHuNewsPresenter?.destory()
    }
}