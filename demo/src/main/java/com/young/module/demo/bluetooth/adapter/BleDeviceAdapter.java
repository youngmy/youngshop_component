package com.young.module.demo.bluetooth.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.young.module.demo.R;
import com.young.module.demo.bluetooth.bean.BleDevice;

import java.util.List;

public class BleDeviceAdapter extends BaseQuickAdapter<BleDevice, BaseViewHolder> {

    public BleDeviceAdapter(int layoutResId, List<BleDevice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BleDevice bleDevice) {
        holder.setText(R.id.tv_device_name, bleDevice.getRealName())
                .setText(R.id.tv_mac_address, bleDevice.getDevice().getAddress())
                .setText(R.id.tv_rssi, bleDevice.getRssi() + " dBm");
    }


}
