package com.stx.xhb.enjoylife.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import com.jaeger.library.StatusBarUtil
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.enjoylife.MainActivity
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.config.GlideApp

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class SplashActivity : BaseActivity() {

    /**动画时间 1000ms */
    private val ANIMATOR_DURATION = 2000
    private var alphaAnimIn: ObjectAnimator? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //设置状态栏透明
        StatusBarUtil.setTranslucent(this, 0)
        val ivSplash = findViewById<ImageView>(R.id.iv_splash)
        val splashView = findViewById<FrameLayout>(R.id.splash_view)
//        GlideApp.with(this).load("https://pic1.zhimg.com/v2-9639852750175df1b80ed995729e64e8.jpg").dontAnimate().into(ivSplash)
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
    }

    override fun setListener() {
    }
}