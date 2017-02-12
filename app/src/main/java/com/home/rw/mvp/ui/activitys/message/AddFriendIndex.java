package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.common.Const.TYPE_ADD;
import static com.home.rw.common.Const.TYPE_NORMAL;

public class AddFriendIndex extends BaseActivity {


    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @OnClick({R.id.back,
            R.id.ll_contact,
            R.id.ll_org,
            R.id.ll_myteam,

    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.ll_contact:
                intent = new Intent(this,ContactsActivity.class);
                intent.putExtra("type",TYPE_ADD);
                startActivity(intent);
                break;
            case R.id.ll_org:
                intent = new Intent(this,OriganizationActivity.class);
                intent.putExtra("type",TYPE_ADD);
                startActivity(intent);
                break;
            case R.id.ll_myteam:
                intent = new Intent(this,MyTeamActivity.class);
                intent.putExtra("type",TYPE_ADD);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_friend_index;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.addFriend));
        mBack.setImageResource(R.drawable.btn_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
