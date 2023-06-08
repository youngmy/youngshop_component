@file:Suppress("DEPRECATION")

package com.utils

import android.content.*
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.view.Gravity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Version 1.0.1
 */
object WeChatShareHelper {

    private var IMAGE_NAME = "iv_share_"
    private var i = 0


//    fun shareCommodityImagesToWeChat(context: Context, imageDataList: List<CreateShareBean>) {
//            val fileList = ArrayList<File>()
//            if (!isWeChatInstall(context)) {
//                ToastHelper.shortToast(context, "您还没有安装微信!")
//                return
//            }
//            if (imageDataList.isEmpty()) {
//                ToastHelper.shortToast(context, "您还没有选择图片!")
//            } else {
//                Thread {
//                    try {
//                        //把分享过后的list清空
//                        fileList.clear()
//                        for (i in imageDataList.indices) {
//                            val file = saveImageToSdCard(context, imageDataList[i].getImage())
//                            file?.let { fileList.add(it) }
//                        }
//                        val uriList = ArrayList<Uri>()
//                        for (file in fileList) {
//                            LogHelper.i("data===", "===SDK_INT===${Build.VERSION.SDK_INT}")
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                if (Build.VERSION.SDK_INT > 29) {
//                                    val uri =
//                                        FileProvider.getUriForFile(
//                                            context,
//                                            context.getString(R.string.getPackageName) + ".FileProvider",
//                                            file
//                                        )
//                                    uriList.add(uri)
//                                } else {
//                                    val uri = getImageContentUri(context, file)
//                                    val builder = StrictMode.VmPolicy.Builder()
//                                    StrictMode.setVmPolicy(builder.build())
//                                    uriList.add(uri)
//                                }
//                            } else {
//                                uriList.add(Uri.fromFile(file))
//                            }
//                        }
//
//                        val intent = Intent()
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//                        val componentName =
//                            ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")
//                        intent.component = componentName
//                        intent.action = Intent.ACTION_SEND_MULTIPLE
//                        intent.type = "image/*"
//                        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
//                        context.startActivity(Intent.createChooser(intent, ""))
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }.start()
//            }
//    }
//
//    fun shareCommodityImagesToFriends(context: Context, imageList: List<CreateShareBean>) {
//        promptsDialog(context, imageList)
//    }
//
//    private fun promptsDialog(context: Context, imageList: List<CreateShareBean>) {
//        val promptsDialog = CustomDialog(context, R.layout.dialog_prompt)
//        promptsDialog.gravity = Gravity.CENTER
//        promptsDialog.show()
//        promptsDialog.setText(
//            R.id.prompt_content,
//            "如果您希望分享多图到微信、朋友圈，请先点击【保存图片】保存图片到相册，再点击【手动分享】到微信、朋友圈自行选取图片分享！"
//        )
//        promptsDialog.setText(R.id.prompt_cancel, "保存图片")
//        promptsDialog.setText(R.id.prompt_confirm, "手动分享")
//        promptsDialog.setOnItemClickListener(R.id.prompt_cancel) {
//            //                promptsDialog.dismiss();
//            ToastHelper.shortToast(context, "请稍后...")
//            Thread {
//                try {
//                    for (i in imageList.indices) {
//                        val options = RequestOptions()
//                            .fitCenter()
//                        val myBitmap = Glide.with(context)//上下文
//                            .asBitmap() //必须
//                            .load(imageList[i].getImage())//url
//                            .apply(options)
//                            .into(
//                                Resources.getSystem().displayMetrics.widthPixels,
//                                Resources.getSystem().displayMetrics.heightPixels
//                            )
//                            .get()
//
//                        runOnUiThread { saveImageToGallerys(context, myBitmap) }
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }.start()
//            ToastHelper.shortToast(context, "图片已经保存到本地，前往微信、朋友圈分享!")
//        }
//        promptsDialog.setOnItemClickListener(R.id.prompt_confirm) {
//            promptsDialog.dismiss()
//            try {
//                val intent = Intent()
//                val cmp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
//                intent.action = Intent.ACTION_MAIN
//                intent.addCategory(Intent.CATEGORY_LAUNCHER)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                intent.component = cmp
//                context.startActivity(intent)
//            } catch (e: Exception) {
//                //若无法正常跳转，在此进行错误处理
//                ToastHelper.shortToast(context, "无法跳转到微信，请检查您是否安装了微信！")
//            }
//        }
//    }
//
//    private fun saveImageToGallerys(context: Context, bmp: Bitmap?) {
//        if (bmp == null) {
//            ToastHelper.shortToast(context, "保存出错了..")
//            return
//        }
//        // 首先保存图片
//        val appDir = File(Environment.getExternalStorageDirectory(), "share")
//        if (!appDir.exists()) {
//            appDir.mkdir()
//        }
//        val fileName = System.currentTimeMillis().toString() + ".jpg"
//        val file = File(appDir, fileName)
//        try {
//            val fos = FileOutputStream(file)
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
//            fos.flush()
//            fos.close()
//        } catch (e: FileNotFoundException) {
//            ToastHelper.shortToast(context, "文件未发现")
//            e.printStackTrace()
//        } catch (e: IOException) {
//            ToastHelper.shortToast(context, "保存出错了...")
//            e.printStackTrace()
//        } catch (e: Exception) {
//            ToastHelper.shortToast(context, "保存出错了...")
//            e.printStackTrace()
//        }
//
//        // 最后通知图库更新
//        try {
//            MediaStore.Images.Media.insertImage(
//                context.contentResolver,
//                file.absolutePath,
//                fileName,
//                null
//            )
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        }
//
//        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//        val uri = Uri.fromFile(file)
//        intent.data = uri
//        context.sendBroadcast(intent)
//    }
//
//
//    private fun getImageContentUri(context: Context, imageFile: File): Uri {
//        val filePath = imageFile.absolutePath
//        val cursor = context.contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            arrayOf(MediaStore.Images.Media._ID), MediaStore.Images.Media.DATA + "=? ",
//            arrayOf(filePath), null
//        )
//        var uri: Uri? = null
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
//                val baseUri = Uri.parse("content://media/external/images/media")
//                uri = Uri.withAppendedPath(baseUri, "" + id)
//            }
//
//            cursor.close()
//        }
//
//        if (uri == null) {
//            val values = ContentValues()
//            values.put(MediaStore.Images.Media.DATA, filePath)
//            uri =
//                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//        }
//
//        return uri!!
//    }

    //根据网络图片url路径保存到本地
    private fun saveImageToSdCard(context: Context, image: String): File? {
        var success = false
        var file: File? = null
        try {
            file = createStableImageFile(context)

            val bitmap: Bitmap?
            val url = URL(image)
            val conn: HttpURLConnection?
            conn = url.openConnection() as HttpURLConnection
            val inputStream = conn.inputStream
            bitmap = BitmapFactory.decodeStream(inputStream)

            val outStream = FileOutputStream(file)
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()
            success = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return if (success) {
            file
        } else {
            null
        }
    }

    //创建本地保存路径
    @Throws(IOException::class)
    private fun createStableImageFile(context: Context): File {
        i++
        val imageFileName = "$IMAGE_NAME$i.jpg"
        val storageDir = context.externalCacheDir
        return File(storageDir, imageFileName)
    }

    //判断是否安装了微信
    private fun isWeChatInstall(context: Context): Boolean {
        val packageManager = context.packageManager// 获取packagemanager
        val pInfo = packageManager.getInstalledPackages(0)// 获取所有已安装程序的包信息
        for (i in pInfo.indices) {
            val pn = pInfo[i].packageName
            if (pn == "com.tencent.mm") {
                return true
            }
        }
        return false
    }

}
