package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.BusinessMeetingPhoneEntity;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.message.BusineseCallEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.presenter.impl.BusinessCallPrensenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.activitys.social.CommListActivity;
import com.home.rw.mvp.ui.adapters.BusinessMeetingAdapter;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.view.BusinessCallView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import static com.home.rw.common.Const.SEARCH_MYTEAM_ADD;
import static com.home.rw.common.Const.SEARCH_MYTEAM_SELECT;
import static com.home.rw.common.Const.SEARCH_RECENT;
import static com.home.rw.common.Const.TYPE_ADD;

public class BusinessPhoneActivity extends BaseActivity implements BusinessCallView{

    private BusinessMeetingAdapter mAdapter;

    private ArrayList<MessageCommonEntity> dataSource  = new ArrayList<>();

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @Inject
    BusinessCallPrensenterImpl mBusinessCallPrensenterImpl;
    @OnClick({R.id.back,
            R.id.bt_meeting,
            R.id.search,
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_meeting:
                Intent intent = new Intent(this,PreviewCallActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                intent = new Intent(this,SearchActivity.class);
                intent.putExtra("type",SEARCH_RECENT);
                intent.putExtra("data",dataSource);
                startActivity(intent);
                overridePendingTransition(R.anim.search_fade_in,R.anim.search_fade_out);
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_business_phone;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.shangWu));
        mback.setImageResource(R.drawable.btn_back);
        mBusinessCallPrensenterImpl.attachView(this);
        initRecycleView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBusinessCallPrensenterImpl.getBusinessCall();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBusinessCallPrensenterImpl!=null)
            mBusinessCallPrensenterImpl.onDestroy();
    }

    private void initRecycleView() {


        mAdapter = new BusinessMeetingAdapter(dataSource,this);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(dataSource.get(position).getIsFriend() !=null && dataSource.get(position).getIsFriend().equals("1")){
                    UserInfo user = new UserInfo();
                    user.setAvatar(dataSource.get(position).getAvatar());
                    user.setId(Long.parseLong(dataSource.get(position).getUserId()));
                    user.setPhone(dataSource.get(position).getPhone());
                    user.setRealName(dataSource.get(position).getRealname());
                    user.setNickName(dataSource.get(position).getNickname());
                    UserInfoDaoHelper.getInstance().insertUserInfo(user);
                    RongIM.getInstance().startConversation(BusinessPhoneActivity.this, Conversation.ConversationType.PRIVATE,String.valueOf(dataSource.get(position).getUserId()),dataSource.get(position).getNickname()==null?dataSource.get(position).getRealname():dataSource.get(position).getNickname());
                }else{
                    MessageCommonEntity entity = dataSource.get(position);
                    CallListEntity.DataEntity data = new CallListEntity.DataEntity();
                    data.setName(entity.getNickname() == null ?entity.getRealname():entity.getNickname());
                    data.setAvatar(entity.getAvatar());
                    Intent intent = new Intent(BusinessPhoneActivity.this,PreviewCallActivity.class);
                    intent.putExtra("data",data);
                    startActivity(intent);
                }

            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public void getBusinessCallCompleted(BusineseCallEntity data) {
        if(data.getCode().equals("ok")){
            dataSource = data.getData();
            mAdapter.setList(dataSource);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress(int reqType) {

    }

    @Override
    public void hideProgress(int reqType) {

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {

    }
}
