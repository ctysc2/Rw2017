package com.home.rw.utils;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by cty on 2017/3/31.
 */

public class BtUtils {
    /**
     * 当前 Android 设备的 bluetooth 是否已经开启
     *
     * @return true：Bluetooth 已经开启 false：Bluetooth 未开启
     */
    public static boolean isBluetoothEnabled()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        if (bluetoothAdapter != null)
        {
            return bluetoothAdapter.isEnabled();
        }

        return false;
    }

    /**
     * 强制开启当前 Android 设备的 Bluetooth
     *
     * @return true：强制打开 Bluetooth　成功　false：强制打开 Bluetooth 失败
     */
    public static boolean turnOnBluetooth()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        if (bluetoothAdapter != null)
        {
            return bluetoothAdapter.enable();
        }

        return false;
    }
}
