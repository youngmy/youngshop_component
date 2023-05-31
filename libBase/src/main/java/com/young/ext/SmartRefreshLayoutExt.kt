package com.young.ext

import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

fun SmartRefreshLayout.initSmartRefreshLayout(autoRefresh: Boolean = true,
                                              enableLoadMore: Boolean = true,
                                              refreshListener: OnRefreshListener? = null,
                                              loadMoreListener: OnLoadMoreListener? = null): SmartRefreshLayout {
    if (autoRefresh) {
        autoRefresh()
    }
    setEnableLoadMore(enableLoadMore)
    setOnRefreshListener(refreshListener)
    setOnLoadMoreListener(loadMoreListener)
    return this
}