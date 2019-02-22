package com.stx.xhb.enjoylife.ui.activity

import android.Manifest
import android.os.Environment
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.core.utils.RxImage
import com.stx.xhb.core.utils.ShareUtils
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.ui.adapter.PhotoViewPagerAdapter
import rx.android.schedulers.AndroidSchedulers
import java.io.File
import java.util.*

/**
 * @author: xiaohaibin.
 * @time: 2018/7/11
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class PhotoViewActivity : BaseActivity() {

    val PERMISS_REQUEST_CODE = 0x001
    private var imageList: ArrayList<String>? = null
    private var mPos: Int = 0
    private var saveImgUrl = ""
    private var photoViewpager: ViewPager? = null
    private var mTvIndicator: TextView? = null
    private var toolbar: Toolbar? = null

    companion object {
        val TRANSIT_PIC = "transit_img"
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_photo_view
    }

    override fun initView() {
        photoViewpager = findViewById(R.id.photo_viewpager)
        mTvIndicator = findViewById(R.id.tv_indicator)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setTransitionName(photoViewpager, PhotoViewActivity.TRANSIT_PIC)

    }

    override fun initData() {
        imageList = intent.getStringArrayListExtra("image")
        mPos = intent.getIntExtra("pos", 0)
        mTvIndicator?.text = ((mPos + 1).toString() + "/" + imageList?.size)
        setAdapter()
    }

    override fun setListener() {
        photoViewpager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                saveImgUrl = photoViewpager?.currentItem?.let { imageList?.get(it) }!!
                mTvIndicator?.text = ((photoViewpager?.getCurrentItem()!! + 1).toString() + "/" + imageList?.size)
            }
        })
        toolbar?.setNavigationOnClickListener({ onBackPressed() })
    }

    private fun setAdapter() {
        val adapter = imageList?.let { PhotoViewPagerAdapter(this, it) }
        photoViewpager?.adapter = adapter
        photoViewpager?.currentItem = mPos

        adapter?.setOnClickListener(object : PhotoViewPagerAdapter.onImageLayoutListener {
            override fun setOnImageOnClik() {
                onBackPressed()
            }

            override fun setLongClick(url: String) {
                AlertDialog.Builder(this@PhotoViewActivity)
                        .setMessage(getString(R.string.ask_saving_picture))
                        .setNegativeButton(android.R.string.cancel) { dialog, which -> dialog.dismiss() }
                        .setPositiveButton(android.R.string.ok) { dialog, which ->
                            if (checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))) {
                                saveImage()
                            } else {
                                requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISS_REQUEST_CODE)
                            }
                            dialog.dismiss()
                        }
                        .show()
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
                if (checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_WALLPAPER))) {
                    setWallpaper()
                } else {
                    requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_WALLPAPER), PERMISS_REQUEST_CODE)
                }
                return true
            }
            R.id.menu_share -> {
                if (checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))){
                    val subscribe = RxImage.saveImageAndGetPathObservable(this, saveImgUrl)
                            .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribe({ uri ->
                                ShareUtils.shareImage(this@PhotoViewActivity, uri, getString(R.string.share_image_to))
                            }, { throwable -> showToast(throwable.message.toString()) })
                    addSubscription(subscribe)
                }else{
                    requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISS_REQUEST_CODE)
                }
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
                }, {
                    showToast("壁纸设置失败")
                })
        addSubscription(subscribe)
    }
}