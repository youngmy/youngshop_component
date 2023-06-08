@file:Suppress("DEPRECATION")

package com.young.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log

object MapHelper {

    /**
     * 高德转百度（火星坐标gcj02ll–>百度坐标bd09ll）
     */
    fun gaoDeToBaidu(gd_lat: Double, gd_lon: Double): DoubleArray {
        val bd_lat_lon = DoubleArray(2)
        val PI = 3.14159265358979324 * 3000.0 / 180.0
        val z = Math.sqrt(gd_lon * gd_lon + gd_lat * gd_lat) + 0.00002 * Math.sin(gd_lat * PI)
        val theta = Math.atan2(gd_lat, gd_lon) + 0.000003 * Math.cos(gd_lon * PI)
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006
        return bd_lat_lon
    }


    /**
     * 百度转高德（百度坐标bd09ll–>火星坐标gcj02ll）
     */
    fun bdToGaoDe(bd_lat: Double, bd_lon: Double): DoubleArray {
        val gd_lat_lon = DoubleArray(2)
        val PI = 3.14159265358979324 * 3000.0 / 180.0
        val x = bd_lon - 0.0065
        val y = bd_lat - 0.006
        val z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI)
        val theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI)
        gd_lat_lon[0] = z * Math.cos(theta)
        gd_lat_lon[1] = z * Math.sin(theta)
        return gd_lat_lon
    }

    /**
     * 打开高德地图（公交出行，起点位置使用地图当前位置）
     * dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     */
    fun openGaoDeMap(context: Context?, appName: String, myLocationName: String, dlat: Double, dlon: Double, dname: String) {
        if (isInstallGaoDeMap(context)) {
            val url = "androidamap://route?sourceApplication=${appName}&sname=${myLocationName}&dlat=" +
                    dlat.toString() + "&dlon=" + dlon.toString() + "&dname=" + dname + "&dev=0&m=0&t=0"
//            LogHelper.i("data===", "===openGaoDeMap===${url}")
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setPackage("com.autonavi.minimap")
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(url)
                context!!.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
//            ToastHelper.shortToast(context, "未安装高德地图")
        }
    }

    /** 打开百度地图（公交出行，起点位置使用地图当前位置）
     *
     * mode = transit（公交）、driving（驾车）、walking（步行）和riding（骑行）. 默认:driving
     * 当 mode=transit 时 ： sy = 0：推荐路线 、 2：少换乘 、 3：少步行 、 4：不坐地铁 、 5：时间短 、 6：地铁优先
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     **/
    fun openBaiDuMap(context: Context?, myLocationName: String, dlat: Double, dlon: Double, dname: String) {
        if (isInstallBaiDuMap(context)) {
            val url = "baidumap://map/direction?origin=${myLocationName}&destination=name:" + dname +
                    "|latlng:" + dlat + "," + dlon + "&mode=driving&sy=0&index=0&target=1"
//            LogHelper.i("data===", "===openBaiDuMap===${url}")
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                context!!.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
//            ToastHelper.shortToast(context, "未安装百度地图")
        }
    }

    /**
     * 打开腾讯地图（公交出行，起点位置使用地图当前位置）
     *
     * 公交：type=bus，policy有以下取值
     * 0：较快捷 、 1：少换乘 、 2：少步行 、 3：不坐地铁
     * 驾车：type=drive，policy有以下取值
     * 0：较快捷 、 1：无高速 、 2：距离短
     * policy的取值缺省为0
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     */
    fun openTenCentMap(context: Context?, myLocationName: String, dlat: Double, dlon: Double, dname: String) {
        if (isInstallTenCentMap(context)) {
            val url = "qqmap://map/routeplan?type=drive&from=${myLocationName}&fromcoord=0,0" +
                    "&to=" + dname +
                    "&tocoord=" + dlat + "," + dlon + "&policy=1&referer=myapp"
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                context!!.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
//            ToastHelper.shortToast(context, "未安装腾讯地图")
        }
    }


    private fun isInstallGaoDeMap(context: Context?): Boolean {
        return checkPackage(context, "com.autonavi.minimap")
    }

    private fun isInstallBaiDuMap(context: Context?): Boolean {
        return checkPackage(context, "com.baidu.BaiduMap")
    }

    private fun isInstallTenCentMap(context: Context?): Boolean {
        return checkPackage(context, "com.tencent.map")
    }


    private fun checkPackage(context: Context?, packageName: String?): Boolean {
        if (packageName == null || "" == packageName) return false
        return try {
            context!!.packageManager.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun openBrowserMap(context: Context, location: String, toLng: String, toLat: String) {
        try {
            val url = "http://uri.amap.com/navigation?" +
                    "from=" + location +
                    "&to=" + toLng + "," + toLat
            Log.i("data===", "===openBrowserMap===url===$url")
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}