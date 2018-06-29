package com.stx.xhb.enjoylife.data.entity

/**
 * @author: xiaohaibin.
 * @time: 2018/6/26
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */

data class ZhiHuNewsContentResponse(
		val body: String,
		val image_source: String,
		val title: String,
		val image: String,
		val share_url: String,
		val js: List<Any>,
		val ga_prefix: String,
		val images: List<String>,
		val type: Int,
		val id: Int,
		val css: List<String>
)