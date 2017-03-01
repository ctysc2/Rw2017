package com.home.rw.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.home.rw.event.UnReadMessageEvent;
import com.home.rw.mvp.ui.activitys.message.ContactsActivity;
import com.home.rw.mvp.ui.activitys.rongCloud.MyExtensionModule;
import com.home.rw.utils.RxBus;

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
        setReadReceiptConversationType();
        RongIM.getInstance().enableNewComingMessageIcon(true);
        RongIM.getInstance().enableUnreadMessageIcon(true);
//        RongCallClient.getInstance().setVoIPCallListener(new IRongCallListener() {
//            @Override
//            public void onCallOutgoing(RongCallSession rongCallSession, SurfaceView surfaceView) {
//                Log.i("RongCloud","onCallOutgoing targetId:"+rongCallSession.getTargetId());
//            }
//
//            @Override
//            public void onCallConnected(RongCallSession rongCallSession, SurfaceView surfaceView) {
//                Log.i("RongCloud","onCallConnected targetId:"+rongCallSession.getTargetId());
//            }
//
//            @Override
//            public void onCallDisconnected(RongCallSession rongCallSession, RongCallCommon.CallDisconnectedReason callDisconnectedReason) {
//                Log.i("RongCloud","onCallDisconnected targetId:"+rongCallSession.getTargetId());
//            }
//
//            @Override
//            public void onRemoteUserRinging(String s) {
//                Log.i("RongCloud","onRemoteUserRinging s"+s);
//            }
//
//            @Override
//            public void onRemoteUserJoined(String s, RongCallCommon.CallMediaType callMediaType, SurfaceView surfaceView) {
//                Log.i("RongCloud","onRemoteUserJoined s"+s);
//            }
//
//            @Override
//            public void onRemoteUserInvited(String s, RongCallCommon.CallMediaType callMediaType) {
//
//            }
//
//            @Override
//            public void onRemoteUserLeft(String s, RongCallCommon.CallDisconnectedReason callDisconnectedReason) {
//
//            }
//
//            @Override
//            public void onMediaTypeChanged(String s, RongCallCommon.CallMediaType callMediaType, SurfaceView surfaceView) {
//
//            }
//
//            @Override
//            public void onError(RongCallCommon.CallErrorCode callErrorCode) {
//
//            }
//
//            @Override
//            public void onRemoteCameraDisabled(String s, boolean b) {
//
//            }
//        });
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
        return null;
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
        }else if(s.equals("1")){
            userInfo = new io.rong.imlib.model.UserInfo(
                    s,
                    "孙悟空",
                    Uri.parse("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2636222877,2825274081&fm=23&gp=0.jpg")
            );
        }else if(s.equals("1001")){
            userInfo = new io.rong.imlib.model.UserInfo(
                    s,
                    "若为置业",
                    Uri.parse("http://juqing.9duw.com/UploadPic/2016-8/201682610271922903.jpg")
            );
        }
        else if(s.equals("2")){
            userInfo = new io.rong.imlib.model.UserInfo(
                    s,
                    "猪八戒",
                    Uri.parse("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=50088fe8d3ca7bcb6976cf7ddf600006/b2de9c82d158ccbf4e8703111dd8bc3eb13541e5.jpg")
            );
        }else if(s.equals("3")){
            userInfo = new io.rong.imlib.model.UserInfo(
                    s,
                    "沙和尚",
                    Uri.parse("https://imgsa.baidu.com/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=f5e2a401dac451dae2fb04b9d7943903/b219ebc4b74543a96c11c69319178a82b9011462.jpg"));

        }else if(s.equals("4")){
            userInfo = new io.rong.imlib.model.UserInfo(
                    s,
                    "白龙马",
                    Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487750183704&di=c241129fbe42167014cc19a1c73877a9&imgtype=0&src=http%3A%2F%2Fkibey-echo.b0.upaiyun.com%2Fd90252fcbe11a6b02bf95d4708e74513.jpg"));

        }else{
            userInfo = new io.rong.imlib.model.UserInfo(
                    s,
                    "其他用户",
                    null
            );
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
