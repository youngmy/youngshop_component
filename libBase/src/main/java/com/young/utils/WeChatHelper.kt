package com.young.utils

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.helper.YToastHelper
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXImageObject
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

object WeChatHelper {

    /**
     * APP拉起微信客服功能
     */
    fun openCustomerServiceChat(context: Context?, corpId: String, url: String) {
//        val api = WXAPIFactory.createWXAPI(context, context?.getString(R.string.wx_appid))
//        // 判断当前版本是否支持拉起客服会话
//        if (api.wxAppSupportAPI >= Build.SUPPORT_OPEN_CUSTOMER_SERVICE_CHAT) {
//            val req: WXOpenCustomerServiceChat.Req = WXOpenCustomerServiceChat.Req()
//            req.corpId = corpId // 企业ID
//            req.url = url // 客服URL
//            api.sendReq(req)
//        }
    }


    fun openWeChatApplets(
        context: Context?,
        weChatAppId: String?,
        userName: String?,
        path: String?
    ) {
        val mIWXAPI = WXAPIFactory.createWXAPI(context, weChatAppId)//填应用AppId
        val req = WXLaunchMiniProgram.Req()
        req.userName = userName // 填小程序原始id
        req.path = path //拉起小程序页面的可带参路径，不填默认拉起小程序首页，
        // 对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE // 可选打开 开发版，体验版和正式版
        mIWXAPI?.sendReq(req)
    }

    var mIWXAPI: IWXAPI? = null

    @Suppress("DEPRECATION")
    fun weChatShareImage(context: Context?, appId: String, isWeChat: Int, imageUrl: String) {
        // 微信OpenAPI访问入口，通过WXAPIFactory创建实例
        mIWXAPI = WXAPIFactory.createWXAPI(context, appId, true)
        // 将应用的AppId注册到微信
        mIWXAPI?.registerApp(appId)
        if (mIWXAPI?.isWXAppInstalled!!) {
            val options = RequestOptions()
                .dontAnimate()
            Glide.with(context!!)
                .asBitmap()
                .load(//if (TextUtils.isEmpty(imageUrl)) R.mipmap.app_logo else
                    imageUrl)
                .apply(options)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        resource.let {
                            val wXImageObject = WXImageObject(it)
                            val wXMediaMessage = WXMediaMessage(wXImageObject)
                            val thumbBmp = Bitmap.createScaledBitmap(it, 150, 150, true)
//                                it.recycle()
                            wXMediaMessage.setThumbImage(thumbBmp)
                            val req = SendMessageToWX.Req()
                            req.transaction = "img"
                            req.message = wXMediaMessage
                            req.scene = if (isWeChat == 0) SendMessageToWX.Req.WXSceneSession
                            else
                                SendMessageToWX.Req.WXSceneTimeline
                            mIWXAPI?.sendReq(req)
                        }
                    }
                })
        } else {
//            ToastHelper.shortToast(context, "未发现微信客户端")
        }
    }

    fun weChatText(
        context: Context?,
        appId: String,
        isWeChat: Int,
        shareTitle: String?,
        shareImage: String?,
        shareUrl: String?,
        shareDescribe: String?
    ) {
        // 微信OpenAPI访问入口，通过WXAPIFactory创建实例
        mIWXAPI = WXAPIFactory.createWXAPI(context, appId, true)
        // 将应用的AppId注册到微信
        mIWXAPI?.registerApp(appId)
        if (mIWXAPI?.isWXAppInstalled!!) {
            val options = RequestOptions()
                .dontAnimate()
            Glide.with(context!!)
                .asBitmap()
                .load(
//                    if (TextUtils.isEmpty(shareImage))
//                        R.mipmap.app_logo
//                    else
                        shareImage
                )
                .apply(options)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        resource.let {
                            val wXWebpageObject = WXWebpageObject()
                            wXWebpageObject.webpageUrl = shareUrl
                            val wWXMediaMessage = WXMediaMessage(wXWebpageObject)
                            if (isWeChat == 1) {
                                wWXMediaMessage.title = shareDescribe
                            } else if (isWeChat == 0) {
                                wWXMediaMessage.title = shareTitle
                            }
                            wWXMediaMessage.description = shareDescribe
                            val thumbBmp = Bitmap.createScaledBitmap(it, 150, 150, true)
                            wWXMediaMessage.setThumbImage(thumbBmp)

                            val req = SendMessageToWX.Req()
                            req.transaction = System.currentTimeMillis().toString()
                            req.message = wWXMediaMessage
                            req.scene = if (isWeChat == 0)
                                SendMessageToWX.Req.WXSceneSession
                            else
                                SendMessageToWX.Req.WXSceneTimeline

                            // 调用api接口发送数据到微信
                            mIWXAPI?.sendReq(req)
                        }
                    }
                })
        } else {
//            ToastHelper.shortToast(context, "未发现微信客户端")
        }
    }


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