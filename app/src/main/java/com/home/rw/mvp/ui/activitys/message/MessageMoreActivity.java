package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageMoreActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;



    @OnClick({R.id.back,
            R.id.iv_qunliao,
            R.id.iv_shangwu,
            R.id.iv_addfriend,
            R.id.iv_roll,

    })
    public void onClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.iv_qunliao:
                break;
            case R.id.iv_shangwu:
                intent = new Intent(this, BusinessPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_addfriend:
                break;
            case R.id.iv_roll:
                intent = new Intent(this, SendRollActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_more;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(R.string.more);
        mback.setImageResource(R.drawable.btn_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
