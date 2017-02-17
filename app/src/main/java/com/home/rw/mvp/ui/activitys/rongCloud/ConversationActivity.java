package com.home.rw.mvp.ui.activitys.rongCloud;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.preference.Preference;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.annotation.BindValues;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.mvp.ui.activitys.LoginActivity;
import com.home.rw.mvp.ui.activitys.MainActivity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.message.AddFriendIndex;
import com.home.rw.mvp.ui.activitys.message.MyNewFriendActivity;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.push.notification.PushMessageReceiver;

@BindValues(mIsStatusTranslucent = false)
public class ConversationActivity extends BaseActivity {
    /**
     * 对方id
     */
    private String mTargetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    private boolean isFromPush = false;
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rong_content)
    FrameLayout mContent;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    private ConversationFragment mFragment;
    @OnClick({R.id.back,
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                doBack();

                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_conversation;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    private void doBack(){
        if (isFromPush) {
            isFromPush = false;
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
    public void initViews() {

        Intent intent = getIntent();
        midText.setText(intent.getData().getQueryParameter("title"));
        mback.setImageResource(R.drawable.btn_back);
        mConversationType = Conversation.ConversationType.PRIVATE;
        mTargetId = intent.getData().getQueryParameter("targetId");

        if (mTargetId != null && mTargetId.equals("10000")) {
            startActivity(new Intent(ConversationActivity.this, MyNewFriendActivity.class));
            return;
        }
        isPushMessage(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

    }
    /**
     * 判断是否是 Push 消息，判断是否需要做 connect 操作
     */
    private void isPushMessage(Intent intent) {

        if (intent == null || intent.getData() == null)
            return;
        //push
        if (intent.getData().getScheme().equals("rong") && intent.getData().getQueryParameter("isFromPush") != null) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.getData().getQueryParameter("isFromPush").equals("true")) {
                //只有收到系统消息和不落地 push 消息的时候，pushId 不为 null。而且这两种消息只能通过 server 来发送，客户端发送不了。
                //RongIM.getInstance().getRongIMClient().recordNotificationEvent(id);
                isFromPush = true;
                enterActivity();
            } else if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED)) {

                if (intent.getData().getPath().contains("conversation/system")) {
                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.putExtra("systemconversation", true);
                    startActivity(intent1);
                    return;
                }
                enterActivity();
            } else {
                if (intent.getData().getPath().contains("conversation/system")) {
                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.putExtra("systemconversation", true);
                    startActivity(intent1);
                    return;
                }
                enterFragment(mConversationType, mTargetId);
            }

        } else {
            if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        enterActivity();
                    }
                }, 300);
            } else {
                enterFragment(mConversationType, mTargetId);
            }
        }
    }
    private void enterActivity() {

        String token = PreferenceUtils.getPrefString(this,"token","");

        if (token.equals("")) {
            startActivity(new Intent(ConversationActivity.this, LoginActivity.class));

        } else {
            reconnect(token);
        }
    }
    private io.rong.imlib.model.UserInfo findUserById(String userId){
        UserInfo info = UserInfoDaoHelper.getInstance().getUserInfoById(Integer.parseInt(userId));
        io.rong.imlib.model.UserInfo userInfo = null;
//        io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(
//                String.valueOf(info.getId()),
//                info.getUserName(),
//                Uri.parse(info.getHeadUrl())
//                );
        if(userId.equals("1")){
            userInfo = new io.rong.imlib.model.UserInfo(
                    userId,
                    "陈无人",
                    Uri.parse("http://y0.ifengimg.com/e6ce10787c9a3bdb/2014/0423/re_53571adb03caf.jpg")
            );

        }else{
            userInfo = new io.rong.imlib.model.UserInfo(
                    userId,
                    "张19",
                    Uri.parse("http://img1.mp.oeeee.com/201702/03/04f372705ced49eb.jpg")
            );

        }


        return userInfo;
    }
    private void reconnect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                RongIM.getInstance().setCurrentUserInfo(findUserById(s));
                RongIM.getInstance().setMessageAttachedUserInfo(true);
                enterFragment(mConversationType, mTargetId);

            }

            @Override
            public void onError(RongIMClient.ErrorCode e) {

                enterFragment(mConversationType, mTargetId);
            }
        });

    }
    /**
     * 加载会话页面 ConversationFragmentEx 继承自 ConversationFragment
     *
     * @param mConversationType 会话类型
     * @param mTargetId         会话 Id
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        mFragment = new ConversationFragment();

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        mFragment.setUri(uri);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //xxx 为你要加载的 id
        transaction.add(R.id.rong_content, mFragment);
        transaction.commitAllowingStateLoss();

    }
}
