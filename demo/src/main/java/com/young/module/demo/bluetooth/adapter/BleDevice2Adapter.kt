package com.young.module.demo.bluetooth.adapter

import android.annotation.SuppressLint
import com.young.module.demo.bluetooth.bean.BleDevice2
import com.young.module.demo.databinding.ItemBluetoothBinding

/**
 * Ble设备适配器 传入ViewBinding
 * @description BleDeviceAdapter
 * @author llw
 * @date 2021/9/10 12:28
 */
class BleDevice2Adapter(data: MutableList<BleDevice2>? = null) :
    ViewBindingAdapter<ItemBluetoothBinding, BleDevice2>(data) {
    @SuppressLint("SetTextI18n")
    override fun convert(holder: ViewBindingHolder<ItemBluetoothBinding>, item: BleDevice2) {
        val binding = holder.vb
        binding.tvDeviceName.text = item.name
        binding.tvMacAddress.text = item.device.address
        binding.tvRssi.text = "${item.rssi} dBm"
    }
}