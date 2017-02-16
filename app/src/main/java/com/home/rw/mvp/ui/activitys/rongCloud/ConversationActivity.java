package com.home.rw.mvp.ui.activitys.rongCloud;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.annotation.BindValues;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.DimenUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;
@BindValues(mIsStatusTranslucent = false)
public class ConversationActivity extends BaseActivity {
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
                finish();
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

    public void initViews() {

        Intent intent = getIntent();
        midText.setText(intent.getData().getQueryParameter("title"));
        mback.setImageResource(R.drawable.btn_back);
        enterFragment(Conversation.ConversationType.PRIVATE,intent.getData().getQueryParameter("targetId"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
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
