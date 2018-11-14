package com.stx.xhb.enjoylife.ui.fragment

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.stx.xhb.core.base.BaseFragment
import com.stx.xhb.core.widget.RecyclerViewNoBugStaggeredGridLayoutManger
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.data.entity.feed.FeedListBean
import com.stx.xhb.enjoylife.mvp.contract.GeTuChongFeedContract
import com.stx.xhb.enjoylife.mvp.presenter.GetTuChongFeedPresenter
import com.stx.xhb.enjoylife.ui.activity.PhotoViewActivity
import com.stx.xhb.enjoylife.ui.adapter.TuChongFeedAdapter
import java.util.*

/**
 * @author: xiaohaibin.
 * @time: 2018/11/14
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: 图虫摄影
 */
class TuChongFeedFragment:BaseFragment(), GeTuChongFeedContract.View , SwipeRefreshLayout.OnRefreshListener{

    var getWallPaperPresenter: GetTuChongFeedPresenter? = null
    private var mRvTuChong: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var page = 1
    private var posId = ""
    private var mTuChongListAdapter: TuChongFeedAdapter? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_common
    }

    override fun initView() {
        mRvTuChong = getView(R.id.recly_view)
        mSwipeRefreshLayout = getView(R.id.refresh_layout)
        val layoutManager = RecyclerViewNoBugStaggeredGridLayoutManger(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary)
        mRvTuChong?.layoutManager = layoutManager
        mTuChongListAdapter = TuChongFeedAdapter(R.layout.list_item_tuchong)
        mTuChongListAdapter?.openLoadAnimation()
        mRvTuChong?.adapter = mTuChongListAdapter
    }

    override fun initData() {
        getWallPaperPresenter = GetTuChongFeedPresenter(this)
    }

    override fun setListener() {
        mTuChongListAdapter?.setOnImageItemClickListener(object : TuChongFeedAdapter.OnImageItemClickListener {
            override fun setOnImageClick(view: View, imageList: ArrayList<String>) {
                val intent = Intent(mContext, PhotoViewActivity::class.java)
                intent.putStringArrayListExtra("image", imageList)
                intent.putExtra("pos", 0)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, view, PhotoViewActivity.TRANSIT_PIC)
                try {
                    ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                    startActivity(intent)
                }

            }
        })
        mSwipeRefreshLayout?.setOnRefreshListener(this)
        mTuChongListAdapter?.setOnLoadMoreListener({
            page++
            getWallPaperPresenter?.getFeed(page,"loadmore",posId)
        }, mRvTuChong)
    }

    override fun onResponse(feedList: List<FeedListBean>, isMore: Boolean) {
        onLoadComplete(page)
        mTuChongListAdapter?.setEnableLoadMore(isMore)
        posId = feedList[feedList.size - 1].post_id.toString()
        mTuChongListAdapter?.addData(feedList)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onVisible() {
        super.onVisible()
        mSwipeRefreshLayout?.setRefreshing(true)
        onRefresh()
    }

    override fun showMsg(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        page = 1
        getWallPaperPresenter?.getFeed(page,"refresh",posId)
    }

    override fun onDestroy() {
        super.onDestroy()
        getWallPaperPresenter?.destory()
    }

    private fun onLoadComplete(page: Int) {
        if (page == 1) {
            mTuChongListAdapter?.setNewData(null)
            mSwipeRefreshLayout?.setRefreshing(false)
        } else {
            mTuChongListAdapter?.loadMoreComplete()
        }
    }
}