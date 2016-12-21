package com.home.rw.listener;

/**
 * Created by cty on 2016/12/18.
 */

public interface OnAddressReceive {
    void sendAddress(String mAddress);
    void error(String errorMsg);
}
