package com.stx.xhb.enjoylife.ui.activity

import android.Manifest
import android.graphics.Color
import android.os.Environment
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jaeger.library.StatusBarUtil
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.core.utils.DateUtils
import com.stx.xhb.core.utils.RxImage
import com.stx.xhb.core.utils.ShareUtils
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.ui.adapter.WallPaperPagerAdapter
import rx.android.schedulers.AndroidSchedulers
import java.io.File
import java.util.*

/**
 * @author: xiaohaibin.
 * @time: 2018/7/11
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:WallPagerActivity
 */
class WallPagerActivity : BaseActivity() {

    val PERMISS_REQUEST_CODE = 0x001
    private var imageList: ArrayList<String>? = null
    private var mPos: Int = 0
    private var saveImgUrl = ""
    private var photoViewpager: ViewPager? = null

    companion object {
        val TRANSIT_PIC = "transit_img"
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_wall_pager
    }

    override fun initView() {
        photoViewpager = findViewById(R.id.photo_viewpager)
        findViewById<TextView>(R.id.tv_time).text = DateUtils.getCurrentHourAndMinute()
        findViewById<TextView>(R.id.tv_current_date).text = DateUtils.getCurrentMouthDayWeek()
        findViewById<TextView>(R.id.tv_lunar).text = DateUtils.getLunarYearMonthDay()
        findViewById<TextView>(R.id.btn_save).setOnClickListener(View.OnClickListener {
            saveImage()
        })
        findViewById<TextView>(R.id.btn_set_wallpaper).setOnClickListener(View.OnClickListener {
            setWallpaper()
        })
        findViewById<ImageView>(R.id.iv_close).setOnClickListener(View.OnClickListener {
           onBackPressed()
        })
        photoViewpager?.pageMargin = (resources.displayMetrics.density * 15).toInt()
        ViewCompat.setTransitionName(photoViewpager, PhotoViewActivity.TRANSIT_PIC)
        StatusBarUtil.setColor(this, Color.BLACK)
        hideBottomUIMenu()
    }

    override fun initData() {
        imageList = intent.getStringArrayListExtra("image")
        mPos = intent.getIntExtra("pos", 0)
        setAdapter()
    }

    override fun setListener() {
        photoViewpager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                saveImgUrl = photoViewpager?.currentItem?.let { imageList?.get(it) }!!
            }
        })
    }

    private fun setAdapter() {
        val adapter = imageList?.let { WallPaperPagerAdapter(this, it) }
        photoViewpager?.adapter = adapter
        photoViewpager?.currentItem = mPos
        adapter?.setOnClickListener(object : WallPaperPagerAdapter.onImageLayoutListener {
            override fun setOnImageOnClik() {

            }

            override fun setLongClick(url: String) {

            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PERMISS_REQUEST_CODE == requestCode) {
            if (checkPermissions(permissions)) {
                saveImage()
            } else {
                showTipsDialog()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_more, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                if (checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))) {
                    saveImage()
                } else {
                    requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISS_REQUEST_CODE)
                }
                return true
            }
            R.id.menu_setting_picture -> {
                setWallpaper()
                return true
            }
            R.id.menu_share -> {
                val subscribe = RxImage.saveImageAndGetPathObservable(this, saveImgUrl)
                        .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe({ uri -> ShareUtils.shareImage(this@WallPagerActivity, uri, getString(R.string.share_image_to)) }, { throwable -> showToast(throwable.message.toString()) })
                addSubscription(subscribe)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * 保存图片
     */
    private fun saveImage() {
        val subscribe = RxImage.saveImageAndGetPathObservable(this, saveImgUrl)
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe({
                    val appDir = File(Environment.getExternalStorageDirectory(), "EnjoyLife")
                    val msg = String.format(getString(R.string.picture_has_save_to),
                            appDir.absolutePath)
                    showToast(msg)
                }, {
                    showToast(getString(R.string.string_img_save_failed))
                })
        addSubscription(subscribe)
    }


    /**
     * 设置壁纸
     */
    private fun setWallpaper() {
        val subscribe = RxImage.setWallPaper(this, saveImgUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showToast("壁纸设置成功")
                }, {
                    showToast("壁纸设置失败")
                })
        addSubscription(subscribe)
    }
}