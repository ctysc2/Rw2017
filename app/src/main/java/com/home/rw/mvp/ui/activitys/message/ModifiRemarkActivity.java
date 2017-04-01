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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.RemarkPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.RemarkView;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifiRemarkActivity extends BaseActivity implements RemarkView{
    private String mName;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.et1)
    EditText mEt1;

    @BindView(R.id.iv_del)
    ImageView mDelete;

    @Inject
    RemarkPresenterImpl mRemarkPresenterImpl;

    private String userId;

    @OnClick({R.id.back,
            R.id.iv_del,
            R.id.rightText
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rightText:
                if(mEt1.getText().toString().equals("")){
                    Toast.makeText(this,getString(R.string.nicknameCannotEmpty),Toast.LENGTH_SHORT).show();
                }else{
                    mRemarkPresenterImpl.beforeRequest();
                    mRemarkPresenterImpl.setRemark(userId, mEt1.getText().toString());

                }
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
        return R.layout.activity_modifi_remark;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.modifiremark));
        rightText.setText(getString(R.string.completed));
        mback.setImageResource(R.drawable.btn_back);
        mName = getIntent().getStringExtra("name");
        userId = getIntent().getStringExtra("userId");
        mEt1.setText(mName);
        mEt1.setSelection(mEt1.length());
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mRemarkPresenterImpl.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void setRemarkCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(this,getString(R.string.modifiSuccessed),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("name",mEt1.getText().toString());
            setResult(RESULT_OK,intent);
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
        Toast.makeText(this,getString(R.string.modifiFailed),Toast.LENGTH_SHORT).show();
    }
}
