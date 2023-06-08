package com.young.module.mine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.young.utils.CoilUtil
import android.widget.BaseAdapter
import coil.load
import com.young.module.mine.R
import com.young.module.mine.bean.MenuBean

class  FiveGridAdapter(var context: Context, var data: MutableList<MenuBean>, var index: Int, var pageSize: Int): BaseAdapter()  {
    private var imageLoader: ImageLoader = CoilUtil.getImageLoader()

    override fun getView(position: Int, p1: View?, parent: ViewGroup?): View {
        var menuBean = getItem(position)

        var convertView = p1
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.five_menu_item, parent, false)
        }
        var holder = ViewHolder(convertView!!)
        convertView.tag = holder
        holder.itemView.findViewById<ImageView>(R.id.menuIcon).load(menuBean.menuIcon, imageLoader ) {
            crossfade(true)
            placeholder(com.young.module.libbase.R.drawable.default_img)
            error(com.young.module.libbase.R.drawable.default_img)
        }
        holder.itemView.findViewById<TextView>(R.id.menuName).text = menuBean.menuName

        return convertView
    }

    override fun getCount(): Int {
        return if (data.size > (index + 1) * pageSize)
            pageSize
        else
            data.size - index * pageSize
    }

    override fun getItem(position: Int): MenuBean {
        return data[position + index * pageSize]
    }

    override fun getItemId(position: Int): Long {
        return (position + index * pageSize).toLong()
    }

    class ViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView)

}