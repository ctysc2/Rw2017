package com.home.rw.greendaohelper;

import android.util.Log;

import com.home.rw.application.App;
import com.home.rw.greendao.entity.Group;
import com.home.rw.greendao.gen.GroupDao;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/31.
 */

public class GroupDaoHelper {
    private static GroupDaoHelper mInstance;
    private GroupDao dao;

    public static GroupDaoHelper getInstance(){

        if(mInstance == null){

            mInstance = new GroupDaoHelper();

        }
        return mInstance;
    }

    private GroupDaoHelper(){
        dao = App.getInstances().getDaoSession().getGroupDao();
    }

    public void insertGroups(ArrayList<Group> groupList){
        dao.insertOrReplaceInTx(groupList);

    }

    public void deleteAdd(){
        dao.deleteAll();
    }

    //根据ID查询用户数据
    public  Group getGroupById(long id){
        Log.i("GreenDao","要查询的id:"+id);
        Group group = dao.queryBuilder().where(GroupDao.Properties.Id.eq(id)).build().unique();

        if(group == null){
            Log.i("GreenDao","没有查询到群组");

        }else {
            Log.i("GreenDao","查询到群组了 id为"+id);
        }

        return group;
    }
}
