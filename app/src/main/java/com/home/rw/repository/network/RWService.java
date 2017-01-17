package com.home.rw.repository.network;

import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by cty on 2016/12/10.
 */

public interface RWService {
    //登录接口
    @POST("login.json")
    Observable<LoginEntity> login(
            @Query("username") String phone,
            @Query("password") String password);


    //文件上传接口
    @POST("attachment_uploads.json")
    Observable<UploadEntity> upload(
            @Body MultipartBody body);


    //上传头像
    @FormUrlEncoded
    @POST("avatar.json")
    Observable<BaseEntity> updateAvatar(
            @FieldMap Map<String,Object> map);
}
