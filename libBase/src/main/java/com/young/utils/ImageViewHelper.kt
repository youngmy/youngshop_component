package com.young.utils

import android.annotation.SuppressLint
import android.content.Context
import com.young.module.libbase.R

object ImageViewHelper {

    @SuppressLint("DiscouragedApi")
    fun getImageViewId(context: Context?, imageName: String): Int {
        return context?.resources!!.getIdentifier(
            "mipmap/$imageName", null,
            context.packageName
        )
    }

    fun getImageViewId(imageName: String): Int {
      return  when (imageName) {
            "icon_tao_bao" -> {
                R.mipmap.icon_tao_bao
            }
          "icon_jing_dong" -> {
              R.mipmap.icon_jing_dong
          }
          "icon_pin_duo_duo" -> {
              R.mipmap.icon_pin_duo_duo
          }
          "icon_su_ning_yi_gou" -> {
              R.mipmap.icon_su_ning_yi_gou
          }
          "icon_wei_pin_hui" -> {
              R.mipmap.icon_wei_pin_hui
          }
          "icon_mobile_phone_recharge" -> {
              R.mipmap.icon_mobile_phone_recharge
          }
          "icon_communication" -> {
              R.mipmap.icon_communication
          }
          "icon_take_out_food" -> {
              R.mipmap.icon_take_out_food
          }
          "icon_discount_oil" -> {
              R.mipmap.icon_discount_oil
          }
          "icon_free_buy" -> {
              R.mipmap.icon_free_buy
          }
            else -> {
                0
            }
        }
    }

}