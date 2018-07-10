package com.stx.xhb.enjoylife.ui.activity

import android.content.Intent
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AlphaAnimation
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
class SplashActivity:BaseActivity() {

    internal var milliseconds = 500

    override fun getLayoutResource(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //设置状态栏透明
        StatusBarUtil.setTranslucent(this, 0)
        val splashView = findViewById<ImageView>(R.id.splash_view)
        GlideApp.with(this).load("https://pic1.zhimg.com/v2-9639852750175df1b80ed995729e64e8.jpg").dontAnimate().into(splashView)
        //设置动画
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = milliseconds.toLong()//设置动画持续时间
        alphaAnimation.fillAfter = true
        splashView.setAnimation(alphaAnimation)
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, milliseconds.toLong())

    }

    override fun initData() {

    }

    override fun setListener() {

    }
}