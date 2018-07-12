package com.stx.xhb.enjoylife.ui.activity

import android.annotation.TargetApi
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.listener.OnTransitionListener
import com.stx.xhb.enjoylife.listener.SampleVideoPlayListener
import com.stx.xhb.enjoylife.widget.CustomVideoView

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class VideoPlayActivity : BaseActivity() {

    companion object {
        val IMG_TRANSITION = "IMG_TRANSITION"
        val TRANSITION = "TRANSITION"
        val VIDEO_URL = "URL"
        val VIDEO_TITLE = "title"
        val VIDEO_IMAGE = "image"
    }

    private var mVideoPlayer: CustomVideoView? = null
    private var orientationUtils: OrientationUtils? = null
    private var isTransition: Boolean = false
    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    private var transition: Transition? = null
    private var mTitle: String? = null
    private var mUrl: String? = null
    private var mImage: String? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_play;
    }

    override fun initView() {
        StatusBarUtil.setTranslucent(this)
        mVideoPlayer = findViewById(R.id.video_player)
    }

    override fun initData() {
        val intent = intent
        if (intent.hasExtra("video")) {
            val bundle = intent.getBundleExtra("video")
            if (bundle.containsKey(TRANSITION)) {
                isTransition = bundle.getBoolean(TRANSITION)
            }
            if (bundle.containsKey(VIDEO_TITLE)) {
                mTitle = bundle.getString(VIDEO_TITLE)
            }
            if (bundle.containsKey(VIDEO_URL)) {
                mUrl = bundle.getString(VIDEO_URL)
            }
            if (bundle.containsKey(VIDEO_IMAGE)) {
                mImage = bundle.getString(VIDEO_IMAGE)
            }
        }
        setData()
    }

    override fun setListener() {

    }

    private fun setData() {
        //需要路径的
        mVideoPlayer?.setUp(mUrl, true, "")

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).load(mImage).into(imageView)
        mVideoPlayer?.setThumbImageView(imageView)

        //增加title
        mVideoPlayer?.titleTextView?.visibility = View.VISIBLE
        mVideoPlayer?.titleTextView?.text = mTitle
        mVideoPlayer?.titleTextView?.setSingleLine(true)
        mVideoPlayer?.titleTextView?.ellipsize = TextUtils.TruncateAt.END

        //设置返回键
        mVideoPlayer?.backButton?.visibility = View.VISIBLE

        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoPlayer)
        //初始化不打开外部的旋转
        orientationUtils?.setEnable(false)

        mVideoPlayer?.setIsTouchWiget(true)
        //关闭自动旋转
        mVideoPlayer?.setRotateViewAuto(false)
        mVideoPlayer?.setLockLand(false)
        mVideoPlayer?.setShowFullAnimation(false)
        mVideoPlayer?.setNeedLockFull(true)


        //设置全屏按键功能
        mVideoPlayer?.fullscreenButton?.setOnClickListener {
            orientationUtils?.resolveByClick()

            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoPlayer?.startWindowFullscreen(this@VideoPlayActivity, true, true)
        }

        mVideoPlayer?.setBottomProgressBarDrawable(resources.getDrawable(R.drawable.video_new_progress))
        mVideoPlayer?.setDialogVolumeProgressBar(resources.getDrawable(R.drawable.video_new_volume_progress_bg))
        mVideoPlayer?.setDialogProgressBar(resources.getDrawable(R.drawable.video_new_progress))
        mVideoPlayer?.setBottomShowProgressBarDrawable(resources.getDrawable(R.drawable.video_new_seekbar_progress),
                resources.getDrawable(R.drawable.video_new_seekbar_thumb))
        mVideoPlayer?.setDialogProgressColor(resources.getColor(R.color.colorAccent), -11)


        //设置返回按键功能
        mVideoPlayer?.backButton?.setOnClickListener { onBackPressed() }

        mVideoPlayer?.setStandardVideoAllCallBack(object : SampleVideoPlayListener() {
            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.setEnable(true)
                isPlay = true
            }

            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, *objects)
            }

            override fun onClickStartError(url: String, vararg objects: Any) {
                super.onClickStartError(url, *objects)
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.backToProtVideo()
            }
        })

        mVideoPlayer?.setLockClickListener(LockClickListener { view, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils?.setEnable(!lock)
        })
        //过渡动画
        initTransition()
    }

    private fun initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoPlayer, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            mVideoPlayer?.startPlayLogic()
        }
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()

        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onBackPressed()
        } else {
            finish()
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener(): Boolean {
        transition = window.sharedElementEnterTransition
        if (transition != null) {
            transition?.addListener(onTransitionListener)
            return true
        }
        return false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!mVideoPlayer?.isIfCurrentIsFullscreen()!!) {
                    mVideoPlayer?.startWindowFullscreen(this@VideoPlayActivity, true, true)
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (mVideoPlayer?.isIfCurrentIsFullscreen!!) {
                    StandardGSYVideoPlayer.backFromWindowFull(this)
                }
                if (orientationUtils != null) {
                    orientationUtils?.setEnable(true)
                }
            }
        }
    }

    private var onTransitionListener: OnTransitionListener = object : OnTransitionListener() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onTransitionEnd(transition: Transition) {
            super.onTransitionEnd(transition)
            mVideoPlayer?.startPlayLogic()
            transition.removeListener(this)
        }
    }

}