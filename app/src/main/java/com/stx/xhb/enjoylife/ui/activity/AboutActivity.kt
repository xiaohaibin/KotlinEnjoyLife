package com.stx.xhb.enjoylife.ui.activity

import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.core.utils.ShareUtils
import com.stx.xhb.enjoylife.BuildConfig
import com.stx.xhb.enjoylife.R

class AboutActivity : BaseActivity() {

    var tvVersion: TextView? = null
    var toolbar: Toolbar? = null
    var collapsingToolbar: CollapsingToolbarLayout? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_about;
    }

    override fun initView() {
        tvVersion = findViewById(R.id.tv_version) as TextView
        toolbar = findViewById(R.id.toolbar) as Toolbar
        collapsingToolbar = findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout

        tvVersion!!.setText("Version " + BuildConfig.VERSION_NAME)
        collapsingToolbar!!.setTitle(getString(R.string.app_name))

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.getItemId()) {
            R.id.menu_share -> {
                ShareUtils.share(this, R.string.share_text)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun initData() {

    }

    override fun setListener() {
        toolbar!!.setNavigationOnClickListener(View.OnClickListener { this@AboutActivity.onBackPressed() })
    }
}
