package com.home.rw.greendaohelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.home.rw.greendao.gen.DaoMaster;
import com.home.rw.greendao.gen.FriendsDao;
import com.home.rw.greendao.gen.GroupDao;
import com.home.rw.greendao.gen.UserInfoDao;

/**
 * Created by cty on 2017/4/16.
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {


    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db,FriendsDao.class, GroupDao.class, UserInfoDao.class);//数据版本变更才会执行
    }

}
