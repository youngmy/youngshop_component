package com.young.widget.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class WidgetMultipleItem(
    override var itemType: Int,
    var bean: WidgetDataBean = WidgetDataBean(),
//    var page: Int = 1,
//    var commodityList: List<CommodityListBean> = arrayListOf(),
//    var total: Int = 0,
) : MultiItemEntity {


    companion object {
        const val SEARCH = 1
        const val TOP_BANNER = 2
        const val BTN_GRID = 3
        const val DATE_SHOW = 4
        const val VERTICAL_LED = 5
        const val HORIZONTAL_LED = 6
        const val MINE_HEADER = 7
        const val BTN_HORIZONTAL = 8
        const val ADV_4 = 9
        const val ADV_5 = 10
        const val ADV_6 = 11
        const val ADV_7 = 12
        const val TITLE_BLOCK = 13
        const val GROUP_ON_1 = 14
        const val GROUP_ON_2 = 15
        const val SEC_KILL_1 = 16
        const val SEC_KILL_2 = 17
        const val GOODS_GROUP_1 = 18
        const val GOODS_GROUP_2 = 19
        const val GOODS_LIST_1 = 20
        const val GOODS_LIST_2 = 21
        const val CATEGORY_TABS = 22
        const val CATEGORY_TABS_LIST_1 = 23
        const val CATEGORY_TABS_LIST_2 = 24
    }
}