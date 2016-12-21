package com.home.rw.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.MapView;
import com.home.rw.listener.OnAddressReceive;
import com.home.rw.mvp.ui.activitys.MainActivity;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by cty on 2016/12/18.
 */

public class GoogleMapUtils {

    private static GoogleMapUtils  mapUtils;

    public static GoogleMapUtils getInstance(){
        if(mapUtils == null){
            mapUtils = new GoogleMapUtils();
        }
        return mapUtils;
    }
    //预加载谷歌地图减少第一次进入花费的时间
    public void initGoogleMap(final Context context){


        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                MapView m = new MapView(context);
                m.onCreate(null);
                subscriber.onCompleted();
            }

        })
        .compose(TransformUtils.<String>defaultSchedulers())
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String mAddress) {

            }
        });

    }
    //经纬度->地址
    public Subscription getAddressByLocationAsyn(final Context context, final OnAddressReceive receiver, final Location mLocation){

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {

                String result = "";

                Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                try {
                    List<Address> address = geocoder.getFromLocation(mLocation.getLatitude(),mLocation.getLongitude(),1);

                    if(address.size()>0){
                        result = address.get(0).getAddressLine(0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                    return;
                }

                subscriber.onNext(result);
                subscriber.onCompleted();
            }

        })
        .compose(TransformUtils.<String>defaultSchedulers())
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                receiver.error(e.getMessage());
            }

            @Override
            public void onNext(String mAddress) {
                receiver.sendAddress(mAddress);
            }
        });


    }
}
