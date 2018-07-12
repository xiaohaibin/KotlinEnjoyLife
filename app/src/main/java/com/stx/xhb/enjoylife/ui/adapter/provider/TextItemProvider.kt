package com.stx.xhb.enjoylife.ui.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.stx.xhb.enjoylife.R
import com.stx.xhb.enjoylife.data.entity.Item
import com.stx.xhb.enjoylife.ui.adapter.VideoRecyclerAdapter

/**
 * @author: xiaohaibin.
 * @time: 2018/7/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class TextItemProvider:BaseItemProvider<Item,BaseViewHolder>() {

    override fun layout(): Int {
            return R.layout.list_home_text_item
    }

    override fun viewType(): Int {
        return VideoRecyclerAdapter.TEXT
    }

    override fun convert(holder: BaseViewHolder?, data: Item?, position: Int) {
        if (data?.type?.startsWith("text")!!) {
            holder?.setGone(R.id.tv_home_text, true)
        } else {
            holder?.setGone(R.id.tv_home_text, false)
        }
        holder?.setText(R.id.tv_home_text, data.data.text)
    }
}