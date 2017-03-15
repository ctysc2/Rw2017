package com.home.rw.mvp.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.event.BeforeReadEvent;
import com.home.rw.event.UnReadMessageEvent;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.BusinessMeetingPhoneEntity;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.MessegeMainEntity;
import com.home.rw.mvp.ui.activitys.increment.MeetingAppointmentListActivity;
import com.home.rw.mvp.ui.activitys.increment.TempActivity;
import com.home.rw.mvp.ui.activitys.lock.LockMainActivity;
import com.home.rw.mvp.ui.activitys.message.AddFriendIndex;
import com.home.rw.mvp.ui.activitys.message.BusinessPhoneActivity;
import com.home.rw.mvp.ui.activitys.message.CompanyNoticeActivity;
import com.home.rw.mvp.ui.activitys.message.LandMarkNotice;
import com.home.rw.mvp.ui.activitys.message.MessageMoreActivity;
import com.home.rw.mvp.ui.activitys.message.ModifiRemarkActivity;
import com.home.rw.mvp.ui.activitys.message.MyFriendActivity;
import com.home.rw.mvp.ui.activitys.message.OriganizationActivity;
import com.home.rw.mvp.ui.activitys.message.PreviewCallActivity;
import com.home.rw.mvp.ui.activitys.rongCloud.ConversationListActivity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.activitys.social.OthersDetailActivity;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.HomePagerAdapter;
import com.home.rw.mvp.ui.adapters.MessegeMainAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.utils.RxBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;
import static com.home.rw.common.Const.TYPE_ADD;
import static com.home.rw.common.Const.TYPE_NORMAL;

/**
 * Created by cty on 2016/12/13.
 */

public class MessageFragment extends BaseFragment {

    private MessegeMainAdapter mAdapter;

    private int mSelectedPosition;

    private final int TYPE_REMARK = 0;

    @BindView(R.id.back)
    ImageButton mScan;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @Inject
    Activity mActivity;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    ArrayList<MessegeMainEntity.DataEntity> dataSource = new ArrayList<>();

    @OnClick({R.id.rightText,
            R.id.back,
    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.rightText:
                intent = new Intent(mActivity, MessageMoreActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                intent = new Intent(mActivity,LockMainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = RxBus.getInstance().toObservable(UnReadMessageEvent.class)
                .subscribe(new Action1<UnReadMessageEvent>() {
                    @Override
                    public void call(UnReadMessageEvent unread) {
                        RongIMClient.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
                            @Override
                            public void onSuccess(Integer integer) {
                                mAdapter.updateChatNum(integer);
                            }
                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {

                            }
                        });
                    }
                });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        midText.setText(R.string.messageTitle);
        rightText.setText(R.string.more);
        mScan.setImageResource(R.drawable.btn_scan);
        initRecycleView();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initRecycleView() {

        ArrayList<MessegeMainEntity.DataEntity> subData = new ArrayList<>();

        MessegeMainEntity.DataEntity child0 = new MessegeMainEntity.DataEntity(
                -100,
                0,
                "新的消息",
                null,
                null,
                null,
                false
        );

        MessegeMainEntity.DataEntity child1 = new MessegeMainEntity.DataEntity(
                -1,
                0,
                "商务电话",
                "最近通话:小李",
                "10-28",
                null,
                false
        );
        MessegeMainEntity.DataEntity child2 = new MessegeMainEntity.DataEntity(
                -2,
                0,
                "我的好友",
                null,
                null,
                null,
                false
        );
        MessegeMainEntity.DataEntity child3 = new MessegeMainEntity.DataEntity(
                -3,
                0,
                "置地公告",
                null,
                "10-28",
                null,
                false
        );
        MessegeMainEntity.DataEntity child4 = new MessegeMainEntity.DataEntity(
                -4,
                0,
                "公司公告",
                null,
                "10-28",
                null,
                false
        );
        MessegeMainEntity.DataEntity child5 = new MessegeMainEntity.DataEntity(
                -5,
                0,
                "企业通讯录",
                null,
                null,
                null,
                false
        );
        MessegeMainEntity.DataEntity child6 = new MessegeMainEntity.DataEntity(
                -6,
                0,
                "常用联系人",
                null,
                null,
                subData,
                false
        );


        MessegeMainEntity.DataEntity subchild1 = new MessegeMainEntity.DataEntity(
                101,
                0,
                "张依旧",
                "13736239384",
                "10-28",
                null,
                false
        );
        MessegeMainEntity.DataEntity subchild2 = new MessegeMainEntity.DataEntity(
                102,
                0,
                "陈无人",
                "13947589484",
                "10-29",
                null,
                false
        );
        MessegeMainEntity.DataEntity subchild3 = new MessegeMainEntity.DataEntity(
                103,
                0,
                "导师",
                "15543245867",
                "10-28",
                null,
                false
        );

        subData.add(subchild1);
        subData.add(subchild2);
        subData.add(subchild3);
        subData.add(subchild1);
        subData.add(subchild2);
        subData.add(subchild3);
        subData.add(subchild2);
        subData.add(subchild3);
        subData.add(subchild1);
        subData.add(subchild1);
        subData.add(subchild2);
        subData.add(subchild2);
        subData.add(subchild3);
        subData.add(subchild1);
        subData.add(subchild3);
        dataSource.add(child0);
        dataSource.add(child1);
        dataSource.add(child3);
        dataSource.add(child4);
        dataSource.add(child5);
        dataSource.add(child2);
        dataSource.add(child6);



        saveSetions();

        mAdapter = new MessegeMainAdapter(dataSource,mActivity);
        mAdapter.setOnRemarkClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mSelectedPosition = position;
                Intent intent = new Intent(mActivity, ModifiRemarkActivity.class);
                intent.putExtra("name",dataSource.get(position).getTitle());
                startActivityForResult(intent,TYPE_REMARK);
            }
        });

