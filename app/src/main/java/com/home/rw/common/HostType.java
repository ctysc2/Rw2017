package com.home.rw.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cty on 16/10/19.
 */
public class HostType {
    /**
     * 多少种Host类型
     */
    public static final int TYPE_COUNT = 3;
    public static final int TEST = -1;
    //登录
    public static final int LOGIN = 1;

    //上传文件
    public static final int UPLOAD = 2;

    //用户头像
    public static final int AVATAR = 3;

    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({LOGIN,UPLOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }
}
