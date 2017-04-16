package com.home.rw.mvp.ui.activitys.mineme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.FeedBackPresenterImpl;
import com.home.rw.mvp.presenter.impl.TopicFeedPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.FeedBackView;
import com.home.rw.mvp.view.TopicFeedbackView;
import com.home.rw.utils.DialogUtils;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity implements FeedBackView,TopicFeedbackView{

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.et_feedback)
    EditText mContent;

    @Inject
    FeedBackPresenterImpl mFeedBackPresenterImpl;

    @Inject
    TopicFeedPresenterImpl mTopicFeedPresenterImpl;

    private int reqType;

    private String mTopicId;
    @OnClick({
            R.id.back,
            R.id.bt_commit
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_commit:
                if(mContent.getText().toString().equals("")){
                    Toast.makeText(this,getString(R.string.checkinput),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mTopicId)){
                    mFeedBackPresenterImpl.beforeRequest();
                    mFeedBackPresenterImpl.setReqType(reqType);
                    mFeedBackPresenterImpl.sendFeedBack(mContent.getText().toString());
                }else{
                    mTopicFeedPresenterImpl.beforeRequest();
                    HashMap<String,String> map = new HashMap<>();
                    map.put("topicId",mTopicId);
                    map.put("content",mContent.getText().toString());
                    mTopicFeedPresenterImpl.sendTopicFeed(map);
                }

                break;
            default:
                break;

        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        reqType = intent.getIntExtra("entry", HostType.MY_FEEDBACK);
        mTopicId = intent.getStringExtra("mTopicId");
        midText.setText(R.string.FeedBack);
        mback.setImageResource(R.drawable.btn_back);
        mFeedBackPresenterImpl.attachView(this);
        mTopicFeedPresenterImpl.attachView(this);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public void sendFeedBackCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(this,getString(R.string.commitSuccessed),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_UPDATE);
            mLoadDialog.show();
        }
    }

    @Override
    public void hideProgress(int reqType) {
        if(mLoadDialog != null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mFeedBackPresenterImpl!=null){
            mFeedBackPresenterImpl.onDestroy();
        }
    }

    @Override
    public void sendTopicFeedbackCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(this,getString(R.string.commitSuccessed),Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
