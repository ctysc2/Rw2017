package com.home.rw.repository.network;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.gson.Gson;
import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.ApiConstants;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
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
import com.home.rw.utils.PreferenceUtils;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.SystemTool;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by cty on 2016/12/10.
 */

public class RetrofitManager {

    private RWService mRWService;

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    private static volatile OkHttpClient sOkHttpClient;

    private static SparseArray<RetrofitManager> sRetrofitManager = new SparseArray<>(HostType.TYPE_COUNT);

    public RetrofitManager(@HostType.HostTypeChecker int hostType) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getHost(hostType))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        mRWService = retrofit.create(RWService.class);
    }

    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"),
                        1024 * 1024 * 100);
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .addInterceptor(mLoggingInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor).build();

                }
            }
        }
        return sOkHttpClient;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!SystemTool.isNetworkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                KLog.d("no network");
            }
            //request = request.newBuilder().addHeader("Cookie","123").build();
            //KLog.i("Retrofit","request.headers:"+request.headers());
            Response originalResponse;
            String sessionID = PreferenceUtils.getPrefString(App.getAppContext(),"sessionID","");
            if(sessionID!=null && !sessionID.equals("")){
                //添加公共参数
                HttpUrl.Builder authorizedUrlBuilder = request.url()
                        .newBuilder()
                        .scheme(request.url().scheme())
                        .host(request.url().host())
                        .addQueryParameter("jsid", sessionID);

                // 新的请求
                Request newRequest = request.newBuilder()
                        .method(request.method(), request.body())
                        .url(authorizedUrlBuilder.build())
                        .build();
                Log.i("Retrofit","request url:"+newRequest.url().toString());

                originalResponse = chain.proceed(newRequest);
            }else{

                originalResponse = chain.proceed(request);
            }


            if (SystemTool.isNetworkAvailable()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                //String cacheControl = request.cacheControl().toString();

                String cacheControl = getCacheControl();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();

            }
        }
    };

    private final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();

            KLog.i(String.format(" Retrofit Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            KLog.i(String.format(Locale.getDefault(), "Retrofit Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            String logResponseBody = buffer.clone().readString(UTF_8);
            KLog.i("Retrofit","response.body:"+logResponseBody);
            return response;
        }
    };


    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
            return retrofitManager;
        }
        return retrofitManager;
    }


    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    private String getCacheControl() {
        return SystemTool.isNetworkAvailable() ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    /**
     * Rw登录接口
     *
     * @param phone ：手机号
     * @param password ：密码
     */
    public Observable<LoginEntity> login(String phone, String password) {
        return mRWService.login(phone,password);
    }


    /**
     * 文件上传
     * @param body ：文件集合body
     */
    public Observable<UploadEntity> upload(MultipartBody body) {
        return mRWService.upload(body);
    }


    /**
     * 获取用户信息
     */
    public Observable<UserInfoEntity> getUserInfo() {
        return mRWService.getUserInfo();
    }

    /**
     * 请假/icon_wiped/icon_getout/加班新增接口
     */
    public Observable<AddApplyEntity> addApply() {
        return mRWService.addApply();
    }


    /**
     * 请假/icon_getout/加班新增接口
     */
    public Observable<BaseEntity> editApply(String id,HashMap<String,Object> input) {
        return mRWService.editApply(id,input);
    }

    //我已审批

    public Observable<ApprovementListEntity> checked(int page, int size){
        return mRWService.checked(page,size);
    }

    //icon_byme
    public Observable<ApprovementListEntity> waitinghecked(int page, int size){
        return mRWService.waitinghecked(page,size);
    }


    //审核中
    public Observable<ApprovementListEntity> checking(int page, int size){
        return mRWService.checking(page,size);
    }


    //通过审核的
    public Observable<ApprovementListEntity> passed(int page, int size){
        return mRWService.passed(page,size);
    }

    //审核被拒的
    public Observable<ApprovementListEntity> reject(int page, int size){
        return mRWService.reject(page,size);
    }

    //通过
    public Observable<BaseEntity> doPass(String id){
        return mRWService.doPass(id);
    }

    //拒绝
    public Observable<BaseEntity> doReject(String id){
        return mRWService.doReject(id);
    }

    //获取详情
    public Observable<ApplyDetailEntity> applyDetail(String id){
        return mRWService.applyDetail(id);
    }
    //写日志
    public Observable<BaseEntity> addLog(String title,String content){
        return mRWService.addLog(title,content);
    }
    //发送的日志列表
    public Observable<LogEntity> sendLogList(int page, int size){
        return mRWService.sendLogList(page,size);
    }
    //接受的日志列表
    public Observable<LogEntity> receiveLogList(int page,int size){
        return mRWService.receiveLogList(page,size);
    }
    //发送若一若
    public Observable<BaseEntity> sendRoll(HashMap<String,Object> input){
        return mRWService.sendRoll(input);
    }
    //接受的若一若列表
    public Observable<RollMeEntity> receiveRoll(int page,int size){
        return mRWService.receiveRoll(page,size);
    }
    //签到
    public Observable<BaseEntity> sign(HashMap<String,Object> input){
        return mRWService.sign(input);
    }
    //签到列表
    public Observable<SignEntity> signList(int page, int size){
        return mRWService.signList(page,size);
    }

    //打卡
    public Observable<BaseEntity> card(HashMap<String,Object> input){
        return mRWService.card(input);
    }

    //打卡查询
    public Observable<CardQueryEntity> cardQuery(){
        return  mRWService.cardQuery();
    }


    //修改个人信息
    public Observable<BaseEntity> modifiUserInfo(HashMap<String,Object> input){
        return  mRWService.modifiUserInfo(input);
    }


    //获取短信验证码
    public Observable<BaseEntity> sendVerifiCode(){
        return mRWService.sendVerifiCode();
    }

    //修改密码
    public Observable<BaseEntity> modifiPassword(HashMap<String,Object> input){
        return mRWService.modifiPassword(input);
    }

    //个人中心意见反馈
    public Observable<BaseEntity> feedBack(String content){
        return  mRWService.feedBack(content);
    }
    //退出登录
    public Observable<BaseEntity> logOut(){
        return  mRWService.logOut();
    }

    //我关注的列表
    public Observable<FacusListEntity> focusList (int page, int size){
        return mRWService.focusList(page,size);
    }

    //发布帖子
    public Observable<BaseEntity> publish(HashMap<String,Object> input){
        return mRWService.publish(input);
    }


    //企业交流轮播
    public Observable<LinkedEntity> link1(){
        return mRWService.link1();
    };

    //发现轮播
    public Observable<LinkedEntity> link2(){
        return mRWService.link2();
    };


    //发现精选
    public Observable<LinkedEntity> link3(){
        return mRWService.link3();
    };

    //我的置业公告
    public Observable<MineNoticeEntity> getMineNotice(){
        return mRWService.mineNotice();
    };


    //关注用户
    public Observable<BaseEntity> foucs(String userId){
        return mRWService.foucs(userId);
    }

    //取消关注用户
    public Observable<BaseEntity> cancleFoucs(String userId){
        return mRWService.cancleFocus(userId);
    }
    //我发布的列表

    public Observable<CommunicationEntity> myPublish(int page, int size){
        return mRWService.myPublish(page,size);
    }

    //4个帖子列表
    public Observable<CommunicationEntity> publishList(String catId,int page, int size){
        return mRWService.publishList(catId,page,size);
    }

    //关注混合列表
    public Observable<MixFocusEntity> getMixFocusList(){
        return mRWService.getMixFocusList();
    }

    //他人发布列表
    public Observable<CommunicationEntity> otherPublish(String userId, int page, int size){
        return mRWService.otherPublish(userId,page,size);
    }
    //帖子详情
    public Observable<TopicDetailEntity> topicDetail(String id){
        return mRWService.topicDetail(id);
    }
    //点赞
    public Observable<BaseEntity> follow(String id){
        return mRWService.follow(id);
    }

    //主页
    public Observable<MainPageEntity> getMainPage(){
        return mRWService.getMainPage();
    }
    //动态
    public Observable<CommunicationEntity> getDynamics(int page,int size){
        return mRWService.getDynamics(page,size);
    }
    //帖子评论
    public Observable<CommunicationEntity> topicFeedBack(HashMap<String,Object> input){
        return mRWService.topicFeedBack(input);
    }
    //消息首页
    public Observable<MainBusinessEntity> mainMessage(){
        return mRWService.mainMessage();
    }

    //商务电话列表
    public Observable<BusineseCallEntity> businessCall(){
        return mRWService.businessCall();
    }
    //我的朋友列表
    public Observable<MyFriendEntity> getMyFriend(){
        return mRWService.getMyFriend();
    }
    //添加好友
    public Observable<BaseEntity> addFriend(String userId,String remark){
        return mRWService.addFriend(userId,remark);
    }

    //同意添加好友
    public Observable<BaseEntity> acceptFriend(String userId){
        return mRWService.acceptFriend(userId);
    }
    //获取新的好友列表
    public Observable<NewFriendEntity> getNewFriend(int page , int size){
        return mRWService.getNewFriend(page,size);
    }
    //置业公告列表
    public Observable<RwNoticeEntity> getRwNotice(int page,int size){
        return mRWService.getRwNotice(page,size);
    }
    //公司公告列表
    public Observable<CompNoticeEntity> getCompanyNotice(int page, int size, int type){
        return mRWService.getCompanyNotice(page,size,type);
    }
    //公司公告已读
    public Observable<BaseEntity> readCompanyNotice(String id){
        return mRWService.readCompanyNotice(id);
    }
    //我的群组
    public Observable<MyGroupEntity> getGroupList(){
        return mRWService.getGroupList();
    }
    //添加群组
    public Observable<CreatGroupEntity> addGroup(String name, String users){
        return mRWService.addGroup(name,users);
    }
    //企业通讯录列表
    public Observable<DepartmentEntity> getDepartmentList(){
        return mRWService.getDepartmentList();
    }
    //添加备注
    public Observable<BaseEntity> remark(String userId, String nickname){
        return mRWService.remark(userId,nickname);
    }
    //他人信息
    public Observable<UserInfoEntity> getOtherInfo(String userId){
        return mRWService.getOtherInfo(userId);
    }
    //拨出语音通话
    public Observable<BaseEntity> dialOut(String userId){
        return mRWService.dialOut(userId);
    }

    //删除公司公告
    public Observable<BaseEntity> delNotice(String id){
        return mRWService.delNotice(id);
    }

    //通讯录注册用户列表
    public Observable<ContactListEntity> getUsersByPhone(String phones){
        HashMap<String,String> input = new HashMap<>();
        input.put("phones",phones);
        return mRWService.getUsersByPhone(input);
    }
    //置业公告详情
    public Observable<RwNoticeDetailEntity> getRwNoticeDetailById(String id){
        return mRWService.getRwNoticeDetailById(id);
    }
    //我的团队
    public Observable<MyTeamEntity> myTeam(){
        return mRWService.myTeam();
    }

    //获取钥匙
    public Observable<DoorKeyEntity> getDoorKey(){
        return mRWService.getDoorKey();
    }
}
