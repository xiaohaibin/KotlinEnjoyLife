package com.stx.xhb.enjoylife.ui.adapter.provider

import android.view.View
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
class VideoItemProvider:BaseItemProvider<Item,BaseViewHolder>() {

    override fun layout(): Int {
        return R.layout.list_home_video_item
    }

    override fun viewType(): Int {
        return VideoRecyclerAdapter.VIDEO
    }

    override fun convert(holder: BaseViewHolder?, data: Item?, position: Int) {
        //得到不同类型所需要的数据
        val feed = data?.data?.cover?.feed
        val title = data?.data?.title
        var category = data?.data?.category
        category = "#$category  /  "
        val duration = data?.data?.duration

        val last = duration?.rem(60)
        var stringLast: String=""
        if (last != null) {
            if (last <= 9) {
                stringLast = "0$last"
            } else {
                stringLast = last.toString() + ""
            }
        }

        var durationString: String=""
        val minit = duration?.div(60)
        if (minit != null) {
            if (minit < 10) {
                durationString = "0$minit"
            } else {
                durationString = "" + minit
            }
        }
        val stringTime = durationString + "' " + stringLast + '"'.toString()

        val view =holder?.getView(R.id.iv) as ImageView
        GlideApp.with(view.getContext()).load(feed).diskCacheStrategy(DiskCacheStrategy.ALL).transition(DrawableTransitionOptions.withCrossFade()).into(view)
        holder.setText(R.id.tv_title, title)
        holder.setText(R.id.tv_time, (category + stringTime).toString())
    }

    override fun onClick(holder: BaseViewHolder?, data: Item?, position: Int) {
        data?.let { mItemClickListener?.onItemClick(holder?.getView(R.id.iv) as ImageView, it) }
    }

    private var mItemClickListener: setOnItemClickListener? = null

    fun setItemClickListener(itemClickListener: setOnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    interface setOnItemClickListener {
        fun onItemClick(view: View, data: Item)
    }
}