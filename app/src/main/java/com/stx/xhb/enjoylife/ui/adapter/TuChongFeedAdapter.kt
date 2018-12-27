package com.stx.xhb.enjoylife.ui.adapter

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stx.xhb.core.widget.RatioImageView
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.config.GlideApp
import com.stx.xhb.enjoylife.data.entity.feed.FeedListBean
import java.util.*

/**
 * @author: xiaohaibin.
 * @time: 2018/7/11
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: 图虫推荐
 */
class TuChongFeedAdapter(layoutResId: Int) : BaseQuickAdapter<FeedListBean, BaseViewHolder>(layoutResId) {

    private var mOnImageItemClickListener: OnImageItemClickListener? = null

    override fun convert(holder: BaseViewHolder?, feedListBean: FeedListBean?) {

        val imageView = holder?.getView(R.id.iv_img) as RatioImageView
        imageView.setOriginalSize(50, 50)
        val limit = 48
        val text = if (feedListBean?.title?.length!! > limit)
            feedListBean.title.substring(0, limit) + "..."
        else
            feedListBean.title
        val textView = holder.getView(R.id.tv_title) as TextView
        textView.setText(text)

        val images = feedListBean.images
        if (images.isEmpty()) {
            return
        }
        val imagesBean = images.get(0)
        val url = "https://photo.tuchong.com/" + imagesBean.user_id + "/f/" + imagesBean.img_id + ".jpg"
        GlideApp.with(mContext)
                .load(url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView)
        holder.itemView.setTag(url)
        ViewCompat.setTransitionName(imageView, url)

        val imageList = ArrayList<String>()
        for (i in images.indices) {
            val imageData = images.get(i)
            val url = "https://photo.tuchong.com/" + imageData.user_id + "/f/" + imageData.img_id + ".jpg"
            imageList.add(url)
        }
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (mOnImageItemClickListener != null) {
                mOnImageItemClickListener?.setOnImageClick(imageView, imageList)
            }
        })
    }


    fun setOnImageItemClickListener(onImageItemClickListener: TuChongFeedAdapter.OnImageItemClickListener) {
        mOnImageItemClickListener = onImageItemClickListener
    }

    interface OnImageItemClickListener {
        fun setOnImageClick(view: View, imageList: ArrayList<String>)
    }
}