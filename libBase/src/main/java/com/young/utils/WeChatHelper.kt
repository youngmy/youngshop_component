package com.young.utils

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.helper.YToastHelper

object WeChatHelper {

    /**
     * 分享到微信好友
     * https://juejin.cn/post/7232127712642891834
     */
     fun shareTextToWeChat(context: Context?, text: String) {
        //判断是否安装微信，如果没有安装微信 又没有判断就直达微信分享是会挂掉的
        if (!isAppInstall(context, "com.tencent.mm")) {
            YToastHelper.shortToast(context, "您还没有安装微信")
            return
        }
        shareText(context, text, "com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")
    }

  private  fun shareText(context: Context?, text: String, pkg: String, cls: String) {
        if (TextUtils.isEmpty(text)) {
            YToastHelper.shortToast(context, "内容不能为空")
            return
        }

        try {
            val intent = Intent()
            intent.component = ComponentName(pkg, cls)
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, text)
            intent.type = "text/plain"
            context?.startActivity(Intent.createChooser(intent, "分享"))
        }catch (e: Exception) {
            e.printStackTrace()
            YToastHelper.shortToast(context, "分享失败")
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    fun isAppInstall(context: Context?, appPackageName: String): Boolean {
        val packageManager = context?.packageManager // 获取packagemanager
        @Suppress("DEPRECATION")
        val info = packageManager?.getInstalledPackages(0) // 获取所有已安装程序的包信息
        for (i in info!!.indices) {
            val pn = info[i].packageName
            if (appPackageName == pn) {
                return true
            }
        }
        return false
    }


    /**
     * 分享到微信朋友圈，需要带一张图
     */
     fun shareTextToWeChatFriend(context: Context, message: Message) {
        if (!isAppInstall(context, "com.tencent.mm")) {
            YToastHelper.shortToast(context, "您还没有安装微信")
            return
        }

        try {
            val intent = Intent()
            intent.component = ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI")
            intent.action = Intent.ACTION_SEND
            intent.type = "image/*";

            val shareStr = """
                ${message.title}
                ${message.description}
                ${message.shareUrl}
                """.trimIndent()
            intent.putExtra(Intent.EXTRA_TEXT, shareStr)
            intent.putExtra("Kdescription", shareStr);
            // 给目标应用一个临时授权，好像用不到
            // intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.putExtra(Intent.EXTRA_STREAM, message.thumbnail);

            context.startActivity(Intent.createChooser(intent, "分享"))
        }catch (e: Exception) {
            e.printStackTrace()
            YToastHelper.shortToast(context, "分享失败")
        }
    }




}

data class Message(
    var title: String = "",
    var description: String = "",
    var shareUrl: String = "",
    var scene: String = "",
    var thumbnail: Uri? = null)