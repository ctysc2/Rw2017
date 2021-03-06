package com.home.rw.repository.network;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.ApplyDetailEntity;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.CardQueryEntity;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.LinkedEntity;
import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.MainPageEntity;
import com.home.rw.mvp.entity.MineNoticeEntity;
import com.home.rw.mvp.entity.MixFocusEntity;
import com.home.rw.mvp.entity.message.DoorKeyEntity;
import com.home.rw.mvp.entity.message.MyTeamEntity;
import com.home.rw.mvp.entity.RollMeEntity;
import com.home.rw.mvp.entity.SignEntity;
import com.home.rw.mvp.entity.TopicDetailEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.BusineseCallEntity;
import com.home.rw.mvp.entity.message.CompNoticeEntity;
import com.home.rw.mvp.entity.message.ContactListEntity;
import com.home.rw.mvp.entity.message.CreatGroupEntity;
import com.home.rw.mvp.entity.message.DepartmentEntity;
import com.home.rw.mvp.entity.message.MainBusinessEntity;
import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.entity.message.MyGroupEntity;
import com.home.rw.mvp.entity.message.NewFriendEntity;
import com.home.rw.mvp.entity.message.RwNoticeDetailEntity;
import com.home.rw.mvp.entity.message.RwNoticeEntity;
import com.home.rw.utils.CacheUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    //获取用户信息接口
    @GET("user_info.json")
    Observable<UserInfoEntity> getUserInfo();

    //文件上传接口
    @POST("attachment_uploads.json")
    Observable<UploadEntity> upload(
            @Body MultipartBody body);


    //请假/icon_wiped/icon_getout/加班新增接口
    @GET("add.json")
    Observable<AddApplyEntity> addApply();

    //请假/icon_wiped/icon_getout/加班编辑接口
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("edit.json")
    Observable<BaseEntity> editApply(
            @Query("id") String id,
            @FieldMap HashMap<String,Object> input
            );

    //我已审批
    @GET("checked.json")
    Observable<ApprovementListEntity> checked(
            @Query("page") int page,
            @Query("size") int size
    );

    //icon_byme
    @GET("waiting_check.json")
    Observable<ApprovementListEntity> waitinghecked(
            @Query("page") int page,
            @Query("size") int size
    );

    //审核中
    @GET("checking.json")
    Observable<ApprovementListEntity> checking(
            @Query("page") int page,
            @Query("size") int size
    );

    //通过审核的
    @GET("passed.json")
    Observable<ApprovementListEntity> passed(
            @Query("page") int page,
            @Query("size") int size
    );

    //审核被拒的
    @GET("reject.json")
    Observable<ApprovementListEntity> reject(
            @Query("page") int page,
            @Query("size") int size
    );

    //通过
    @GET("apply_passed.json")
    Observable<BaseEntity> doPass(
            @Query("id") String id

    );
    //拒绝
    @GET("apply_reject.json")
    Observable<BaseEntity> doReject(
            @Query("id") String id
    );

    //icon_getout,请假,icon_wiped,加班详情
    @GET("details.json")
    Observable<ApplyDetailEntity> applyDetail(
            @Query("id") String id
    );

    //发送日志
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("add.json")
    Observable<BaseEntity> addLog(
            @Field("title") String title,
            @Field("content") String content
    );
    //发送日志列表
    @GET("my_send.json")
    Observable<LogEntity> sendLogList(
            @Query("page") int page,
            @Query("size") int size
    );

    //接受日志列表
    @GET("my_receive.json")
    Observable<LogEntity> receiveLogList(
            @Query("page") int page,
            @Query("size") int size
    );

    //发送若一若
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("add.json")
    Observable<BaseEntity> sendRoll(
            @FieldMap HashMap<String,Object> input
    );

    //接受若一若列表
    @GET("my_receive.json")
    Observable<RollMeEntity> receiveRoll(
            @Query("page") int page,
            @Query("size") int size
    );

    //签到
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("add.json")
    Observable<BaseEntity> sign(
            @FieldMap HashMap<String,Object> input
    );

    //签到列表
    @GET("list.json")
    Observable<SignEntity> signList(
            @Query("page") int page,
            @Query("size") int size

    );

    //打卡
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("add.json")
    Observable<BaseEntity> card(
            @FieldMap HashMap<String,Object> input
    );

    //打卡查询
    @GET("curr_day.json")
    Observable<CardQueryEntity> cardQuery();

    //修改个人信息
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("set.json")
    Observable<BaseEntity> modifiUserInfo(
            @FieldMap HashMap<String,Object> input
    );

    //获取短信验证码
    @GET("up_pwd_sms.json")
    Observable<BaseEntity> sendVerifiCode();

    //修改密码
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("up_pwd.json")
    Observable<BaseEntity> modifiPassword(
            @FieldMap HashMap<String,Object> input
    );

    //个人中心意见反馈
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("feedback.json")
    Observable<BaseEntity> feedBack(
            @Field("content") String content
    );

    //退出登录
    @GET("logout.json")
    Observable<BaseEntity> logOut();


    //关注人列表
    @GET("focus.json")
    Observable<FacusListEntity> focusList(
            @Query("page") int page,
            @Query("size") int size
    );

    //提交帖子
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("add.json")
    Observable<BaseEntity> publish(
            @FieldMap HashMap<String,Object> input
    );

    //企业交流轮播
    @GET("work_loop_ad.json")
    Observable<LinkedEntity> link1();

    //发现轮播
    @GET("ac_loop_ad.json")
    Observable<LinkedEntity> link2();


    //发现精选
    @GET("winnow.json")
    Observable<LinkedEntity> link3();

    //我的置业公告
    @GET("rw_notice_new.json")
    Observable<MineNoticeEntity> mineNotice();


    //关注用户
    @GET("add.json")
    Observable<BaseEntity> foucs(
            @Query("userId") String userId
    );

    //取消关注用户
    @GET("remove.json")
    Observable<BaseEntity> cancleFocus(
            @Query("userId") String userId
    );

    //我发布的列表
    @GET("publish.json")
    Observable<CommunicationEntity> myPublish(
            @Query("page") int page,
            @Query("size") int size

    );
    //4个帖子列表
    @GET("cat.json")
    Observable<CommunicationEntity> publishList(
            @Query("catId") String catId,
            @Query("page") int page,
            @Query("size") int size

    );

    //关注混合列表
    @GET("focus.json")
    Observable<MixFocusEntity> getMixFocusList();

    //他人发布列表
    @GET("publish.json")
    Observable<CommunicationEntity> otherPublish(
            @Query("createdBy") String userId,
            @Query("page") int page,
            @Query("size") int size
    );

    //帖子详情
    @GET("topic_item.json")
    Observable<TopicDetailEntity> topicDetail(
            @Query("id") String id

    );

    //点赞
    @GET("support.json")
    Observable<BaseEntity> follow(
            @Query("topicId") String id

    );
    //主页
    @GET("main.json")
    Observable<MainPageEntity> getMainPage();

    //动态
    @GET("dynamics.json")
    Observable<CommunicationEntity> getDynamics(
            @Query("page") int page,
            @Query("size") int size
    );

    //帖子评论
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("add.json")
    Observable<CommunicationEntity> topicFeedBack(
            @FieldMap HashMap<String,Object> input
    );

    //消息首页
    @GET("center.json")
    Observable<MainBusinessEntity> mainMessage();

    //商务电话列表
    @GET("bi_phones.json")
    Observable<BusineseCallEntity> businessCall();

    //我的朋友列表
    @GET("friends.json")
    Observable<MyFriendEntity> getMyFriend();
    //添加好友
    @GET("apply.json")
    Observable<BaseEntity> addFriend(
            @Query("userId") String userId,
            @Query("remark") String remark
    );
    //同意添加好友
    @GET("friend_accept.json")
    Observable<BaseEntity> acceptFriend(
            @Query("userId") String userId
    );

    //新的好友
    @GET("new_friends.json")
    Observable<NewFriendEntity> getNewFriend(
            @Query("page") int page,
            @Query("size") int size
    );

    //置业公告列表
    @GET("list.json")
    Observable<RwNoticeEntity> getRwNotice(
            @Query("page") int page,
            @Query("size") int size
    );

    //公司公告列表
    @GET("notice_list.json")
    Observable<CompNoticeEntity> getCompanyNotice(
            @Query("page") int page,
            @Query("size") int size,
            @Query("readed") int type

    );

    //公司公告已读
    @GET("notice_read.json")
    Observable<BaseEntity> readCompanyNotice(
            @Query("Id") String id
    );

    //我的群组
    @GET("groups.json")
    Observable<MyGroupEntity> getGroupList(
    );
    //添加群组
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("add.json")
    Observable<CreatGroupEntity> addGroup(
            @Field("name") String name,
            @Field("receiveUsers") String users
    );
    //企业通讯录列表
    @GET("dept_info_list.json")
    Observable<DepartmentEntity> getDepartmentList();

    //添加备注
    @GET("edit_nickname.json")
    Observable<BaseEntity> remark(
            @Query("userId") String userId,
            @Query("nickname") String nickname
    );
    //他人信息
    @GET("user_info.json")
    Observable<UserInfoEntity> getOtherInfo(
            @Query("userId") String userId
    );
    //拨出语音通话
    @GET("telephone.json")
    Observable<BaseEntity> dialOut(
            @Query("userId") String userId
    );

    //删除公司公告
    @GET("notice_remove.json")
    Observable<BaseEntity> delNotice(
            @Query("Id") String id
    );
    //通讯录注册用户列表
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8;")
    @FormUrlEncoded
    @POST("find_users_by_phone.json")
    Observable<ContactListEntity> getUsersByPhone(
            @FieldMap HashMap<String,String> input
    );

    //置业公告详情
    @GET("details.json")
    Observable<RwNoticeDetailEntity> getRwNoticeDetailById(
            @Query("id") String id
    );

    //我的团队
    @GET("team.json")
    Observable<MyTeamEntity> myTeam();

    //获取钥匙
    @GET("open_door.json")
    Observable<DoorKeyEntity> getDoorKey();

}
