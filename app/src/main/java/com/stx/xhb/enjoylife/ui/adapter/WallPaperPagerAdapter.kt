package com.stx.xhb.enjoylife.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.chrisbanes.photoview.OnViewTapListener
import com.github.chrisbanes.photoview.PhotoView
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.config.GlideApp
import java.util.*

/**
 * @author: xiaohaibin.
 * @time: 2018/7/11
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:WallPaperPagerAdapter
 */
class WallPaperPagerAdapter(context: Context, imageList: ArrayList<String>):PagerAdapter() {

    private val context: Context
    private val imageList: ArrayList<String>

    init {
        this.context = context
        this.imageList = imageList
    }
    private var mImageLayoutListener: onImageLayoutListener? = null

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val convertView = View.inflate(context, R.layout.item_wallpaper, null)
        val photoView = convertView.findViewById<ImageView>(R.id.photo_view)
        val progressBar = convertView.findViewById<ProgressBar>(R.id.progress_view)
        val imgUrl = imageList[position]
        GlideApp.with(context)
                .load(imgUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(photoView)

       photoView.setOnClickListener(View.OnClickListener {
           if (mImageLayoutListener != null) {
               mImageLayoutListener!!.setOnImageOnClik()
           }
       })

        photoView.setOnLongClickListener(View.OnLongClickListener {
            if (mImageLayoutListener != null) {
                mImageLayoutListener!!.setLongClick(imgUrl)
            }
            true
        })
        container.addView(convertView)
        return convertView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun setOnClickListener(onClickListener: onImageLayoutListener) {
        mImageLayoutListener = onClickListener
    }

    interface onImageLayoutListener {

        fun setOnImageOnClik()

        fun setLongClick(url: String)

    }
}