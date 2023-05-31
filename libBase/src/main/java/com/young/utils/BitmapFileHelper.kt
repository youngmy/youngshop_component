package com.young.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

object BitmapFileHelper {

    // 保存到外部储存-公有目录-Picture内，并且无需储存权限
     fun insert2Pictures(context: Context, bitmap: Bitmap): Uri {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val bais = ByteArrayInputStream(baos.toByteArray())
        return insert2Album(context, bais, "Media")
    }

    // 使用MediaStore方式将流写入相册
    @Suppress("SameParameterValue")
    private fun insert2Album(context: Context, inputStream: InputStream, type: String): Uri {
        val fileName = "${type}_${System.currentTimeMillis()}.jpg"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName)
        // Android 10，路径保存在RELATIVE_PATH
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //RELATIVE_PATH 字段表示相对路径，Fundark为相册下专有目录
            contentValues.put(
                MediaStore.Images.ImageColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "YOUR_PATH"
            )
        } else {
            val dstPath = StringBuilder().let { sb->
                sb.append(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.path)
                sb.append(File.separator)
                sb.append("YOUR_PATH")
                sb.append(File.separator)
                sb.append(fileName)
                sb.toString()
            }

            //DATA字段在Android 10.0 之后已经废弃（Android 11又启用了，但是只读）
            contentValues.put(MediaStore.Images.ImageColumns.DATA, dstPath)
        }

        // 插入相册
        val uri =  context.contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        // 写入文件
        uri?.let {
            write2File(context, it, inputStream)
        }

        return uri!!
    }

    private fun write2File(context: Context, uri: Uri, inputStream: InputStream) {
        // 从Uri构造输出流
        context.contentResolver.openOutputStream(uri)?.use { outputStream->
            val byteArray = ByteArray(1024)
            var len: Int
            do {
                //从输入流里读取数据
                len = inputStream.read(byteArray)
                if (len != -1) {
                    outputStream.write(byteArray, 0, len)
                    outputStream.flush()
                }
            } while (len != -1)
        }
    }


}