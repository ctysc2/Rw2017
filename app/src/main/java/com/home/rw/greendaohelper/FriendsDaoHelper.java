package com.home.rw.greendaohelper;

import android.util.Log;

import com.home.rw.application.App;
import com.home.rw.greendao.entity.Friends;
import com.home.rw.greendao.gen.FriendsDao;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/22.
 */

public class FriendsDaoHelper {


    private static FriendsDaoHelper mInstance;
    private FriendsDao dao;

    public static FriendsDaoHelper getInstance(){

        if(mInstance == null){

            mInstance = new FriendsDaoHelper();

        }
        return mInstance;
    }

    private FriendsDaoHelper(){
        dao = App.getInstances().getDaoSession().getFriendsDao();
    }

    public void insertFriends(ArrayList<Friends> friendList){
        dao.insertOrReplaceInTx(friendList);

    }

    public void deleteAdd(){
        dao.deleteAll();
    }

    //根据ID查询用户数据
    public  Friends getFriendById(long id){
        Log.i("GreenDao","要查询的id:"+id);
        Friends friends = dao.queryBuilder().where(FriendsDao.Properties.Id.eq(id)).build().unique();

        if(friends == null){
            Log.i("GreenDao","没有查询到好友");

        }else {
            Log.i("GreenDao","查询到好友了 id为"+id);
        }

        return friends;
    }
}
