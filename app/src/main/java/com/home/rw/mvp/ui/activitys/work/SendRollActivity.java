package com.home.rw.mvp.ui.activitys.work;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.home.rw.R;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class SendRollActivity extends BaseActivity implements AlertDialogListener {

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


    @OnClick({
            R.id.rl_receiver,
            R.id.rl_deadLine,
            R.id.rightText,
            R.id.iv_rollme,
            R.id.back
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));
                break;
            case R.id.rightText:
                //Intent intent = new Intent(this,WriteLogActivity.class);
                //startActivity(intent);
                break;
            case R.id.rl_receiver:
                break;
            case R.id.rl_deadLine:
                pvTime.show();
                break;
            case R.id.iv_rollme:
                Intent intent = new Intent(this,RollMeActivity.class);
                startActivity(intent);
                break;
            default:
                break;


        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_roll;
    }

    @Override
    public void initInjector() {

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
}
