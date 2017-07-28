package com.home.rw.mvp.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.vision.text.Text;
import com.home.rw.R;
import com.home.rw.event.BeforeReadEvent;
import com.home.rw.event.UnReadMessageEvent;
import com.home.rw.greendao.entity.Friends;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.FriendsDaoHelper;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.BusinessMeetingPhoneEntity;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.MessegeMainEntity;
import com.home.rw.mvp.entity.message.MainBusinessEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.presenter.impl.BusinessCallPrensenterImpl;
import com.home.rw.mvp.presenter.impl.MainMessagePresenterImpl;
import com.home.rw.mvp.presenter.impl.MyFriendPresenterImpl;
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
import com.home.rw.mvp.view.MainMessageView;
import com.home.rw.mvp.view.MyFriendView;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.RxBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;
import static com.home.rw.common.Const.TYPE_ADD;
import static com.home.rw.common.Const.TYPE_NORMAL;

/**
 * Created by cty on 2016/12/13.
 */

public class MessageFragment extends BaseFragment implements MainMessageView,MyFriendView {

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
    @Inject
    MyFriendPresenterImpl mMyFriendPresenterImpl;
    @Inject
    MainMessagePresenterImpl mMainMessagePresenterImpl;

    ArrayList<MessegeMainEntity.DataEntity> dataSource = new ArrayList<>();

    private boolean isExpanded = false;


    private RelativeLayout mContainerChangYong;

    private RelativeLayout mContainerQiYe;

    private RelativeLayout mContainerShangWu;

    private RelativeLayout mContainerZhiDi;

    private RelativeLayout mContainerNewMessage;

    private RelativeLayout mContainerCompany;

    private RelativeLayout mContainerFriend;


    private View mRedPointZhidi;

    private View mRedPointMessage;

    private View mRedPointGongsi;

    private View mRedPointHaoyou;


    private TextView mZhidiDate;

    private TextView mGongsiDate;

    private TextView mShangwuRecent;

