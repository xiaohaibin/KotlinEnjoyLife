package com.stx.xhb.core.base

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.jaeger.library.StatusBarUtil
import com.stx.xhb.core.R
import com.stx.xhb.core.rx.RxAppCompatActivity
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import java.util.*
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION


/**
 * @author: xiaohaibin.
 * @time: 2018/6/25
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:BaseActivity
 */
abstract class BaseActivity : RxAppCompatActivity() {

    protected abstract fun getLayoutResource(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun setListener()
    //权限相关
    private val TAG = "PermissionsUtil"
    private var REQUEST_CODE_PERMISSION = 0x00099

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorGreen))
        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource())
        }
        initView()
        initData()
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

    /**
     * 请求权限
     *
     *
     * 警告：此处除了用户拒绝外，唯一可能出现无法获取权限或失败的情况是在AndroidManifest.xml中未声明权限信息
     * Android6.0+即便需要动态请求权限（重点）但不代表着不需要在AndroidManifest.xml中进行声明。
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    fun requestPermission(permissions: Array<String>, requestCode: Int) {
        this.REQUEST_CODE_PERMISSION = requestCode
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION)
        } else {
            val needPermissions = getDeniedPermissions(permissions)
            ActivityCompat.requestPermissions(this, needPermissions.toTypedArray(), REQUEST_CODE_PERMISSION)
        }
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    fun checkPermissions(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private fun getDeniedPermissions(permissions: Array<String>): List<String> {
        val needRequestPermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission)
            }
        }
        return needRequestPermissionList
    }


    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION)
            } else {
                permissionFail(REQUEST_CODE_PERMISSION)
                showTipsDialog()
            }
        }
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private fun verifyPermissions(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 显示提示对话框
     */
    fun showTipsDialog() {
        AlertDialog.Builder(this)
                .setTitle("警告")
                .setMessage("需要必要的权限才可以正常使用该功能，您已拒绝获得该权限。\n" +
                        "如果需要重新授权，您可以点击“允许”按钮进入系统设置进行授权")
                .setNegativeButton("取消") { dialog, i ->
                    dialog.dismiss()
                }
                .setPositiveButton("确定") { dialogInterface, i ->
                    startAppSettings()
                }
                .show()
    }

    /**
     * 权限获取失败
     *
     * @param requestCode
     */
    fun permissionFail(requestCode: Int) {
        Log.d(TAG, "获取权限失败=$requestCode")
    }

    /**
     * 启动当前应用设置页面
     */
    private fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    fun permissionSuccess(requestCode: Int) {
        Log.d(TAG, "获取权限成功=$requestCode")
    }

    private var mCompositeSubscription: CompositeSubscription? = null


    fun getCompositeSubscription(): CompositeSubscription {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }

        return this.mCompositeSubscription!!
    }


    fun addSubscription(s: Subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }

        this.mCompositeSubscription!!.add(s)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription?.unsubscribe()
        }
    }

    fun showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }

}