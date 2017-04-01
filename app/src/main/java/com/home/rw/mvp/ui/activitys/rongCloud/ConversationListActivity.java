package com.home.rw.mvp.ui.activitys.rongCloud;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.greendao.entity.Friends;
import com.home.rw.greendaohelper.FriendsDaoHelper;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.presenter.impl.MyFriendPresenterImpl;
import com.home.rw.mvp.ui.activitys.LoginActivity;
import com.home.rw.mvp.ui.activitys.MainActivity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.MyFriendView;
import com.home.rw.utils.PreferenceUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class ConversationListActivity extends BaseActivity{

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    private boolean isFromPush = false;

    private ConversationListFragment mConversationListFragment = null;

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
    private void doBack(){
            startActivity(new Intent(this, MainActivity.class));
            finish();
    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_conversation_list;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.conversationList);
        mback.setImageResource(R.drawable.btn_back);
        Intent intent  = getIntent();
        if (intent !=null &&
                intent.getData()!=null &&
                intent.getData().getScheme() != null &&
                intent.getData().getScheme().equals("rong") && intent.getData().getQueryParameter("push") != null) {
            isFromPush = true;
            enterActivity();

        }else{
            enterFragment();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
    private void enterActivity() {

        String token = PreferenceUtils.getPrefString(this,"token","");

        if (token.equals("")) {
            startActivity(new Intent(ConversationListActivity.this, LoginActivity.class));

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
                enterFragment();

            }

            @Override
            public void onError(RongIMClient.ErrorCode e) {

            }
        });

    }
    /**
     * 加载会话页面 ConversationFragmentEx 继承自 ConversationFragment
     */
    private void enterFragment() {

        mConversationListFragment = new ConversationListFragment();

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                .build();

        mConversationListFragment.setUri(uri);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //xxx 为你要加载的 id
        transaction.add(R.id.rong_content, mConversationListFragment);
        transaction.commitAllowingStateLoss();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //mConversationListFragment.onRestoreUI();
    }

}
