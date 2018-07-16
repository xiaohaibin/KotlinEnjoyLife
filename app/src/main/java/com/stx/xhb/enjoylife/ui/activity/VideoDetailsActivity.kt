package com.stx.xhb.enjoylife.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.core.utils.NetworkUtil
import com.stx.xhb.core.utils.ShareUtils
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.config.GlideApp

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class VideoDetailsActivity : BaseActivity(), View.OnClickListener {

    private var video: String? = null
    private var title: String? = null
    private var mFeed: String? = null
    private var videoDetailIv: ImageView? = null
    private var videoDetailIvmo: ImageView? = null
    private var videoDetailTitle: TextView? = null
    private var videoDetailTime: TextView? = null
    private var videoDetailDesc: TextView? = null
    private var toolbar: Toolbar? = null
    private var articleShare: ImageButton? = null
    private var videoPaly:ImageView?=null

    companion object {
        val TRANSIT_PIC = "transit_picture"
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_video_detail
    }

    override fun initView() {
        videoDetailIv = findViewById(R.id.video_detail_iv)
        videoDetailIvmo = findViewById(R.id.video_detail_ivmo)
        videoDetailTitle = findViewById(R.id.video_detail_title)
        videoDetailTime = findViewById(R.id.video_detail_time)
        videoDetailDesc = findViewById(R.id.video_detail_desc)
        videoPaly=findViewById(R.id.video_paly)
        articleShare = findViewById(R.id.article_share)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "视频详情"
        toolbar?.setNavigationOnClickListener({ finish() })
        ViewCompat.setTransitionName(videoDetailIv, VideoDetailsActivity.TRANSIT_PIC)
    }

    override fun initData() {
        //背景图片
        mFeed = intent.getStringExtra("feed")
        title = intent.getStringExtra("title")
        val time = intent.getStringExtra("time")//时间
        val desc = intent.getStringExtra("desc")//视频详情
        val blurred = intent.getStringExtra("blurred")//模糊图片
        video = intent.getStringExtra("video")//视频播放地址
        //给控件设置数据
        videoDetailIv?.let { GlideApp.with(this).load(mFeed).diskCacheStrategy(DiskCacheStrategy.ALL).transition(DrawableTransitionOptions.withCrossFade()).into(it) }
        videoDetailTitle?.text = title
        videoDetailTime?.text = time
        videoDetailDesc?.text = desc
        videoDetailIvmo?.let { GlideApp.with(this).load(blurred).diskCacheStrategy(DiskCacheStrategy.ALL).transition(DrawableTransitionOptions.withCrossFade()).into(it) }
    }

    override fun setListener() {
        videoPaly?.setOnClickListener(this)
        articleShare?.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.getId()) {
            R.id.video_paly ->
                if (NetworkUtil.isInternetConnection(this)) {
                val intent = Intent(this, VideoPlayActivity::class.java)
                val bundle = Bundle()
                bundle.putString(VideoPlayActivity.VIDEO_URL, video)
                bundle.putString(VideoPlayActivity.VIDEO_TITLE, title)
                bundle.putString(VideoPlayActivity.VIDEO_IMAGE, mFeed)
                bundle.putBoolean(VideoPlayActivity.TRANSITION, true)
                intent.putExtra("video", bundle)
                startActivity(intent)
            } else {
                showToast("网络异常，请稍后再试")
            }
            R.id.article_share -> if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(video)) {
                ShareUtils.share(this, "$title $video(分享自开眼视频)")
            }
        }
    }
}