package com.home.rw.mvp.interactor.impl;

import android.util.Log;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.interactor.LoginInteractor;
import com.home.rw.mvp.interactor.UploadInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RetrofitUtils;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;
import okhttp3.MediaType;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/1/12.
 */

public class UploadInteractorImpl implements UploadInteractor<UploadEntity> {

    @Inject
    public UploadInteractorImpl(){

    }

    @Override
    public Subscription upload(final RequestCallBack<UploadEntity> callback, List<String> fileList) {


        MultipartBody body = RetrofitUtils.filesToMultipartBody("fileName",fileList,MediaType.parse("multipart/form-data"));
        return RetrofitManager.getInstance(HostType.UPLOAD).upload(body)
                .compose(TransformUtils.<UploadEntity>defaultSchedulers())
                .subscribe(new Observer<UploadEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(UploadEntity data) {
                        if(data!=null && data.getCode().equals("9999")){
                            callback.onError(App.getAppContext().getString(R.string.reRoad));
                            RxBus.getInstance().post(new ReLoginEvent());
                        }
                        else{
                            callback.success(data);
                        }

                    }

                });
    }

}
