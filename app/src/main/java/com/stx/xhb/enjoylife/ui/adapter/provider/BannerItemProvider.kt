package com.stx.xhb.enjoylife.ui.adapter.provider

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.config.GlideApp
import com.stx.xhb.enjoylife.data.entity.Item
import com.stx.xhb.enjoylife.ui.adapter.VideoRecyclerAdapter

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class BannerItemProvider:BaseItemProvider<Item,BaseViewHolder>() {

    override fun layout(): Int {
        return R.layout.list_home_item_banner
    }

    override fun viewType(): Int {
        return VideoRecyclerAdapter.BANNER
    }

    override fun convert(holder: BaseViewHolder?, data: Item?, position: Int) {
        val imageView =holder?.getView(R.id.iv_banner) as ImageView
        GlideApp.with(imageView.getContext())
                .load(data?.data?.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }
}