package com.home.rw.application;

import android.app.ActivityManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.home.rw.di.component.ApplicationComponent;
import com.home.rw.di.component.DaggerApplicationComponent;
import com.home.rw.di.module.ApplicationModule;
import com.home.rw.greendao.gen.DaoMaster;
import com.home.rw.greendao.gen.DaoSession;
import com.home.rw.utils.CrashHandler;
import com.squareup.okhttp.OkHttpClient;

import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;

/**
 * Created by cty on 16/10/18.
 */
public class App extends MultiDexApplication {
    private static App sAppContext;
    private ApplicationComponent mApplicationComponent;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    //当前登录用户的ID
    public static int ID;
    //当前登录用户的sessionID
    public static String sessionID;
    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            //初始化崩溃日志
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(this);
            initApplicationComponent();

            //初始化Fresco
            ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, new OkHttpClient())
                    .setDownsampleEnabled(true)
                    .build();
            Fresco.initialize(this, config);
            //注册GCM推送
            try {
                RongPushClient.registerGCM(this);

            } catch (RongException e) {
                Log.i("RongYun", "GCM失败:" + e.getMessage());
                e.printStackTrace();
            }
            RongIM.init(this);
            RongCloudAppContext.init(this);
            setDataBase();
        }

    }

    private void setDataBase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
    public static Context getAppContext() {
        return sAppContext;
    }

    // Fixme
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static App getInstances(){

        return sAppContext;
    }
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
