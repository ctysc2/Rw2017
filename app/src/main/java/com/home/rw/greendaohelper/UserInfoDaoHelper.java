package com.home.rw.greendaohelper;

import android.util.Log;

import com.home.rw.application.App;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendao.gen.UserInfoDao;

import java.util.Map;

/**
 * Created by cty on 2016/12/16.
 */

public class UserInfoDaoHelper {

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

    //根据用户名查询用户数据
    private  UserInfo getUserInfo(String username){

        UserInfo user = dao.queryBuilder().where(UserInfoDao.Properties.UserName.eq(username)).build().unique();

        if(user == null){
            Log.i("GreenDao","插入UserInfo数据,username为"+username);
            user = new UserInfo();
            user.setUserName(username);
            dao.insert(user);

        }

        return user;
    }

    //根据用户名查询用户并更新多项数据
    public void updateUserInfo(String username, Map<String,String> needUpdate){

        //先找到数据
        UserInfo user = getUserInfo(username);

        //修改数据参数
        for (Map.Entry<String, String> entry : needUpdate.entrySet()) {

            String key = entry.getKey();
            String value = entry.getValue();
            switch (key){
                case NICKNAME:
                    user.setNickName(value);
                    break;
                case HEADURL:
                    user.setHeadUrl(value);
                    break;
                default:
                    break;
            }

        }
        Log.i("GreenDao","更新UserInfo数据,username为"+username);
        //更新数据
        dao.update(user);


    }
    //获取指定数据
    public String getDataByUser(String username,String dataType){
        //先找到数据
        UserInfo user = getUserInfo(username);

        //读取数据
        String result = "";
        switch (dataType){
            case NICKNAME:
                result = user.getNickName();
                break;
            case HEADURL:
                result = user.getHeadUrl();
                break;
            default:
                break;
        }
        Log.i("GreenDao","读取UserInfo数据,username为"+username);
        Log.i("GreenDao","读取UserInfo数据,类型："+dataType+" 值："+result);
        return result;

    }
}
