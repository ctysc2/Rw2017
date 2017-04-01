package com.home.rw.mvp.ui.activitys.message;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.mvp.entity.SelectEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.CreatGroupEntity;
import com.home.rw.mvp.presenter.impl.AddGroupPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.AddGroupView;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class GroupChatNamedActivity extends BaseActivity implements AddGroupView{

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.et1)
    EditText mEt1;

    private ArrayList<SelectEntity.DataEntity> receiveData;

    private String names = "";

    @Inject
    AddGroupPresenterImpl mAddGroupPresenterImpl;

    @OnClick({R.id.back,
            R.id.bt_next,
            R.id.iv_del
    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_next:
                if(mEt1.getText().toString().equals("")){
                    Toast.makeText(this,getString(R.string.groupNoEmpty),Toast.LENGTH_SHORT).show();
                    return;
                }
                mAddGroupPresenterImpl.beforeRequest();
                mAddGroupPresenterImpl.addGroup(mEt1.getText().toString(),names);
                break;
            case R.id.iv_del:
                mEt1.setText("");
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAddGroupPresenterImpl!=null)
            mAddGroupPresenterImpl.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_chat_named;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.createGroupChatName));
        mBack.setImageResource(R.drawable.btn_back);
        mAddGroupPresenterImpl.attachView(this);
        receiveData = (ArrayList<SelectEntity.DataEntity>)(getIntent().getSerializableExtra("data"));
        for(int i = 0;i<receiveData.size();i++){
            names+=receiveData.get(i).getId();
            if(i!=receiveData.size()-1)
                names+=",";
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void addGroupCompleted(CreatGroupEntity data) {
        if(data.getCode().equals("ok") &&
                data.getData() !=null){

            if(!TextUtils.isEmpty(data.getData().getId())){
                RongIM.getInstance().startConversation(this, Conversation.ConversationType.GROUP,data.getData().getId(),mEt1.getText().toString());
            }

            Toast.makeText(this,getString(R.string.createdSucceed),Toast.LENGTH_SHORT).show();
            finish();


        }

    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog ==null){
            mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_CREATE);
            mLoadDialog.show();
        }
    }

    @Override
    public void hideProgress(int reqType) {
        if(mLoadDialog !=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        Toast.makeText(this,getString(R.string.createdFailed),Toast.LENGTH_SHORT).show();
    }
}
