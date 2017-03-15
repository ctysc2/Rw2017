package com.home.rw.mvp.ui.activitys.mineme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.ModifiPasswordPresenterImpl;
import com.home.rw.mvp.presenter.impl.VerifiCodePresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.ModifiPasswordView;
import com.home.rw.mvp.view.VerifiCodeView;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;
import com.home.rw.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePassWord extends BaseActivity implements ModifiPasswordView {


    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.et1)
    EditText midText1;

    @BindView(R.id.et2)
    EditText midText2;

    @BindView(R.id.et3)
    EditText midText3;

    @BindView(R.id.tv_phoneNum)
    TextView mTvPhone;

    @Inject
    ModifiPasswordPresenterImpl mModifiPasswordPresenterImpl;


    private String phone = "";

    @OnClick({
            R.id.back,
            R.id.iv1,
            R.id.iv2,
            R.id.iv3,
            R.id.bt_commit,
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.iv1:
                midText1.setText("");
                break;
            case R.id.iv2:
                midText2.setText("");
                break;
            case R.id.iv3:
                midText3.setText("");
                break;
            case R.id.bt_commit:
                if(checkInput()){
                    mModifiPasswordPresenterImpl.beforeRequest();
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("oldPassword",midText1.getText().toString());
                    map.put("password",midText2.getText().toString());
                    mModifiPasswordPresenterImpl.modifiPassword(map);
                }
                break;
            default:
                break;

        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pass_word;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.chagePsw);
        mback.setImageResource(R.drawable.btn_back);
        mModifiPasswordPresenterImpl.attachView(this);
        //首先从数据库获取数据
        UserInfo user = UserInfoDaoHelper.getInstance().getUserInfoById(PreferenceUtils.getPrefLong(this,"ID",0));
        if(user!=null){
            phone = user.getPhone();
        }
        if(phone!=null && !phone.equals("")){
            mTvPhone.setText(phone.substring(0,2)+"***"+phone.substring(5,phone.length()));
        }
        else{
            mTvPhone.setText("");
        }

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
    protected void onDestroy() {
        super.onDestroy();

        if(mModifiPasswordPresenterImpl!=null){
            mModifiPasswordPresenterImpl.onDestroy();
        }


    }
    private boolean checkInput(){

        String errorMsg = "";

        if(midText1.getText().toString().equals("")||
           midText2.getText().toString().equals("")||
           midText3.getText().toString().equals("")){
            errorMsg = getString(R.string.inputCannotBeEmpty);
        }else if(!midText2.getText().toString().equals(midText3.getText().toString())){
            errorMsg = getString(R.string.notSamePassword);
        }

        if(errorMsg.equals("")){
            return true;
        }else{
            Toast.makeText(this,errorMsg,Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void modifiPasswordCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(this,getString(R.string.modifiPassedSucceed),Toast.LENGTH_SHORT).show();
            finish();
        }else if(data.getCode().equals("101")){
            Toast.makeText(this,getString(R.string.oldPasswordWrong),Toast.LENGTH_SHORT).show();
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
        switch (reqType){
            case HostType.MODIFI_PASSWORD:
                Toast.makeText(this,getString(R.string.modifiPassedFailed),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
