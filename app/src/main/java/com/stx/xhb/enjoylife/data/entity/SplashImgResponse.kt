package com.stx.xhb.enjoylife.data.entity

/**
 * @author: xiaohaibin.
 * @time: 2018/12/25
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: SplashImgResponse
 */
data class SplashImgResponse(
    val app: List<App>,
    val app_timing: List<Any>,
    val result: String
)

data class App(
    val author_name: String,
    val id: String,
    val image_url: String,
    val title: String,
    val tuchong_url: String
)