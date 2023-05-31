package com.young.widget.utils

import com.young.module.libbase.R
import com.young.widget.bean.WidgetDataBean
import com.young.widget.bean.WidgetItemTypeBean
import com.young.widget.bean.WidgetMultipleItem


object WidgetDataHelper {

    fun getItemView(list: List<WidgetDataBean>): List<WidgetMultipleItem> {
        val dataList = ArrayList<WidgetMultipleItem>()
        dataList.clear()
        for (i in list.indices) {
            when (list[i].viewType) {
                "top_banner" -> {
                    dataList.add(WidgetMultipleItem(WidgetMultipleItem.TOP_BANNER, bean = list[i]))
                }
                "btn_grid" -> {
                    dataList.add(WidgetMultipleItem(WidgetMultipleItem.BTN_GRID, bean = list[i]))
                }
                "data_show" -> {
                    dataList.add(WidgetMultipleItem(WidgetMultipleItem.DATE_SHOW, bean = list[i]))
                }
                "horizontal_led"-> {
                    dataList.add(WidgetMultipleItem(WidgetMultipleItem.HORIZONTAL_LED, bean = list[i]))
                }
                "mine_header"-> {
                    dataList.add(WidgetMultipleItem(WidgetMultipleItem.MINE_HEADER, bean = list[i]))
                }
                "btn_horizontal"-> {
                    dataList.add(WidgetMultipleItem(WidgetMultipleItem.BTN_HORIZONTAL, bean = list[i]))
                }
                else -> {

                }
            }
        }
        return dataList
    }

    fun getItemType(): List<WidgetItemTypeBean> {
        val dataList = ArrayList<WidgetItemTypeBean>()
        dataList.clear()
        dataList.add(
            WidgetItemTypeBean(
                WidgetMultipleItem.TOP_BANNER,
                R.layout.layout_top_banner
            )
        )
        dataList.add(
            WidgetItemTypeBean(
                WidgetMultipleItem.BTN_GRID,
                R.layout.layout_btn_grid
            )
        )
        dataList.add(
            WidgetItemTypeBean(
                WidgetMultipleItem.DATE_SHOW,
                R.layout.layout_date_show
            )
        )
        dataList.add(
            WidgetItemTypeBean(
                WidgetMultipleItem.HORIZONTAL_LED,
                R.layout.layout_horizontal_led
            )
        )
        dataList.add(
            WidgetItemTypeBean(
                WidgetMultipleItem.VERTICAL_LED,
                R.layout.layout_vertical_led
            )
        )
        dataList.add(
            WidgetItemTypeBean(
                WidgetMultipleItem.MINE_HEADER,
                R.layout.layout_mine_header
            )
        )
        dataList.add(
            WidgetItemTypeBean(
                WidgetMultipleItem.BTN_HORIZONTAL,
                R.layout.layout_btn_horizontal
            )
        )
        return dataList
    }

}