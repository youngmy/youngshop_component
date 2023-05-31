package com.young.module.demo.bluetooth.bean

import android.bluetooth.BluetoothDevice

/**
 *
 * @description BleDevice
 * @author llw
 * @date 2021/9/10 11:29
 */
data class BleDevice2(var device:BluetoothDevice, var rssi:Int, var name:String?)
