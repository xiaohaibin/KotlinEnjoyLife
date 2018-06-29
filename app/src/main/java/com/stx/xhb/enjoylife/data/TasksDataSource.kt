package com.stx.xhb.enjoylife.data

/**
 * @author: xiaohaibin.
 * @time: 2018/6/25
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
interface TasksDataSource {

    interface LoadTaskCallback<T> {

        fun onTaskLoaded(data: T)

        fun onDataNotAvailable(msg: String)
    }

    /**
     * 释放资源
     */
    abstract fun release()


}