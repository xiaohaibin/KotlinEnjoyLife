package com.stx.xhb.enjoylife.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.data.entity.StoriesBean
import com.stx.xhb.enjoylife.ui.activity.NewsDetailsActivity

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class NewsAdapter(layoutResId: Int) : BaseQuickAdapter<StoriesBean, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder?, news: StoriesBean?) {
        if (!news?.images?.isEmpty()!!) {
            val imageView = holder?.getView(R.id.thumbnail_image) as ImageView
            Glide.with(imageView.getContext()).load(news.images.get(0)).into(imageView)
        }
        holder?.setText(R.id.daily_title, news.title)
        holder?.itemView?.setOnClickListener {
            NewsDetailsActivity.start(mContext, news.id.toString(), news.title)
        }
    }
}