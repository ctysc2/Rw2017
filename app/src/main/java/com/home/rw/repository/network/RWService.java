package com.home.rw.repository.network;

import com.home.rw.mvp.entity.LoginEntity;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
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
}
