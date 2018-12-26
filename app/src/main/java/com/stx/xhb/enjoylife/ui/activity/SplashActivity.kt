package com.stx.xhb.enjoylife.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.text.TextUtils
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.jaeger.library.StatusBarUtil
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.core.utils.GsonUtil
import com.stx.xhb.core.utils.SPUtils
import com.stx.xhb.enjoylife.MainActivity
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.config.GlideApp
import com.stx.xhb.enjoylife.config.SpConstants
import com.stx.xhb.enjoylife.data.entity.SplashImgResponse
import com.stx.xhb.enjoylife.mvp.contract.GetWelconmeImgConTract
import com.stx.xhb.enjoylife.mvp.presenter.GetSplashImgPresenter

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class SplashActivity : BaseActivity(), GetWelconmeImgConTract.View {

    /**动画时间 1000ms */
    private val ANIMATOR_DURATION = 2000
    private var alphaAnimIn: ObjectAnimator? = null
    private var mSplashImgPresenter: GetSplashImgPresenter? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //设置状态栏透明
        StatusBarUtil.setTransparent(this)
        val ivSplash = findViewById<ImageView>(R.id.iv_splash)
        val splashView = findViewById<FrameLayout>(R.id.splash_view)
        if (TextUtils.isEmpty(getWelcomeImg())) {
            GlideApp.with(this).load(R.drawable.splash_bg).dontAnimate().into(ivSplash)
        } else {
            GlideApp.with(this).load(getWelcomeImg()).dontAnimate().into(ivSplash)
        }
        //设置动画
        alphaAnimIn = ObjectAnimator.ofFloat(splashView, "alpha", 0f, 1f)
        alphaAnimIn?.setDuration(ANIMATOR_DURATION.toLong())
        alphaAnimIn?.start()
        alphaAnimIn?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                jumpToMain()
            }
        })
    }

    private fun jumpToMain() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
        overridePendingTransition(R.anim.splash_fade_in, R.anim.splash_fade_out)
    }

    override fun onStop() {
        super.onStop()
        alphaAnimIn?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        alphaAnimIn?.cancel()
        alphaAnimIn?.removeAllListeners()
        alphaAnimIn = null
    }

    override fun initData() {
        mSplashImgPresenter = GetSplashImgPresenter(this)
        val widthPixels = resources.displayMetrics.widthPixels
        val heightPixels = resources.displayMetrics.heightPixels
        mSplashImgPresenter?.getSplashImg(widthPixels.toString() + "*" + heightPixels.toString(), widthPixels, heightPixels)
    }

    override fun setListener() {

    }

    override fun getSplashImgSuccess(data: SplashImgResponse) {
        SPUtils.putString(this, SpConstants.SP_WELCOME_IMG, GsonUtil.newGson().toJson(data, SplashImgResponse::class.java))
    }


    override fun getSplashImgFailed(msg: String) {
        showMsg(msg)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun getWelcomeImg(): String? {
        val data = SPUtils.getString(this, SpConstants.SP_WELCOME_IMG)
        if (!TextUtils.isEmpty(data)) {
            val imgResponse = GsonUtil.newGson().fromJson(data, SplashImgResponse::class.java)
            if (imgResponse.app.isNotEmpty() && !TextUtils.isEmpty(imgResponse.app.get(0).title)) {
                return imgResponse.app.get(0).image_url
            }
        }
        return ""
    }
}