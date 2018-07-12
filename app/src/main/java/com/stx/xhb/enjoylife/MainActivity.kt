package com.stx.xhb.enjoylife


import android.content.Intent
import android.os.Build
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.transition.Slide
import android.view.Gravity
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.enjoylife.config.Config
import com.stx.xhb.enjoylife.ui.fragment.WallPaperFragment
import com.stx.xhb.enjoylife.ui.fragment.VideoFragment
import com.stx.xhb.enjoylife.ui.fragment.ZhiHuFragment
import com.stx.xhb.enjoylife.ui.activity.AboutActivity
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {

    var toolbar: Toolbar? = null
    var ctlMain: CoordinatorLayout? = null
    var navView: NavigationView? = null
    var drawerLayout: DrawerLayout? = null
    var mFragments: ArrayList<android.app.Fragment>? = null
    var mCurrentFragment: android.app.Fragment? = null
    var mTitles: ArrayList<Int>? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        toolbar = findViewById(R.id.toolbar) as Toolbar
        ctlMain = findViewById(R.id.ctl_main) as CoordinatorLayout
        navView = findViewById(R.id.nav_view) as NavigationView
        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        setSupportActionBar(toolbar)
        ctlMain!!.fitsSystemWindows = false
        toolbar?.let { setToolBar(it, true, false, drawerLayout) }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            val toggle = ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawerLayout!!.addDrawerListener(toggle)
            toggle.syncState()
        }
        initMenu()
    }

    override fun initData() {

    }

    override fun setListener() {
        navView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            val id = it.getItemId()
            if (id < mFragments!!.size) {
                switchFragment(mFragments!!.get(id), getString(mTitles!!.get(id)))
            }
            when (id) {
                R.id.nav_about -> startActivity(Intent(this, AboutActivity::class.java))
            }
            val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
            true;
        })
    }

    fun initMenu() {
        val savedChannelList = ArrayList<Config.Channel>()
        mTitles = ArrayList()
        mFragments = ArrayList()
        val menu = navView!!.getMenu()
        menu.clear()
        Collections.addAll<Config.Channel>(savedChannelList, *Config.Channel.values())
        for (i in savedChannelList.indices) {
            val menuItem = menu.add(0, i, 0, savedChannelList[i].title)
            mTitles!!.add(savedChannelList[i].title)
            menuItem.setIcon(savedChannelList[i].icon)
            menuItem.isCheckable = true
            addFragment(savedChannelList[i].name)
            if (i == 0) {
                menuItem.isChecked = true
            }
        }
        navView!!.inflateMenu(R.menu.activity_main_drawer)
        switchFragment(mFragments!!.get(0), getString(mTitles!!.get(0)))
    }

    /**
     * 添加Fragment
     * @param name
     */
    private fun addFragment(name: String) {
        when (name) {
            Config.ZHIHU -> mFragments!!.add(ZhiHuFragment())
            Config.VIDEO -> mFragments!!.add(VideoFragment())
            Config.WALLPAPER -> mFragments!!.add(WallPaperFragment())
        }
    }


    /**
     * Fragment切换
     * @param fragment
     * @param title
     */
    private fun switchFragment(fragment: android.app.Fragment, title: String) {
        val slideTransition: Slide
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Gravity.START部分机型崩溃java.lang.IllegalArgumentException: Invalid slide direction
            slideTransition = Slide(Gravity.LEFT)
            slideTransition.duration = 700
            fragment.enterTransition = slideTransition
            fragment.exitTransition = slideTransition
        }
        if (mCurrentFragment == null || !(mCurrentFragment!!::class.java.name).equals(fragment::class.java.name)) {
            fragmentManager.beginTransaction().replace(R.id.replace, fragment).commit()
            mCurrentFragment = fragment
            val actionBar = supportActionBar!!
            actionBar.setTitle(title)
        }
    }
}
