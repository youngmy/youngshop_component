package com.young.ext

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("WrongConstant")
fun RecyclerView.initRecyclerView(context: Context?,
                                  layoutManagerType: Int = 0,
                                  spanCount: Int = 1,
                                  @RecyclerView.Orientation orientation: Int = LinearLayoutManager.HORIZONTAL,
                                  reverseLayout: Boolean = false): RecyclerView {
    layoutManager = if (0 == layoutManagerType) {
        GridLayoutManager(context, spanCount)
    } else {
        LinearLayoutManager(context, orientation, reverseLayout)
    }
    return this
}