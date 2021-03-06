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
        Intent intent;
        switch (v.getId()){

            case R.id.back:
                finish();
                break;
            case R.id.ll_byme:
                intent = new Intent(this,ApprovedByMeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_fromme:
                intent = new Intent(this,ProposeFromMeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_getout:
                intent = new Intent(this,GetOutActivity.class);
                intent.putExtra("entryType","edit");
                startActivity(intent);
                break;
            case R.id.ll_leave:
                intent = new Intent(this,AskForLeaveActivity.class);
                intent.putExtra("entryType","edit");
                startActivity(intent);
                break;
            case R.id.ll_extrawork:
                intent = new Intent(this,ExtraWorkActivity.class);
                intent.putExtra("entryType","edit");
                startActivity(intent);
                break;
            case R.id.ll_wiped:
                intent = new Intent(this,WipedActivity.class);
                intent.putExtra("entryType","edit");
                startActivity(intent);
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
