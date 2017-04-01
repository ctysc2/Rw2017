package com.home.rw.mvp.ui.activitys.rongCloud;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.preference.Preference;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.annotation.BindValues;
import com.home.rw.greendao.entity.Friends;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.FriendsDaoHelper;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.presenter.impl.UserInfoPresenterImpl;
import com.home.rw.mvp.ui.activitys.LoginActivity;
import com.home.rw.mvp.ui.activitys.MainActivity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.message.AddFriendIndex;
import com.home.rw.mvp.ui.activitys.message.MyNewFriendActivity;
import com.home.rw.mvp.ui.activitys.message.PreviewCallActivity;
import com.home.rw.mvp.ui.activitys.work.RollMeActivity;
import com.home.rw.mvp.view.UserInfoView;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.KeyBoardUtils;
import com.home.rw.utils.PreferenceUtils;

import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.model.Event;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.push.notification.PushMessageReceiver;

@BindValues(mIsStatusTranslucent = false)
public class ConversationActivity extends BaseActivity implements UserInfoView{
    /**
     * 对方id
     */
    private String mTargetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    private boolean isFromPush = false;
    private String mTitle;
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;


    @BindView(R.id.rong_content)
    FrameLayout mContent;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Inject
    UserInfoPresenterImpl mUserInfoPresenterImpl;

    private ConversationFragment mFragment;
    @OnClick({R.id.back,
            R.id.rightText,
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                doBack();
                break;
            case R.id.rightText:
                Intent intent = new Intent(this,PreviewCallActivity.class);
                com.home.rw.greendao.entity.UserInfo user = UserInfoDaoHelper.getInstance().getUserInfoById(Long.parseLong(mTargetId));
                CallListEntity.DataEntity entity = new CallListEntity.DataEntity();
                entity.setId(Integer.parseInt(mTargetId));
                entity.setAvatar("");
                entity.setName(mTitle);
                if(user != null){
                    entity.setAvatar(user.getAvatar());
                }
                intent.putExtra("data",entity);
                startActivity(intent);
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
        mActivityComponent.inject(this);
    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    private void doBack(){
        hintKbTwo();
        if (isFromPush) {
            isFromPush = false;
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
    public void initViews() {

        Intent intent = getIntent();
        mUserInfoPresenterImpl.attachView(this);
        mTitle = intent.getData().getQueryParameter("title");
        midText.setText(mTitle);
        mback.setImageResource(R.drawable.btn_back);

        mConversationType = Conversation.ConversationType.valueOf(intent.getData()
                .getLastPathSegment().toUpperCase(Locale.getDefault()));
        mTargetId = intent.getData().getQueryParameter("targetId");
        if(!mConversationType.equals(Conversation.ConversationType.GROUP))
            rightText.setText(R.string.doPhone);
        else
            rightText.setText("");
        if (mTargetId != null && (mTargetId.equals("1001")||(mTargetId.equals("1000")))) {
            Intent friendIntent = new Intent(ConversationActivity.this, MyNewFriendActivity.class);
            friendIntent.putExtra("mTargetId",mTargetId);
            friendIntent.putExtra("mConversationType",mConversationType);
            startActivity(friendIntent);
            RongIMClient.getInstance().clearMessagesUnreadStatus(mConversationType, mTargetId, null);
            finish();
            return;
        }
        if (mTargetId != null && mTargetId.equals("1002")) {
            Intent rollIntent = new Intent(ConversationActivity.this, RollMeActivity.class);
            rollIntent.putExtra("mTargetId",mTargetId);
            rollIntent.putExtra("mConversationType",mConversationType);
            startActivity(rollIntent);
            RongIMClient.getInstance().clearMessagesUnreadStatus(mConversationType, mTargetId, null);

            finish();
            return;
        }
        if(mConversationType == Conversation.ConversationType.PRIVATE){
            Friends friends = FriendsDaoHelper.getInstance().getFriendById(Long.parseLong(mTargetId));
            if(friends == null){
                Intent previewIntent = new Intent(this,PreviewCallActivity.class);
                CallListEntity.DataEntity entity = new CallListEntity.DataEntity();
                UserInfo user = UserInfoDaoHelper.getInstance().getUserInfoById(Long.parseLong(mTargetId));
                if(user!=null){
                    entity.setId(Integer.parseInt(mTargetId));
                    entity.setAvatar(user.getAvatar());
                    entity.setName(!TextUtils.isEmpty(user.getNickName())?user.getNickName():user.getRealName());
                    entity.setPhone(user.getPhone());
                }
                previewIntent.putExtra("data",entity);
                startActivity(previewIntent);
                finish();
                return;
            }
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

    private void reconnect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
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
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void getUserInfoCompleted(UserInfoEntity data) {
        if(data.getCode().equals("ok")){
            com.home.rw.greendao.entity.UserInfo user = UserInfoDaoHelper.getInstance().parseEntity2UserInfo(data.getData());
            UserInfoDaoHelper.getInstance().insertUserInfo(user);
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
}
