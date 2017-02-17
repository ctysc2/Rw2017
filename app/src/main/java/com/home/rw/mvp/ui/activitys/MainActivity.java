package com.home.rw.mvp.ui.activitys;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.fragments.IncrementFragment;
import com.home.rw.mvp.ui.fragments.MessageFragment;
import com.home.rw.mvp.ui.fragments.MineMeFragment;
import com.home.rw.mvp.ui.fragments.SocialFragment;
import com.home.rw.mvp.ui.fragments.WorkFragment;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.GoogleMapUtils;
import com.home.rw.utils.PreferenceUtils;
import com.home.rw.utils.TransformUtils;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.functions.Func1;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ll_increment)
    LinearLayout mLlIncrement;

    @BindView(R.id.ll_message)
    LinearLayout mLlMessage;

    @BindView(R.id.ll_mine)
    LinearLayout mLlMine;

    @BindView(R.id.ll_social)
    LinearLayout mLlSocial;

    @BindView(R.id.ll_work)
    LinearLayout mLlWork;

    private List<Fragment> mFragmentList = new ArrayList<>();

    private ArrayList<View> tabArray = new ArrayList<>();

    private int[] selectedResouceArray = {R.drawable.tab_message_selected,
                                          R.drawable.tab_work_selected,
                                          R.drawable.tab_increment_selected,
                                          R.drawable.tab_social_selected,
                                          R.drawable.tab_mine_selected,
                                            };

    private int[] unSelectedResouceArray = {R.drawable.tab_message_unselected,
            R.drawable.tab_work_unselected,
            R.drawable.tab_increment_unselected,
            R.drawable.tab_social_unselected,
            R.drawable.tab_mine_unselected,
    };
    //消息fragment
    private MessageFragment mMessageFragment = new MessageFragment();

    //工作fragment
    private WorkFragment mWorkFragment = new WorkFragment();

    //增值fragment
    private IncrementFragment mIncrementFragment = new IncrementFragment();

    //社区fragment
    private SocialFragment mSocialFragment = new SocialFragment();

    //我的fragment
    private MineMeFragment mMineMeFragment = new MineMeFragment();

    //消息tab索引
    final int TAB_MESSAGE = 0;
    //工作tab索引
    final int TAB_WORK = 1;
    //增值tab索引
    final int TAB_INCREMENT = 2;
    //社区tab索引
    final int TAB_SOCIAL = 3;
    //我的tab索引
    final int TAB_MINE = 4;

    //当前选择的Fragment
    private int mCurrentIndex = -1;

    @OnClick({R.id.ll_message,R.id.ll_work,R.id.ll_increment,R.id.ll_social,R.id.ll_mine})
    public void OnClick(View v){
        int index = TAB_MESSAGE;
        switch (v.getId()){
            case R.id.ll_increment:
                index = TAB_INCREMENT;
                break;
            case R.id.ll_message:
                index = TAB_MESSAGE;
                break;
            case R.id.ll_work:
                index = TAB_WORK;
                break;
            case R.id.ll_social:
                index = TAB_SOCIAL;
                break;
            case R.id.ll_mine:
                index = TAB_MINE;
                break;
            default:
                break;

        }
        showFragment(index);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        addFragment();
    }

    private String mToken = "cKAsG5HC2cGlfLbazQzwPW7NRrR+UMcreHvSS1S/dBvCeP5T+KlqPOKuRv/L7PruKLoxFZuMe/sCjtDzK6xcOA==";
    private String mToken2 = "xG3zApLx/oT5Lj4xU2lSyARzB15IL/xC1Y0D/SKsLhU/6U1ceFneHGeTaQoFER00r2z8/5BLCrA=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabArray.add(mLlMessage);
        tabArray.add(mLlWork);
        tabArray.add(mLlIncrement);
        tabArray.add(mLlSocial);
        tabArray.add(mLlMine);
        initViews();

        GoogleMapUtils.getInstance().initGoogleMap(this);
        RongIM.connect(mToken2, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.i("RongYun","onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                Log.i("RongYun", "onSuccess userId:" + s);
                RongIM.getInstance().setCurrentUserInfo(findUserById(s));
                RongIM.getInstance().setMessageAttachedUserInfo(true);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("RongYun","onError errorCode:"+errorCode.getMessage());
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().disconnect();
    }

    private io.rong.imlib.model.UserInfo findUserById(String userId){
        UserInfo info = UserInfoDaoHelper.getInstance().getUserInfoById(Integer.parseInt(userId));
        io.rong.imlib.model.UserInfo userInfo = null;
//        io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(
//                String.valueOf(info.getId()),
//                info.getUserName(),
//                Uri.parse(info.getHeadUrl())
//                );
        if(userId.equals("1")){
            userInfo = new io.rong.imlib.model.UserInfo(
                    userId,
                    "陈无人",
                    Uri.parse("http://y0.ifengimg.com/e6ce10787c9a3bdb/2014/0423/re_53571adb03caf.jpg")
            );
            PreferenceUtils.setPrefString(this,"token",mToken);
        }else{
            userInfo = new io.rong.imlib.model.UserInfo(
                    userId,
                    "张19",
                    Uri.parse("http://img1.mp.oeeee.com/201702/03/04f372705ced49eb.jpg")
            );
            PreferenceUtils.setPrefString(this,"token",mToken2);
        }


        return userInfo;
    }
    private void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.fl_Content, mMessageFragment);
        transaction.add(R.id.fl_Content, mWorkFragment);
        transaction.add(R.id.fl_Content, mIncrementFragment);
        transaction.add(R.id.fl_Content, mSocialFragment);
        transaction.add(R.id.fl_Content, mMineMeFragment);
        transaction.commit();
        showFragment(TAB_MESSAGE);
    }

    private void hideFragment() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.hide(mMessageFragment);
        transaction.hide(mWorkFragment);
        transaction.hide(mIncrementFragment);
        transaction.hide(mSocialFragment);
        transaction.hide(mMineMeFragment);
        transaction.commit();
    }

    private void showFragment(final int tab) {

        //如果选择的和当前一致不处理
        if(mCurrentIndex == tab)
            return;

        hideFragment();

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        switch (tab) {
            case TAB_MESSAGE:
                transaction.show(mMessageFragment);
                break;
            case TAB_WORK:
                transaction.show(mWorkFragment);
                break;
            case TAB_INCREMENT:
                transaction.show(mIncrementFragment);
                break;
            case TAB_SOCIAL:
                transaction.show(mSocialFragment);
                break;
            case TAB_MINE:
                transaction.show(mMineMeFragment);
                break;
        }
        transaction.commit();
        changeTabBarSelect(tab);
        mCurrentIndex = tab;
    }


    private void changeTabBarSelect(int tab){
        for (int i = 0;i<tabArray.size();i++) {
            LinearLayout ll = (LinearLayout)tabArray.get(i);
            if(i != tab ){
                ((ImageView)ll.getChildAt(0)).setImageResource(unSelectedResouceArray[i]);
                ((TextView)ll.getChildAt(1)).setTextColor(getResources().getColor(R.color.text_color_7B858E));
            }else{
                ((ImageView)ll.getChildAt(0)).setImageResource(selectedResouceArray[i]);
                ((TextView)ll.getChildAt(1)).setTextColor(getResources().getColor(R.color.text_color_176BF9));
            }


        }



    }
}