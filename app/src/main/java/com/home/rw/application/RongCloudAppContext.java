package com.home.rw.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.home.rw.event.UnReadMessageEvent;
import com.home.rw.greendaohelper.GroupDaoHelper;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.MyGroupEntity;
import com.home.rw.mvp.interactor.impl.DialOutInteractorImpl;
import com.home.rw.mvp.interactor.impl.MyGroupInteractorImpl;
import com.home.rw.mvp.interactor.impl.UserInfoInteractorImpl;
import com.home.rw.mvp.presenter.impl.DialOutPresenterImpl;
import com.home.rw.mvp.presenter.impl.MyGroupPresenterImpl;
import com.home.rw.mvp.presenter.impl.UserInfoPresenterImpl;
import com.home.rw.mvp.ui.activitys.message.ContactsActivity;
import com.home.rw.mvp.ui.activitys.rongCloud.MyExtensionModule;
import com.home.rw.mvp.view.DialOutView;
import com.home.rw.mvp.view.MyGroupView;
import com.home.rw.mvp.view.UserInfoView;
import com.home.rw.utils.PreferenceUtils;
import com.home.rw.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import io.rong.calllib.IRongCallListener;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * Created by cty on 2017/2/17.
 */

public class RongCloudAppContext implements RongIM.ConversationListBehaviorListener,
        RongIMClient.OnReceiveMessageListener,
        RongIM.UserInfoProvider,
        RongIM.GroupInfoProvider,
        RongIM.GroupUserInfoProvider,
        RongIM.LocationProvider,
        RongIMClient.ConnectionStatusListener,
        RongIM.ConversationBehaviorListener{

    private Context mContext;

    private static RongCloudAppContext mRongCloudInstance;

    public RongCloudAppContext(Context context){
        this.mContext = mContext;
        initListener();
    }
    public static RongCloudAppContext getInstance() {
        return mRongCloudInstance;
    }

    private UserInfoPresenterImpl mUserInfoPresenterImpl;

    private MyGroupPresenterImpl mMyGroupPresenterImpl;
    private  UserInfoView mUserInfoView = new UserInfoView() {
        @Override
        public void getUserInfoCompleted(UserInfoEntity data) {
            if(data.getCode().equals("ok") &&
                    data.getData()!=null){

                com.home.rw.greendao.entity.UserInfo user = UserInfoDaoHelper.getInstance().parseEntity2UserInfo(data.getData());

                UserInfoDaoHelper.getInstance().insertUserInfo(user);

                RongIM.getInstance().refreshUserInfoCache(new UserInfo(
                        String.valueOf(user.getId()),
                        user.getRealName(),
                        !TextUtils.isEmpty(user.getAvatar())?Uri.parse(user.getAvatar()):null));
                Log.i("Retrofit","异步更新他人信息成功");
            }
        }

        @Override
        public void showProgress(int reqType) {

        }

        @Override
        public void hideProgress(int reqType) {

        }

        @Override
        public void showErrorMsg(int reqType, String msg) {

        }
    };

    private MyGroupView mMyGroupview = new MyGroupView() {
        @Override
        public void getMyGroupCompleted(MyGroupEntity data) {
            if(data.getCode().equals("ok") &&
                    data.getData()!=null){
                ArrayList<com.home.rw.greendao.entity.Group> groupList = new ArrayList<>();
                for(int i = 0;i<data.getData().size();i++){
                    com.home.rw.greendao.entity.Group group = new com.home.rw.greendao.entity.Group();
                    group.setId(Long.parseLong(data.getData().get(i).getId()));
                    group.setGroupName(data.getData().get(i).getName());
                    groupList.add(group);
                    RongIM.getInstance().refreshGroupInfoCache(new Group(data.getData().get(i).getId(),
                            data.getData().get(i).getName(),
                            null));
                }

                GroupDaoHelper.getInstance().insertGroups(groupList);

            }
        }

        @Override
        public void showProgress(int reqType) {

        }

        @Override
        public void hideProgress(int reqType) {

        }

        @Override
        public void showErrorMsg(int reqType, String msg) {

        }
    };
    public Context getContext(){
        return mContext;
    }

    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mRongCloudInstance == null) {
            synchronized (RongCloudAppContext.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new RongCloudAppContext(context);
                }
            }
        }

    }

    /**
     * init 后就能设置的监听
     */
    private void initListener() {
        RongIM.setConversationBehaviorListener(this);//设置会话界面操作的监听器。
        RongIM.setConversationListBehaviorListener(this);
        RongIM.setConnectionStatusListener(this);
        RongIM.setUserInfoProvider(this,true);
        RongIM.setGroupUserInfoProvider(this,true);
        setReadReceiptConversationType();
        RongIM.getInstance().enableNewComingMessageIcon(true);
        RongIM.getInstance().enableUnreadMessageIcon(true);
        setInputProvider();

    }
    private void setReadReceiptConversationType() {
        Conversation.ConversationType[] types = new Conversation.ConversationType[] {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP,
                Conversation.ConversationType.DISCUSSION
        };
        RongIM.getInstance().setReadReceiptConversationTypeList(types);
    }

    private void setInputProvider() {
        RongIM.setOnReceiveMessageListener(this);

        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
            }
        }
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }

    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public Group getGroupInfo(String s) {
        Group RGroup = null;
        com.home.rw.greendao.entity.Group group = GroupDaoHelper.getInstance().getGroupById(Long.parseLong(s));
        if(group!=null){
             RGroup = new Group(String.valueOf(group.getId()),group.getGroupName(),null);
        }
        //异步获取
        if(mMyGroupPresenterImpl == null){
            mMyGroupPresenterImpl = new MyGroupPresenterImpl(new MyGroupInteractorImpl());
            mMyGroupPresenterImpl.attachView(mMyGroupview);
        }
        Log.i("Retrofit","异步获取群组信息");
        mMyGroupPresenterImpl.getMyGroupList();

        return RGroup;
    }

    @Override
    public GroupUserInfo getGroupUserInfo(String s, String s1) {
        return null;
    }

    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {

    }

    @Override
    public UserInfo getUserInfo(String s) {
        Log.i("RongColud","getUserInfo");
        UserInfo userInfo = null;
        if(s.equals("1000")){
            userInfo = new io.rong.imlib.model.UserInfo(
                    s,
                    "若为置业",
                    Uri.parse("http://img1.mp.oeeee.com/201702/03/04f372705ced49eb.jpg")
            );
        }else{

            //先从数据库获取数据
            com.home.rw.greendao.entity.UserInfo user = UserInfoDaoHelper.getInstance().getUserInfoById(Long.parseLong(s));
            if(user != null){
                userInfo = new io.rong.imlib.model.UserInfo(
                        s,
                        user.getRealName(),
                        !TextUtils.isEmpty(user.getAvatar())?Uri.parse(user.getAvatar()):null
                );
                Log.i("Retrofit","数据库更新融云数据");
            }
            //异步获取
            if(mUserInfoPresenterImpl == null){
                mUserInfoPresenterImpl = new UserInfoPresenterImpl(new UserInfoInteractorImpl());
                mUserInfoPresenterImpl.attachView(mUserInfoView);
            }
            Log.i("Retrofit","异步获取他人信息");
            mUserInfoPresenterImpl.getOtherUserInfo(s);

        }
       return userInfo;
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

    }

    @Override
    public boolean onReceived(Message message, int i) {
        Log.i("RongCloud","onReceived type:"+message.getConversationType());
        if(message.getConversationType() == Conversation.ConversationType.DISCUSSION){
            return true;
        }
        RxBus.getInstance().post(new UnReadMessageEvent());
        return false;
    }
}
