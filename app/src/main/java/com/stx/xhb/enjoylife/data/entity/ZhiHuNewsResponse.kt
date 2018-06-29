package com.stx.xhb.enjoylife.data.entity

/**
 * @author: xiaohaibin.
 * @time: 2018/6/26
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
data class ZhiHuNewsResponse(
        val date: String,
        val stories: List<StoriesBean>,
        val top_stories: List<TopStoriesBean>
)

class StoriesBean(
        val type: Int,
        val id: Int,
        val ga_prefix: String,
        val title: String,
        val images: List<String>
)

class TopStoriesBean(
        val image: String,
        val type: Int,
        val id: Int,
        val ga_prefix: String,
        val title: String
)