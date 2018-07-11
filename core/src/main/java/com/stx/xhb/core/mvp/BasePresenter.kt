package com.stx.xhb.core.mvp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * @author: xiaohaibin.
 * @time: 2018/7/3
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
open class BasePresenter<T, V : IBaseView> : IPresenter<V> {

    private var mView: V? = null
    private var mResponseCall: Call<T>? = null

    override fun attachView(mvpView: V) {
        this.mView = mvpView
    }

    override fun detachView() {
        this.mView = null
        if (mResponseCall != null) {
            mResponseCall!!.cancel()
        }
    }

    protected fun request(call: Call<T>, callback: LoadTaskCallback<T>) {
        this.mResponseCall = call
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    callback.onTaskLoaded(response.body() as T)
                } else {
                    callback.onDataNotAvailable(response.message())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onDataNotAvailable(t.message!!)
            }
        })
    }

    fun isViewBind(): Boolean {
        return mView != null
    }

    fun getView(): V? {
        return mView
    }

    interface LoadTaskCallback<T> {

        fun onTaskLoaded(data: T)

        fun onDataNotAvailable(msg: String)
    }

}