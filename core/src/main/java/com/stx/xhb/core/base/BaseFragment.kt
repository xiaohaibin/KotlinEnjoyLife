package com.stx.xhb.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stx.xhb.core.rx.RxFragment

/**
 * @author: xiaohaibin.
 * @time: 2018/6/26
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: BaseFragment
 */
abstract class BaseFragment: RxFragment(){

    protected var rootView: View? = null
    protected var mContext: Context? = null
    /**是否可见*/
    protected var isViable = false

    /**标志位，标志Fragment已经初始化完成*/
    protected var isPrepared = false

    /**标记已加载完成，保证懒加载只能加载一次*/
    protected var hasLoaded = false

    protected abstract fun getLayoutResource(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun setListener()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (getLayoutResource() != 0) {
            rootView = inflater!!.inflate(getLayoutResource(), null)
        } else {
            rootView = super.onCreateView(inflater, container, savedInstanceState)
        }
        initData()
        initView()
        setListener()
        return (rootView as View?)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity;
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isPrepared && userVisibleHint) {
            onFragmentVisibleChange(true)
            isViable = true
        }
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (rootView == null) {
            return
        }
        isPrepared = true
        if (isVisibleToUser) {
            onFragmentVisibleChange(true)
            isViable = true
            return
        }

        if (isViable) {
            onFragmentVisibleChange(false)
            isViable = false
        }
    }

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * @param isVisible
     */
    protected fun onFragmentVisibleChange(isVisible: Boolean) {
        if (isVisible) {
            onVisible()
        } else {
            onInVisible()
        }

    }

    /**
     * 当界面可见时的操作
     */
    protected fun onVisible() {
        if (hasLoaded) {
            return
        }
        lazyLoad()
        hasLoaded = true
    }


    /**
     * 数据懒加载
     */
    protected fun lazyLoad() {

    }

    /**
     * 当界面不可见时的操作
     */
    protected fun onInVisible() {

    }


}