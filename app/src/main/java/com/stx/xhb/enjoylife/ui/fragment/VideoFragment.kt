package com.stx.xhb.enjoylife.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.stx.xhb.core.base.BaseFragment
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.data.entity.Item
import com.stx.xhb.enjoylife.data.entity.VideoResponse
import com.stx.xhb.enjoylife.mvp.contract.GetVideoContract
import com.stx.xhb.enjoylife.mvp.presenter.GetVideoPresenter
import com.stx.xhb.enjoylife.ui.activity.VideoDetailsActivity
import com.stx.xhb.enjoylife.ui.adapter.VideoRecyclerAdapter
import com.stx.xhb.enjoylife.ui.adapter.provider.VideoItemProvider
import java.util.*

/**
 * @author: xiaohaibin.
 * @time: 2018/6/29
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:视频推荐
 */
class VideoFragment : BaseFragment(), GetVideoContract.getVideoView {

    private var mReclyView: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var list: MutableList<Item>? = null
    private var nextPublishTime = ""
    private var mRecyclerAdapter: VideoRecyclerAdapter? = null
    private var isRefresh: Boolean = false
    private var getVideoPresenter: GetVideoPresenter? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_common
    }

    override fun initView() {
        mReclyView = getView(R.id.recly_view)
        mSwipeRefreshLayout = getView(R.id.refresh_layout)
        mReclyView?.layoutManager = LinearLayoutManager(activity)
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary)
    }

    override fun initData() {
        list = ArrayList()
        getVideoPresenter = GetVideoPresenter(this)
        setLvAdapter()
    }

    override fun setListener() {
        mSwipeRefreshLayout?.setOnRefreshListener { onRefreshData() }
        mRecyclerAdapter?.setItemClickListener(object : VideoItemProvider.setOnItemClickListener {
            override fun onItemClick(view: View, data: Item) {
                val intent = Intent(activity, VideoDetailsActivity::class.java)
                val bundle = Bundle()
                val dataEntity = data.data
                if (!"video".equals(data.type, true)) {
                    return
                }
                bundle.putString("title", dataEntity.title)
                //获取到时间
                val duration = dataEntity.duration
                val mm = duration / 60//分
                val ss = duration % 60//秒
                var second = ""//秒
                var minute = ""//分
                if (ss < 10) {
                    second = "0" + ss.toString()
                } else {
                    second = ss.toString()
                }
                if (mm < 10) {
                    minute = "0" + mm.toString()
                } else {
                    minute = mm.toString()//分钟
                }
                bundle.putString("time", "#" + dataEntity.category + " / " + minute + "'" + second + '"')
                bundle.putString("desc", dataEntity.description)//视频描述
                bundle.putString("blurred", dataEntity.cover.blurred)//模糊图片地址
                bundle.putString("feed", dataEntity.cover.feed)//图片地址
                bundle.putString("video", dataEntity.playUrl)//视频播放地址
                bundle.putInt("collect", dataEntity.consumption.collectionCount)//收藏量
                bundle.putInt("share", dataEntity.consumption.shareCount)//分享量
                bundle.putInt("reply", dataEntity.consumption.replyCount)//回复数量
                intent.putExtras(bundle)

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, view, VideoDetailsActivity.TRANSIT_PIC)
                try {
                    ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                    startActivity(intent)
                }

            }
        })
    }

    override fun onVisible() {
        super.onVisible()
        onRefreshData()
    }

    private fun setLvAdapter() {
        mRecyclerAdapter = VideoRecyclerAdapter(list)
        mReclyView?.setAdapter(mRecyclerAdapter)
        mRecyclerAdapter?.openLoadAnimation()
        mRecyclerAdapter?.setOnLoadMoreListener({ onLoadMore() }, mReclyView)
    }

    override fun onResponse(response: VideoResponse) {
        val issueList = response.issueList
        if (issueList.isEmpty()) {
            return
        }
        //刷新需要清除数据
        if (isRefresh) {
            list?.clear()
            mSwipeRefreshLayout?.setRefreshing(false)
        } else {
            mRecyclerAdapter?.loadMoreComplete()
        }
        for (i in issueList.indices) {
            list?.addAll(issueList.get(i).itemList)
        }
        val nextUrl = response.nextPageUrl
        nextPublishTime = Uri.parse(nextUrl).getQueryParameter("date")
        mRecyclerAdapter?.notifyDataSetChanged()
    }

    override fun showLoading() {
        if (isRefresh) {
            mSwipeRefreshLayout?.setRefreshing(true)
        }
    }

    override fun hideLoading() {
        if (isRefresh) {
            mSwipeRefreshLayout?.setRefreshing(false)
        }
        mRecyclerAdapter?.loadMoreFail()
    }

    override fun showMsg(msg: String) {
        if (isRefresh) {
            mSwipeRefreshLayout?.setRefreshing(false)
        } else {
            mRecyclerAdapter?.loadMoreComplete()
        }
        showToast(msg)
    }

    fun onRefreshData() {
        isRefresh = true
        mSwipeRefreshLayout?.setRefreshing(true)
        getVideoPresenter?.getVideoInfo("", 2)
    }

    fun onLoadMore() {
        isRefresh = false
        getVideoPresenter?.getVideoInfo(nextPublishTime, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSwipeRefreshLayout?.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        getVideoPresenter?.destory()
    }
}