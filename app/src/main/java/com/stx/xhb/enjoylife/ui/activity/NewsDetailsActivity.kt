package com.stx.xhb.enjoylife.ui.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.core.utils.ScreenUtil
import com.stx.xhb.core.utils.ShareUtils
import com.stx.xhb.core.utils.WebHtmlUtil
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.data.entity.ZhiHuNewsContentResponse
import com.stx.xhb.enjoylife.mvp.contract.GetNewsContentContract
import com.stx.xhb.enjoylife.mvp.presenter.GetNewsContentPresenter

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class NewsDetailsActivity : BaseActivity(), GetNewsContentContract.View {

    private var webView: WebView? = null
    private var toolbar: Toolbar? = null
    private var mIvBanner: ImageView? = null
    private var mZhiHuNewsContentResponse: ZhiHuNewsContentResponse? = null
    private var mProgressDialog: ProgressDialog? = null
    private var getNewsContentPresenter: GetNewsContentPresenter? = null
    private val SHARE_FROM_ZHIHU = " 分享自知乎网"

    override fun getLayoutResource(): Int {
        return R.layout.activity_web_details
    }

    override fun initView() {
        webView = findViewById(R.id.web_webview)
        toolbar = findViewById(R.id.toolbar)
        mIvBanner = findViewById(R.id.iv_banner)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener { finish() }
        mIvBanner?.setLayoutParams(FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(this) / 2 + 70))
        initWeb()
    }

    override fun initData() {
        getNewsContentPresenter = GetNewsContentPresenter(this)
        val bundle = intent.extras
        if (bundle.containsKey("data")) {
            val data = bundle.getString("data")
            getNewsContentPresenter?.getNewsContent(data)
        }
        if (bundle.containsKey("title")) {
            val title = bundle.getString("title")
            supportActionBar?.title = title
        }
    }

    override fun setListener() {
        findViewById<ImageButton>(R.id.article_share).setOnClickListener {
            ShareUtils.share(this, mZhiHuNewsContentResponse?.title + " " + mZhiHuNewsContentResponse?.share_url + SHARE_FROM_ZHIHU)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWeb() {
        val settings = webView?.getSettings()
        settings?.defaultTextEncodingName = "utf-8"
        settings?.javaScriptEnabled = true
        settings?.setSupportZoom(true)
        settings?.builtInZoomControls = true
        settings?.useWideViewPort = true
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings?.loadWithOverviewMode = true
        //隐藏缩放控件
        settings?.displayZoomControls = false
        //解决HTTPS协议下出现的mixed content问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        settings?.cacheMode = WebSettings.LOAD_DEFAULT
        settings?.domStorageEnabled = true
        settings?.databaseEnabled = true
        settings?.setAppCachePath(cacheDir.path)
        settings?.setAppCacheEnabled(true)
    }

    private fun showWebdata(data: String, css: String) {
        if (webView != null) {
            webView?.loadData(WebHtmlUtil.htmlText(data, css), "text/html; charset=utf-8", "utf-8")
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (webView?.canGoBack()!!) {
            webView?.goBack()
        } else {
            finish()
        }
        return true
    }

    companion object {
        fun start(context: Context, data: String, title: String) {
            val bundle = Bundle()
            val intent = Intent(context, NewsDetailsActivity::class.java)
            bundle.putString("data", data)
            bundle.putString("title", title)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }


    override fun onResponse(response: ZhiHuNewsContentResponse) {
        mZhiHuNewsContentResponse = response
        if (!response.css.isEmpty()) {
            showWebdata(response.body, response.css.get(0))
        } else {
            showWebdata(response.body, "")
        }
        mIvBanner?.let { Glide.with(this).load(response.image).into(it) }
    }

    override fun onPause() {
        super.onPause()
        webView?.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        webView?.resumeTimers()
    }

    override fun showLoading() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.setCancelable(false)
        mProgressDialog?.setMessage("正在加载...")
        mProgressDialog?.show()
    }

    override fun hideLoading() {
        mProgressDialog?.dismiss()
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }
}