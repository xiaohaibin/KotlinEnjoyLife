package com.stx.xhb.enjoylife.ui.adapter

import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.stx.xhb.enjoylife.data.entity.Item
import com.stx.xhb.enjoylife.ui.adapter.provider.BannerItemProvider
import com.stx.xhb.enjoylife.ui.adapter.provider.TextItemProvider
import com.stx.xhb.enjoylife.ui.adapter.provider.VideoItemProvider

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class VideoRecyclerAdapter(data: MutableList<Item>?) : MultipleItemRvAdapter<Item, BaseViewHolder>(data) {

    private var mItemClickListener: VideoItemProvider.setOnItemClickListener? = null

    companion object {
        val VIDEO = 1
        val TEXT = 2
        val BANNER = 3
    }


    init {
        finishInitialize()
    }

    override fun registerItemProvider() {
        mProviderDelegate.registerProvider(TextItemProvider())
        val videoItemProvider = VideoItemProvider()
        mProviderDelegate.registerProvider(videoItemProvider)
        mProviderDelegate.registerProvider(BannerItemProvider())
        videoItemProvider.setItemClickListener(object : VideoItemProvider.setOnItemClickListener {
            override fun onItemClick(view: View, data: Item) {
                mItemClickListener?.onItemClick(view, data)
            }
        })
    }

    override fun getViewType(data: Item?): Int {
        return if ("video" == data?.type) {
            VIDEO
        } else if (data?.type?.startsWith("banner")!! && TextUtils.isEmpty(data.data.actionUrl)) {
            BANNER
        } else {
            TEXT
        }
    }

    fun setItemClickListener(itemClickListener: VideoItemProvider.setOnItemClickListener) {
        mItemClickListener = itemClickListener
    }
}