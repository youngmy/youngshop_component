package com.young.module.demo.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.permissionx.guolindev.PermissionX
import com.young.base.activity.BaseActivity
import com.young.commonconfig.helper.bluetoothDemo1Activity
import com.young.module.demo.R
import com.young.module.demo.bluetooth.adapter.BleDeviceAdapter
import com.young.module.demo.bluetooth.bean.BleDevice
import com.young.module.demo.databinding.ActivityBluetoothDemo1Binding
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanResult


@Route(path = bluetoothDemo1Activity)
class BluetoothDemo1Activity :
    BaseActivity<ActivityBluetoothDemo1Binding>(R.layout.activity_bluetooth_demo_1) {

    private val TAG: String = BluetoothDemo1Activity::class.java.simpleName

    /**
     * 请求打开蓝牙
     */
    private val REQUEST_ENABLE_BLUETOOTH = 100

    /**
     * 权限请求码
     */
//    val REQUEST_PERMISSION_CODE = 9527

    /**
     * 蓝牙适配器
     */
    private var bluetoothAdapter: BluetoothAdapter? = null

    /**
     * nordic扫描回调
     */
    private var scanCallback: ScanCallback? = null

    /**
     * 设备列表
     */
    private val mList = ArrayList<BleDevice>()

    /**
     * 列表适配器
     */
    private var deviceAdapter: BleDeviceAdapter? = null

    /**
     * 加载进度条
     */
//    private val loadingProgressBar: ContentLoadingProgressBar? = null

    /**
     * 等待连接
     */
//    private val layConnectingLoading: LinearLayout? = null

    /**
     * Gatt
     */
    private var bluetoothGatt: BluetoothGatt? = null

    /**
     * 设备是否连接
     */
    private var isConnected = false

    override fun initData() {
        //初始化
        initViews()
        //检查Android版本
        checkAndroidVersion()
    }


    /**
     * 初始化
     */
    private fun initViews() {

        mBinding.btnStartScan.setOnClickListener { v -> startScanDevice() }
        mBinding.btnStopScan.setOnClickListener { v -> stopScanDevice() }
        //扫描结果回调
        scanCallback = object : ScanCallback() {
            @SuppressLint("MissingPermission")
            override fun onScanResult(callbackType: Int, @NonNull result: ScanResult) {
                //添加到设备列表
                addDeviceList(
                    BleDevice(
                        result.getDevice(),
                        result.getRssi(),
                        result.getDevice().getName()
                    )
                )
            }

            override fun onScanFailed(errorCode: Int) {
                throw RuntimeException("Scan error")
            }
        }

        //列表配置
        deviceAdapter = BleDeviceAdapter(
            R.layout.item_device_rv_1,
            mList
        )
        mBinding.rvDevice.layoutManager = LinearLayoutManager(this)
        //item点击事件
        deviceAdapter?.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int ->
            //连接设备
            connectDevice(mList[position])
        }
        //启用动画
        deviceAdapter?.animationEnable = true
        //设置动画方式
        deviceAdapter?.setAnimationWithDefault(AnimationType.SlideInRight)
        mBinding.rvDevice.adapter = deviceAdapter

    }

    /**
     * 连接设备
     *
     * @param bleDevice 蓝牙设备
     */
    @SuppressLint("MissingPermission")
    private fun connectDevice(bleDevice: BleDevice) {
        //显示连接等待布局
        mBinding.layConnectingLoading.visibility = View.VISIBLE

        //停止扫描
        stopScanDevice()

        //获取远程设备
        val device = bleDevice.device
        //连接gatt
        bluetoothGatt = device.connectGatt(this, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        isConnected = true
                        Log.d(TAG, "连接成功")
                        runOnUiThread {
                            mBinding.layConnectingLoading.visibility = View.GONE
                            showMsg("连接成功")
                        }
                    }

                    BluetoothProfile.STATE_DISCONNECTED -> {
                        isConnected = false
                        Log.d(TAG, "断开连接")
                        runOnUiThread {
                            mBinding.layConnectingLoading.visibility = View.GONE
                            showMsg("断开连接")
                            disconnectDevice()
                        }
                    }

                    else -> {}
                }
            }
        })
    }

    /**
     * 断开设备连接
     */
    @SuppressLint("MissingPermission")
    private fun disconnectDevice() {
        if (isConnected && bluetoothGatt != null) {
            bluetoothGatt?.disconnect()
        }
    }

    /**
     * 添加到设备列表
     *
     * @param bleDevice 蓝牙设备
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun addDeviceList(bleDevice: BleDevice) {
        if (!mList.contains(bleDevice)) {
            bleDevice.realName = if (bleDevice.realName == null) "UNKNOWN" else bleDevice.realName
            mList.add(bleDevice)
        } else {
            //更新设备信号强度值
            for (device in mList) {
                device.rssi = bleDevice.rssi
            }
        }
        //刷新列表适配器
        deviceAdapter?.notifyDataSetChanged()
    }


    /**
     * 开始扫描设备
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun startScanDevice() {
        mBinding.loadingProgressBar.visibility = View.VISIBLE;
        mList.clear()
        deviceAdapter?.notifyDataSetChanged()
        val scanner = BluetoothLeScannerCompat.getScanner()
        scanner.startScan(scanCallback!!)
    }

    /**
     * 停止扫描设备
     */
    private fun stopScanDevice() {
        mBinding.loadingProgressBar.visibility = View.INVISIBLE
        val scanner = BluetoothLeScannerCompat.getScanner()
        scanner.stopScan(scanCallback!!)
    }

    /**
     * 检查Android版本
     */
    private fun checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Android 6.0及以上动态请求权限
            requestPermission()
        } else {
            //检查蓝牙是否打开
            openBluetooth()
        }
    }

    /**
     * 请求权限
     */

    fun requestPermission() {
        PermissionX.init(this).permissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
            .request { allGranted, _, _ ->
                if (allGranted) {
                    //权限通过之后检查有没有打开蓝牙
                    openBluetooth()
                } else {
                    showMsg("App需要定位权限")
                }
            }



    }



    /**
     * 是否打开蓝牙
     */
    @SuppressLint("MissingPermission")
    fun openBluetooth() {
        //获取蓝牙适配器
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null) { //是否支持蓝牙
            if (bluetoothAdapter!!.isEnabled) { //打开
                showMsg("蓝牙已打开")
            } else { //未打开
                startActivityForResult(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
                    REQUEST_ENABLE_BLUETOOTH
                )
            }
        } else {
            showMsg("你的设备不支持蓝牙")
        }
    }

    /**
     * Toast提示
     *
     * @param msg 内容
     */
    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
//                if (bluetoothAdapter!!.isEnabled) {
//                    //蓝牙已打开
//                    showMsg("蓝牙已打开")
//                } else {
//                    showMsg("请打开蓝牙")
//                }
//            }
//        }
//    }

}