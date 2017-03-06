package com.home.rw.mvp.ui.activitys.work;

import android.content.Context;
import android.content.Intent;
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

import com.bigkoo.pickerview.TimePickerView;
import com.home.rw.R;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.MeetingSelectTempEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.SendRollPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.message.MeetingSelectActivity;
import com.home.rw.mvp.ui.activitys.message.PreviewCallActivity;
import com.home.rw.mvp.view.SendRollView;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SendRollActivity extends BaseActivity implements AlertDialogListener,SendRollView {

    //时间选择器
    private TimePickerView pvTime;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.tv_deadline)
    TextView mDeadLine;

    @BindView(R.id.content)
    EditText mContent;

    @Inject
    SendRollPresenterImpl mSendRollPresenterImpl;

    private ArrayList<MeetingSelectTempEntity> selectedData = new ArrayList<>();
    @OnClick({
            R.id.rl_receiver,
            R.id.rl_deadLine,
            R.id.rightText,
            R.id.iv_rollme,
            R.id.back
    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));
                break;
            case R.id.rightText:
                if(!checkCommitData()){
                    Toast.makeText(this,getString(R.string.checkinput),Toast.LENGTH_SHORT).show();
                    break;
                }
                resolveSendData();
                break;
            case R.id.rl_receiver:
                intent = new Intent(SendRollActivity.this,MeetingSelectActivity.class);
                intent.putExtra("selectedData",selectedData);
                intent.putExtra("entry","roll");
                startActivity(intent);
                break;
            case R.id.rl_deadLine:
                pvTime.show();
                break;
            case R.id.iv_rollme:
                intent = new Intent(this,RollMeActivity.class);
                startActivity(intent);
                break;
            default:
                break;


        }

    }

    private void resolveSendData() {
        HashMap<String,Object> map = new HashMap<>();
        String receiveUsers = "";
        map.put("beginTime",DateUtils.getTime(new Date()));
        map.put("endTime",mDeadLine.getText().toString());
        map.put("content",mContent.getText().toString());
        for(int i = 0;i<selectedData.size();i++){
            receiveUsers+=selectedData.get(i).getId();
            if(i!=selectedData.size()-1)
                receiveUsers+=",";
        }
        map.put("receiveUsers",receiveUsers);
        mSendRollPresenterImpl.beforeRequest();
        mSendRollPresenterImpl.sendRoll(map);
    }
    private boolean checkCommitData(){
        if(TextUtils.isEmpty(mDeadLine.getText().toString()) || mDeadLine.getText().equals(getString(R.string.deadlinehint))){
            return false;
        }

        if(selectedData.size() == 0)
            return false;

        if(TextUtils.isEmpty(mContent.getText().toString())){
            return false;
        }
        return true;

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_send_roll;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.rollLabel);
        rightText.setText(R.string.send);
        mback.setImageResource(R.drawable.btn_back);

        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);

        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                mDeadLine.setText(DateUtils.getTime(date));

            }
        });
        mSendRollPresenterImpl.attachView(this);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent == null)
            return;
        selectedData = (ArrayList<MeetingSelectTempEntity>)(intent.getSerializableExtra("newdata"));

    }

    @Override
    public void sendRollComplete(BaseEntity data) {
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
        if(mLoadDialog!=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
    }
}
