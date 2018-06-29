package com.stx.xhb.core.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.Window
import com.jaeger.library.StatusBarUtil
import com.stx.xhb.core.R
import com.stx.xhb.core.rx.RxAppCompatActivity

/**
 * @author: xiaohaibin.
 * @time: 2018/6/25
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:BaseActivity
 */
abstract class  BaseActivity: RxAppCompatActivity() {

    protected abstract fun getLayoutResource(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun setListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorGreen))
        if (getLayoutResource()!=0){
            setContentView(getLayoutResource())
        }
        initData()
        initView()
        setListener()
    }


    fun setToolBar(toolbar: Toolbar, isChangeToolbar: Boolean, isChangeStatusBar: Boolean, drawerLayout: DrawerLayout?) {
        val vibrantColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Color.BLACK
        }
        if (isChangeToolbar) {
            toolbar.setBackgroundColor(vibrantColor)
        }
        if (isChangeStatusBar) {
            StatusBarUtil.setColor(this, vibrantColor)
        }
        if (drawerLayout != null) {
            StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, vibrantColor)
        }
    }




}