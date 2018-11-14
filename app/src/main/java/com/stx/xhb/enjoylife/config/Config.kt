package com.stx.xhb.enjoylife.config

import com.stx.xhb.enjoylife.R

/**
 * @author: xiaohaibin.
 * @time: 2018/6/29
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: 侧边栏菜单配置
 */
class Config {

    companion object {
        val WALLPAPER = "WALLPAPER"
        val IMAGE = "IMAGE"
        val ZHIHU = "ZHIHU"
        val VIDEO = "VIDEO"
    }

    enum class Channel constructor(var title: Int, var icon: Int) {
        IMAGE(R.string.fragment_image_title, R.drawable.icon_picture_black_24px),
        WALLPAPER(R.string.fragment_wallpaper_title, R.drawable.icon_picture_black_24px),
        VIDEO(R.string.fragment_video_title, R.drawable.ic_video_black_24px),
        ZHIHU(R.string.fragment_zhihu_title, R.drawable.icon_zhihu_black_24px)
    }

}