package com.stx.xhb.enjoylife.data.entity

/**
 * @author: xiaohaibin.
 * @time: 2018/6/26
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */

data class VideoResponse(
		val issueList: List<Issue>,
		val nextPageUrl: String,
		val nextPublishTime: Any,
		val newestIssueType: String,
		val dialog: Any
)

data class Issue(
		val releaseTime: Long,
		val type: String,
		val date: Long,
		val publishTime: Long,
		val itemList: List<Item>,
		val count: Int
)

data class Item(
		val type: String,
		val data: Data,
		val tag: Any,
		val id: Int,
		val adIndex: Int
)

data class Data(
		val dataType: String,
		val id: Int,
		val title: String,
		val description: String,
		val image: String,
		val actionUrl: String,
		val adTrack: Any,
		val shade: Boolean,
		val label: Any,
		val labelList: Any,
		val header: Any,
		val cover:Cover,
        val category:String,
		val duration:Int,
		val text:String,
		val type:String,
		val playUrl:String,
		val consumption:Consumption,
		val provider:Provider
)

data class Cover(
		val feed:String,
		val detail:String,
		val blurred:String,
		val sharing:String
)

data class Provider(
		val name:String,
		val alias:String,
		val icon:String
)

data class Consumption(
		val collectionCount:Int,
		val shareCount:Int,
		val playCount:Int,
		val replyCount:Int
)