package com.stx.xhb.enjoylife.ui.adapter

import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stx.xhb.core.widget.RatioImageView
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.config.GlideApp
import com.stx.xhb.enjoylife.data.entity.Feed
import java.util.ArrayList

/**
 * @author: xiaohaibin.
 * @time: 2018/7/11
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class TuChongWallPaperAdapter(layoutResId: Int) : BaseQuickAdapter<Feed, BaseViewHolder>(layoutResId) {

    private var mOnImageItemClickListener: OnImageItemClickListener? = null

    override fun convert(holder: BaseViewHolder?, feedListBean: Feed?) {
        val feedListBeanEntry = feedListBean?.entry
        if ("video" == feedListBeanEntry?.type) {
            return
        }
        val imageView = holder?.getView(R.id.iv_img) as RatioImageView
        imageView.setOriginalSize(50, 50)
        val limit = 48
        val text = if (feedListBeanEntry?.title?.length!! > limit)
            feedListBeanEntry.title.substring(0, limit) + "..."
        else
            feedListBeanEntry.title
        (holder.getView(R.id.tv_title) as TextView).setText(text)

        val images = feedListBeanEntry.images
        if (images == null || images.isEmpty()) {
            return
        }
        val imagesBean = images.get(0)
        if (imagesBean != null) {
            val url = "https://photo.tuchong.com/" + imagesBean.user_id + "/f/" + imagesBean.img_id + ".jpg"
            GlideApp.with(mContext)
                    .load(url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
                    .getSize { width, height ->
                        if (!holder.itemView.isShown()) {
                            holder.itemView.setVisibility(View.VISIBLE)
                        }
                    }
            holder.itemView.setTag(url)
            ViewCompat.setTransitionName(imageView, url)
        }

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


    fun setOnImageItemClickListener(onImageItemClickListener: TuChongWallPaperAdapter.OnImageItemClickListener) {
        mOnImageItemClickListener = onImageItemClickListener
    }

    interface OnImageItemClickListener {
        fun setOnImageClick(view: View, imageList: ArrayList<String>)
    }
}