    private TextView mShangwuRight;

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
        mMainMessagePresenterImpl.attachView(this);
        mMyFriendPresenterImpl.attachView(this);
        mScan.setImageResource(R.drawable.btn_scan);
        initRecycleView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMyFriendPresenterImpl!=null)
            mMyFriendPresenterImpl.onDestroy();
        if(mMainMessagePresenterImpl!=null)
            mMainMessagePresenterImpl.onDestroy();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            mMainMessagePresenterImpl.getMainMessage();
            mMyFriendPresenterImpl.getMyFriendList();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initRecycleView() {

        //saveSetions();
        mAdapter = new MessegeMainAdapter(dataSource,mActivity);
        View headview = LayoutInflater.from(mActivity).inflate(R.layout.cell_message_header,null);

        //red point
        mRedPointZhidi = (View)headview.findViewById(R.id.v_red_point_zhidi);
        mRedPointMessage = (View)headview.findViewById(R.id.v_red_point_message);
        mRedPointGongsi = (View)headview.findViewById(R.id.v_red_point_gongsi);
        mRedPointHaoyou = (View)headview.findViewById(R.id.v_red_point_haoyou);


        mContainerChangYong = (RelativeLayout) headview.findViewById(R.id.rl_changyong);
        mContainerQiYe = (RelativeLayout) headview.findViewById(R.id.rl_qiye);
        mContainerShangWu = (RelativeLayout) headview.findViewById(R.id.rl_shansgwu);


        mContainerZhiDi = (RelativeLayout)  headview.findViewById(R.id.rl_zhidi);
        mContainerNewMessage = (RelativeLayout) headview.findViewById(R.id.rl_new_message);
        mContainerCompany = (RelativeLayout)  headview.findViewById(R.id.rl_company);
        mContainerFriend = (RelativeLayout) headview.findViewById(R.id.rl_friend);


       mZhidiDate  = (TextView) headview.findViewById(R.id.tv_zhidi_date);

       mGongsiDate = (TextView) headview.findViewById(R.id.tv_gongsi_date);

       mShangwuRecent = (TextView) headview.findViewById(R.id.tv_line2_shangwu);

        mShangwuRight = (TextView) headview.findViewById(R.id.tv_right_shangwu);


        mContainerZhiDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, LandMarkNotice.class));
            }
        });

        mContainerNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ConversationListActivity.class));
            }
        });

        mContainerCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, CompanyNoticeActivity.class));
            }
        });

        mContainerFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MyFriendActivity.class));
            }
        });

        mContainerChangYong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = !isExpanded;
                openOrClose();
            }
        });

        mContainerQiYe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(mActivity, OriganizationActivity.class);
                    intent.putExtra("type",TYPE_NORMAL);
                    startActivity(intent);
            }
        });

        mContainerShangWu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, BusinessPhoneActivity.class));
            }
        });
        mAdapter.setIsShowHeader(true);
        mAdapter.setHeaderView(headview);
        mAdapter.setOnRemarkClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mSelectedPosition = position;
                Intent intent = new Intent(mActivity, ModifiRemarkActivity.class);
                intent.putExtra("name",dataSource.get(position).getTitle());
                intent.putExtra("userId",String.valueOf(dataSource.get(position).getId()));
                startActivityForResult(intent,TYPE_REMARK);
            }
        });

        mAdapter.setOnDetailClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mSelectedPosition = position;
                MessegeMainEntity.DataEntity source = dataSource.get(position);
                FacusListEntity.DataEntity.ResLst entity = new FacusListEntity.DataEntity.ResLst();
                entity.setSupportNum(source.getSupportNum());
                entity.setRealname(source.getTitle());
                entity.setFocus(source.getFocus());
                entity.setCompany(source.getCompany());
                entity.setUserId(String.valueOf(source.getId()));
                Intent intent = new Intent(mActivity, OthersDetailActivity.class);
                intent.putExtra("data",entity);
                startActivity(intent);
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MessegeMainEntity.DataEntity entity = dataSource.get(position);
                mSelectedPosition = position;


                if(dataSource.get(position).getIsFriend() !=null && dataSource.get(position).getIsFriend().equals("1")){
                    UserInfo user = new UserInfo();
                    user.setAvatar(dataSource.get(position).getAvatar());
                    long id = dataSource.get(position).getId();
                    user.setId(id);
                    user.setPhone(dataSource.get(position).getSubTitle());
                    user.setRealName(dataSource.get(position).getTitle());
                    user.setNickName(dataSource.get(position).getNickName());
                    UserInfoDaoHelper.getInstance().insertUserInfo(user);
                    RongIM.getInstance().startConversation(mActivity, Conversation.ConversationType.PRIVATE,String.valueOf(dataSource.get(position).getId()),dataSource.get(position).getNickName() == null?dataSource.get(position).getTitle():dataSource.get(position).getNickName());
                }else{
                    Intent intent;
                    CallListEntity.DataEntity data = new CallListEntity.DataEntity();
                    data.setName(entity.getNickName() == null ?entity.getTitle():entity.getNickName());
                    data.setAvatar(entity.getAvatar());
                    data.setId(entity.getId());
                    data.setPhone(entity.getSubTitle());
                    intent = new Intent(mActivity,PreviewCallActivity.class);
                    intent.putExtra("data",data);
                    startActivity(intent);
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

    @Override
    public void mainMessageCompleted(MainBusinessEntity data) {
        if(data.getCode().equals("ok")){
            MainBusinessEntity.DataEntity entity = data.getData();

            //置地时间
            if(!TextUtils.isEmpty(entity.getLastRwNoticeTime()))
                mZhidiDate.setText(DateUtils.getMessageMain(new Date(Long.parseLong(entity.getLastRwNoticeTime()))));
            else
                mZhidiDate.setText("");

            //公司时间
            if(!TextUtils.isEmpty(entity.getLastCoNoticeTime()))
                mGongsiDate.setText(DateUtils.getMessageMain(new Date(Long.parseLong(entity.getLastCoNoticeTime()))));
            else
                mGongsiDate.setText("");

            //商务电话
            if(!TextUtils.isEmpty(entity.getBiPhones().getSpeakTime())){
                mShangwuRecent.setVisibility(View.VISIBLE);
                mShangwuRecent.setText(DateUtils.getMessageMain(new Date(Long.parseLong(entity.getBiPhones().getSpeakTime()))));
            }
            else{
                mShangwuRecent.setVisibility(View.GONE);
                mShangwuRecent.setText("");
            }


            //商务电话最近
            if(TextUtils.isEmpty(entity.getBiPhones().getSpeakName()))
                mShangwuRight.setText("");
            else
                mShangwuRight.setText(getString(R.string.recentCallName)+entity.getBiPhones().getSpeakName());

            dataSource = dataTransfer(entity.getFavorites());
            openOrClose();
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

    private ArrayList<MessegeMainEntity.DataEntity> dataTransfer(ArrayList<MessageCommonEntity> source){

        if(source == null || source.size() == 0)
            return null;

        ArrayList<MessegeMainEntity.DataEntity> list = new ArrayList<>();

        for(int i = 0;i<source.size();i++){
            MessegeMainEntity.DataEntity entity = new MessegeMainEntity.DataEntity();
            MessageCommonEntity sourceEntity = source.get(i);
            entity.setTitle(sourceEntity.getRealname());
            entity.setNickName(sourceEntity.getNickname());
            entity.setSubTitle(sourceEntity.getPhone());
            entity.setType(1);
            entity.setAvatar(sourceEntity.getAvatar());
            entity.setIsFriend(sourceEntity.getIsFriend());
            entity.setFocus(sourceEntity.getFocus());
            entity.setId(Integer.parseInt(sourceEntity.getUserId()));
            entity.setFocus(sourceEntity.getFocus());
            entity.setSupportNum(sourceEntity.getSupportNum());
            entity.setCompany(sourceEntity.getCompany());
            list.add(entity);
        }
        return list;
    }

    @Override
    public void getMyFriendCompleted(MyFriendEntity data) {
        if(data.getCode().equals("ok")){
            //数据库缓存
            ArrayList<MessageCommonEntity> source = data.getData().getFriends();
            ArrayList<Friends> list = new ArrayList<>();
            for(int i = 0;i<source.size();i++){
                Friends friends = new Friends();
                MessageCommonEntity entity = source.get(i);
                friends.setId(Long.parseLong(entity.getUserId()));
                friends.setPhone(entity.getPhone());
                friends.setRealName(entity.getRealname());
                friends.setNickName(entity.getNickname());
                friends.setAvatar(entity.getAvatar());
                list.add(friends);
            }
            FriendsDaoHelper.getInstance().insertFriends(list);
        }
    }

    private void openOrClose(){
        ArrayList<MessegeMainEntity.DataEntity> ds = new ArrayList<>();
        if(isExpanded){
            ds = dataSource;
            mContainerChangYong.setBackgroundResource(R.drawable.shape_message_main_half_bac);
        }else{
            mContainerChangYong.setBackgroundResource(R.drawable.shape_message_main_bac);
        }
        mAdapter.setList(ds);
        mAdapter.notifyDataSetChanged();
    }
}