        mAdapter.setOnDetailClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mSelectedPosition = position;
                FacusListEntity.DataEntity entity = new FacusListEntity.DataEntity();

                Intent intent = new Intent(mActivity, OthersDetailActivity.class);

                startActivity(intent);
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MessegeMainEntity.DataEntity entity = dataSource.get(position);
                mSelectedPosition = position;
                if(entity.getChilds()!=null){
                    if(entity.isExpanded()){
                        dataSource.removeAll(entity.getChilds());
                      entity.setExpanded(false);
                    }else{
                        dataSource.addAll(position+1,entity.getChilds());
                      entity.setExpanded(true);
                    }
                   mAdapter.notifyDataSetChanged();
                }else {
                    Intent intent;
                    switch (entity.getId()){
                        case -100:
                            startActivity(new Intent(mActivity, ConversationListActivity.class));
                            break;
                        case -1:
                            startActivity(new Intent(mActivity, BusinessPhoneActivity.class));
                            break;
                        case -2:
                            startActivity(new Intent(mActivity, MyFriendActivity.class));
                            break;
                        case -3:
                            startActivity(new Intent(mActivity, LandMarkNotice.class));
                            break;
                        case -4:
                            startActivity(new Intent(mActivity, CompanyNoticeActivity.class));
                            break;
                        case -5:
                            intent = new Intent(mActivity, OriganizationActivity.class);
                            intent.putExtra("type",TYPE_NORMAL);
                            startActivity(intent);
                            break;
                        default:
                            CallListEntity.DataEntity data = new CallListEntity.DataEntity();
                            data.setName(entity.getTitle());
                            data.setAvatar(entity.getAvatar());
                            data.setId(entity.getId());
                            intent = new Intent(mActivity,PreviewCallActivity.class);
                            intent.putExtra("data",data);
                            startActivity(intent);
                            break;

                    }
                }
            }
        });

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }


    //遍历并保存setion属性
    private void saveSetions(){
        for(int i = 0;i<dataSource.size();i++){
            MessegeMainEntity.DataEntity child = dataSource.get(i);
            child.setType(0);
            if(child.getChilds()!=null){
                for(int j = 0;j<child.getChilds().size();j++){
                    MessegeMainEntity.DataEntity subChild = child.getChilds().get(j);
                    subChild.setType(1);
                }
            }


        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TYPE_REMARK:
                if(resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    dataSource.get(mSelectedPosition).setTitle(name);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        RongIMClient.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                mAdapter.updateChatNum(integer);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
}
