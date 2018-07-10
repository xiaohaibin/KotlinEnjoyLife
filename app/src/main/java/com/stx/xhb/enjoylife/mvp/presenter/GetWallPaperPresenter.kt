package com.stx.xhb.enjoylife.mvp.presenter

import com.stx.xhb.core.mvp.BasePresenter
import com.stx.xhb.enjoylife.data.entity.TuChongWallPaperResponse
import com.stx.xhb.enjoylife.mvp.contract.GetWallPaperContract

/**
 * @author: xiaohaibin.
 * @time: 2018/7/10
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class GetWallPaperPresenter:BasePresenter<TuChongWallPaperResponse,GetWallPaperContract.View>(),GetWallPaperContract.Model {


    override fun getWallPaper(page: Int) {

    }
}