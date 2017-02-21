package com.home.rw.listener;

import android.content.Context;
import android.util.Log;

import com.home.rw.mvp.ui.activitys.MainActivity;
import com.home.rw.utils.PreferenceUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by cty on 2017/2/16.
 */

public class RwNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {
        Log.i("RongYun","onNotificationMessageArrived");
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        Log.i("RongYun","onNotificationMessageClicked");
        RongIM.connect(PreferenceUtils.getPrefString(context,"token",""), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.i("RongYun","onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                Log.i("RongYun", "onSuccess userId:" + s);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("RongYun","onError errorCode:"+errorCode.getMessage());
            }
        });
        return false;
    }

}
