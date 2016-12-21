package com.home.rw.mvp.ui.activitys.work;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ApprovementActivity extends BaseActivity {

    boolean isAdd = false;
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @OnClick({R.id.back,
            R.id.ll_byme,
            R.id.ll_fromme,
            R.id.ll_getout,
            R.id.ll_leave,
            R.id.ll_extrawork,
            R.id.ll_wiped})
    public void onClick(View v){

        switch (v.getId()){

            case R.id.back:
                finish();
                break;
            case R.id.ll_byme:
                break;
            case R.id.ll_fromme:
                Intent intent = new Intent(this,ProposeFromMeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_getout:
                break;
            case R.id.ll_leave:
                break;
            case R.id.ll_extrawork:
                break;
            case R.id.ll_wiped:
                break;
            default:
                break;


        }


    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_approvement;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(R.string.approveLabel);
        mback.setImageResource(R.drawable.btn_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

    }
}
