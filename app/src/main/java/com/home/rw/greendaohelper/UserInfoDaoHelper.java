package com.home.rw.greendaohelper;

import android.util.Log;

import com.home.rw.application.App;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendao.gen.UserInfoDao;
import com.home.rw.mvp.entity.UserInfoEntity;

/**
 * Created by cty on 2016/12/16.
 */

public class UserInfoDaoHelper {

    public static final String USERNAME = "username";
    public static final String NICKNAME = "nickname";
    public static final String HEADURL = "headurl";

    private static UserInfoDaoHelper mInstance;
    private UserInfoDao dao;

    public static UserInfoDaoHelper getInstance(){

        if(mInstance == null){

            mInstance = new UserInfoDaoHelper();

        }
        return mInstance;
    }

    private UserInfoDaoHelper(){
        dao = App.getInstances().getDaoSession().getUserInfoDao();
    }

    public void insertUserInfo(UserInfo user){
        long row = dao.insertOrReplace(user);
        Log.i("GreenDao","插入完成 row:"+row);
    }

    public void deleteAdd(){
        dao.deleteAll();
    }

    //根据ID查询用户数据
    public  UserInfo getUserInfoById(long id){
        Log.i("GreenDao","要查询的id:"+id);
        UserInfo user = dao.queryBuilder().where(UserInfoDao.Properties.Id.eq(id)).build().unique();

        if(user == null){
            Log.i("GreenDao","没有查询到");

        }else {
            Log.i("GreenDao","查询到了 id为"+id);
        }

        return user;
    }

    public UserInfo parseEntity2UserInfo(UserInfoEntity.DataEntity entity){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Long.parseLong(entity.getId()));
        userInfo.setAvatar(entity.getAvatar());
        userInfo.setGender(entity.getGender());
        userInfo.setRealName(entity.getRealname());
        userInfo.setPhone(entity.getPhone());
        userInfo.setCompany(entity.getCompany().getName());
        userInfo.setPubNum(entity.getPubNum());
        userInfo.setFocusNum(entity.getFocusNum());
        return userInfo;
    }

}
