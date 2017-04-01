package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.MyFriendEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.presenter.impl.MyFriendPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.adapters.HomePagerAdapter;
import com.home.rw.mvp.ui.adapters.MyFriendAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.view.MyFriendView;
import com.home.rw.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class MyFriendActivity extends BaseActivity implements MyFriendView{

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    private ArrayList<MessageCommonEntity> dataSource  = new ArrayList<>();

    private MyFriendAdapter mAdapter;

    private boolean mIsLoadingMore;
    View mHeader;

    @Inject
    MyFriendPresenterImpl mMyFriendPresenterImpl;

    @OnClick({R.id.back,
            R.id.rightText,

    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rightText:
                intent = new Intent(this, AddFriendIndex.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_friend;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.haoYou));
        rightText.setText(getString(R.string.addFriend));
        mMyFriendPresenterImpl.attachView(this);
        mBack.setImageResource(R.drawable.btn_back);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mMyFriendPresenterImpl.getMyFriendList();

            }
        });




        mHeader = LayoutInflater.from(this).inflate(R.layout.cell_friend_top, null, false);


        mAdapter = new MyFriendAdapter(dataSource,this);
        mAdapter.setIsShowHeader(true);
        mAdapter.setHeaderView(mHeader);
        mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyFriendActivity.this,MyNewFriendActivity.class));
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                UserInfo user = new UserInfo();
                user.setAvatar(dataSource.get(position).getAvatar());

                user.setId(Long.parseLong(dataSource.get(position).getUserId()));
                user.setPhone(dataSource.get(position).getPhone());
                user.setRealName(dataSource.get(position).getRealname());
                user.setNickName(dataSource.get(position).getNickname());
                UserInfoDaoHelper.getInstance().insertUserInfo(user);
                RongIM.getInstance().startConversation(MyFriendActivity.this, Conversation.ConversationType.PRIVATE,String.valueOf(dataSource.get(position).getUserId()),dataSource.get(position).getNickname() == null?dataSource.get(position).getRealname():dataSource.get(position).getNickname());
            }
        });

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

        mMyFriendPresenterImpl.beforeRequest();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMyFriendPresenterImpl.getMyFriendList();
    }

    @Override
    public void getMyFriendCompleted(com.home.rw.mvp.entity.message.MyFriendEntity data) {
        if(data.getCode().equals("ok")){
            dataSource = data.getData().getFriends();
            mAdapter.setList(dataSource);
            mAdapter.notifyDataSetChanged();

            TextView tv = (TextView)mHeader.findViewById(R.id.tv_friend_num);
            if(data.getData().getWaitAppFriendNum().equals("0")){
                tv.setVisibility(View.INVISIBLE);
            }else{
                tv.setVisibility(View.VISIBLE);
                tv.setText(data.getData().getWaitAppFriendNum());
            }


        }
    }

    @Override
    public void showProgress(int reqType) {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress(int reqType) {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        mRefresh.setRefreshing(false);
        Toast.makeText(this,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();
    }
}
