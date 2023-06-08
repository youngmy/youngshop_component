package com.young.ext

import android.view.View

fun setVisible(visible: Int, vararg views: View?) {
    for (view in views)
        view?.run {
            if (visibility != visible) {
                visibility = visible
            }
        }
}

fun goneViews( vararg views: View?){
    setVisible(View.GONE, *views)
}

fun showViews( vararg views: View?){
    setVisible(View.VISIBLE, *views)
}

fun inVisibleViews( vararg views: View?){
    setVisible(View.INVISIBLE, *views)
}

fun View.setViewAlpha(mAlpha:Int):View{
    background?.alpha=mAlpha
    return this
}

fun setViewAlpha(mAlpha: Int, vararg views: View?) {
    for (view in views)
        view?.run {
            background?.alpha=mAlpha
        }
}