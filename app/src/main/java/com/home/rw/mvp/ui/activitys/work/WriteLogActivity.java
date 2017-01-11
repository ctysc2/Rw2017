package com.home.rw.mvp.ui.activitys.work;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteLogActivity extends BaseActivity implements AlertDialogListener {
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @OnClick({

            R.id.back
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));

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

    }

    @Override
    public void initViews() {
        midText.setText(R.string.writeLog);
        mback.setImageResource(R.drawable.btn_back);
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
