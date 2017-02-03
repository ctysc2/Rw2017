package com.home.rw.mvp.ui.activitys.message;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.KeyBoardUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupChatNamedActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.et1)
    EditText mEt1;

    private ArrayList<SelectEntity.DataEntity> receiveData;

    private String names = "";
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
                Toast.makeText(this,names,Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_del:
                mEt1.setText("");
                break;
            default:
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_chat_named;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.createGroupChatName));
        mBack.setImageResource(R.drawable.btn_back);
        receiveData = (ArrayList<SelectEntity.DataEntity>)(getIntent().getSerializableExtra("data"));
        for (int i = 0;i<receiveData.size();i++){
            names+=receiveData.get(i).getName()+",";
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
}
