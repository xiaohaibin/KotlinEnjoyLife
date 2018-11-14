package com.stx.xhb.enjoylife.data.entity.feed

import java.util.ArrayList

/**
 * @author: xiaohaibin.
 * @time: 2018/11/14
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */

data class TuChongFeedResponse(
        val is_history: Boolean,
        val counts: Int,
        val message: String,
        val more: Boolean,
        val result: String,
        val feedList: List<FeedListBean>
)

data class FeedListBean(

        val post_id: Int,
        var type: String,
        val url: String,
        val site_id: String,
        val author_id: String,
        val published_at: String,
        val passed_time: String,
        val excerpt: String,
        val favorites: Int,
        val comments: Int,
        val isRewardable: Boolean,
        val parent_comments: String,
        val rewards: String,
        val views: Int,
        val isDelete: Boolean,
        val isUpdate: Boolean,
        val content: String,
        val title: String,
        val image_count: Int,
        val title_image: Any,
        val data_type: String,
        val created_at: String,
        val site: SiteBean,
        val recom_type: String,
        val rqt_id: String,
        val isIs_favorite: Boolean,
        val images: List<ImagesBean>,
        val tags: List<String>,
        val event_tags: List<*>,
        val favorite_list_prefix: List<*>,
        var reward_list_prefix: List<*>,
        val comment_list_prefix: List<*>,
        val sites: List<*>
)


data class SiteBean(
        val type: String,
        val name: String,
        val domain: String,
        val description: String,
        val followers: Int,
        val url: String,
        val icon: String,
        val isVerified: Boolean,
        val verified_type: Int,
        val verified_reason: String,
        val verifications: Int,
        val isIs_following: Boolean,
        val verification_list: List<*>,
        val site_id: String
)


data class ImagesBean(
        val img_id: Int,
        val user_id: Int,
        val title: String,
        val excerpt: String,
        val width: Int,
        val height: Int,
        val description: String
)