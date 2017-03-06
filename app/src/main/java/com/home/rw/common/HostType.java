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
    //登录
    public static final int LOGIN = 1;

    //上传文件
    public static final int UPLOAD = 2;

    //用户信息
    public static final int USER_INFO = 3;

    //报销新增
    public static final int ADD_APPLY_EXPENSE = 4;

    //请假新增
    public static final int ADD_APPLY_LEAVE = 5;

    //外出新增
    public static final int ADD_APPLY_OUTGO = 6;

    //加班新增
    public static final int ADD_APPLY_OVERTIME = 7;

    //报销修改
    public static final int EDIT_APPLY_EXPENSE = 8;

    //请假修改
    public static final int EDIT_APPLY_LEAVE = 9;

    //外出修改
    public static final int EDIT_APPLY_OUTGO = 10;

    //加班修改
    public static final int EDIT_APPLY_OVERTIME = 11;


    //我已审批
    public static final int APPROVE_CHECKED= 12;

    //待我审批
    public static final int APPROVE_WAITINNG_CHECKED = 13;

    //审批中
    public static final int APPROVE_CHECKING = 14;

    //审批通过
    public static final int APPROVE_PASSED = 15;

    //审批被拒
    public static final int APPROVE_REJECT = 16;

    //通过
    public static final int APPLY_DO_PASS = 17;

    //拒绝
    public static final int APPLY_DO_REJECT = 18;

    //外出详情
    public static final int DETAIL_APPLY_OUTGO = 19;

    //请假详情
    public static final int DETAIL_APPLY_LEAVE  = 20;

    //加班详情
    public static final int DETAIL_APPLY_OVERTIME = 21;

    //报销详情
    public static final int DETAIL_APPLY_EXPENSE= 22;

    //写日志
    public static final int WRITE_LOG= 23;

    //发送的日志
    public static final int SEND_LOG= 24;

    //接收的日志
    public static final int RECEIVE_LOG= 25;


    //发送若一若
    public static final int SEND_ROLL= 26;

    //接收的若一若列表
    public static final int RECEIVE_ROLL= 27;

    //签到
    public static final int SIGN= 28;

    //签到列表
    public static final int SIGN_LIST= 29;

    //打卡
    public static final int CARD= 30;

    //打卡查询
    public static final int CARD_QUERY= 31;

    //修改个人信息
    public static final int MODIFI_USER_INFO= 32;

    //获取短信验证码
    public static final int VERIFI_CODE= 33;

    //修改密码
    public static final int MODIFI_PASSWORD= 34;

    //意见反馈
    public static final int MY_FEEDBACK= 35;
    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({LOGIN,UPLOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }
}
