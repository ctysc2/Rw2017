package com.home.rw.mvp.ui.activitys.work;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.AddLogPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.AddLogView;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.common.HostType.ADD_APPLY_OVERTIME;
import static com.home.rw.common.HostType.APPLY_DO_PASS;
import static com.home.rw.common.HostType.APPLY_DO_REJECT;
import static com.home.rw.common.HostType.DETAIL_APPLY_OVERTIME;
import static com.home.rw.common.HostType.EDIT_APPLY_OVERTIME;
import static com.home.rw.common.HostType.UPLOAD;

public class WriteLogActivity extends BaseActivity implements AlertDialogListener,AddLogView {
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.et_title)
    EditText mTitle;

    @BindView(R.id.et_content)
    EditText mContent;

    @Inject
    AddLogPresenterImpl mAddLogPresenterImpl;

    @OnClick({
            R.id.back,
            R.id.bt_commit,
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));
                break;
            case R.id.bt_commit:
                if(!checkCommitData()){
                    Toast.makeText(this,getString(R.string.checkinput),Toast.LENGTH_SHORT).show();
                    break;
                }
                mAddLogPresenterImpl.beforeRequest();
                mAddLogPresenterImpl.addLog(mTitle.getText().toString(),mContent.getText().toString());
                break;
            default:
                break;


        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_write_log;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.writeLog);
        mback.setImageResource(R.drawable.btn_back);
        mAddLogPresenterImpl.attachView(this);

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
    public void onConFirm() {
        mAlertDialog.dismiss();
        finish();
    }

    @Override
    public void onCancel() {
        mAlertDialog.dismiss();

    }
    @Override
    public void onBackPressed() {
        showOrDismissDialog();
    }
    private void showOrDismissDialog(){

        if((mAlertDialog != null) && (mAlertDialog.isShowing())){
            mAlertDialog.dismiss();
        }else{
            mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
            mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));
        }
    }

    @Override
    public void addLogCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(this,getString(R.string.commitSuccessed),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog==null) {
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
    private boolean checkCommitData(){
        boolean result = true;
        if(TextUtils.isEmpty(mTitle.getText().toString())){
            return false;
        }
        if(TextUtils.isEmpty(mContent.getText().toString())){
            return false;
        }

        return result;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAddLogPresenterImpl!=null){
            mAddLogPresenterImpl.onDestroy();
        }
    }
}
