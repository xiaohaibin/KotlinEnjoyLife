package com.stx.xhb.enjoylife.mvp.contract

import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel
import com.stx.xhb.enjoylife.data.entity.Feed

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
interface GetWallPaperContract {

          interface Model:IModel{
               fun getWallPaper(page: Int)
          }

         interface View:IBaseView{
             abstract fun onResponse(feedList: List<Feed>, isMore: Boolean)

             abstract fun onFailure(msg: String)
         }



}