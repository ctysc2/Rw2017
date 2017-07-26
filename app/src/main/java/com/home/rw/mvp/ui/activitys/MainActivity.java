package com.home.rw.mvp.ui.activitys;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carduoblue.api.CarduoBlueInterface;
import com.carduoblue.api.InitCallback;
import com.home.rw.R;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.DialOutPresenterImpl;
import com.home.rw.mvp.presenter.impl.LoginPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.fragments.IncrementFragment;
import com.home.rw.mvp.ui.fragments.MessageFragment;
import com.home.rw.mvp.ui.fragments.MineMeFragment;
import com.home.rw.mvp.ui.fragments.SocialFragment;
import com.home.rw.mvp.ui.fragments.WorkFragment;
import com.home.rw.mvp.view.DialOutView;
import com.home.rw.mvp.view.LoginView;
import com.home.rw.utils.GoogleMapUtils;
import com.home.rw.utils.PreferenceUtils;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.callkit.OutGoingNotice;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements LoginView,DialOutView {

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

    private boolean isExit = false;
    @Inject
    LoginPresenterImpl mLoginPresenterImpl;

    @Inject
    DialOutPresenterImpl mDialOutPresenterImpl;
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
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        addFragment();
    }

    private String mToken1 = "QlbrYrD1RRRW0h/abjiy4W7NRrR+UMcreHvSS1S/dBvCeP5T+KlqPHvzIUlMuZpOsxoiCrcMVkoCjtDzK6xcOA==";
    private String mToken2 = "1t33Wpe6GiSmz+cyclErqiQPSAlBcInGYJyuRDuzYBQ/JthdPeQWkltPQljTJfkAs7rFFJpy7rscwdB/fTcMcw==";
    private String mToken3 = "RhfcRSV8g5ewQwKfrEuis27NRrR+UMcreHvSS1S/dBvCeP5T+KlqPLvP/2UHkd3l5sjai2rb5q0CjtDzK6xcOA==";
    private String mToken4 = "Naq42Ze8RkhN0wOi6wNuCSQPSAlBcInGYJyuRDuzYBQ/JthdPeQWkib8PGrfW4OaJgeeuC8FGnUcwdB/fTcMcw==";
    private String mToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        tabArray.add(mLlMessage);
        tabArray.add(mLlWork);
        tabArray.add(mLlIncrement);
        tabArray.add(mLlSocial);
        tabArray.add(mLlMine);
        initViews();
//         switch (PreferenceUtils.getPrefString(this,"userName","3")){
//             case "1":
//                 mToken = mToken1;
//                 break;
//             case "2":
//                 mToken = mToken2;
//                 break;
//             case "3":
//                 mToken = mToken3;
//                 break;
//             default:
//                 mToken = mToken3;
//                 break;
//         }
        GoogleMapUtils.getInstance().initGoogleMap(this);
        RongIM.connect(PreferenceUtils.getPrefString(this,"token",""), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.i("RongYun","onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                Log.i("RongYun", "onSuccess userId:" + s);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("RongYun","onError errorCode:"+errorCode.getMessage());
            }
        });
        CarduoBlueInterface.init(this, new InitCallback() {
            @Override
            public void onResult(Boolean result) {
                Log.e("Lock", "initResult = " + result);
            }
        });
        mLoginPresenterImpl.attachView(this);
        mDialOutPresenterImpl.attachView(this);
        mSubscription = RxBus.getInstance().toObservable(ReLoginEvent.class)
                .subscribe(new Action1<ReLoginEvent>() {
                    @Override
                    public void call(ReLoginEvent reLoginEvent) {

                        String userName = PreferenceUtils.getPrefString(MainActivity.this,"userName","");
                        String passWord = PreferenceUtils.getPrefString(MainActivity.this,"passWord","");
//                        if(!userName.equals("") &&
//                                !passWord.equals("")){
                            PreferenceUtils.setPrefString(MainActivity.this,"sessionID","");
                            mLoginPresenterImpl.processLogin("oa1_user1","1234");
                        //}

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().disconnect();
        if(mLoginPresenterImpl!=null)
            mLoginPresenterImpl.onDestroy();

        if(mDialOutPresenterImpl!=null)
            mDialOutPresenterImpl.onDestroy();

        EventBus.getDefault().unregister(this);
    }
    public void addFragment() {
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
                ((TextView)ll.getChildAt(1)).setTextColor(getResources().getColor(R.color.golden));
            }

        }

    }
    @Override
    public void loginCompleted(LoginEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(MainActivity.this,getString(R.string.reRoadSuccess),Toast.LENGTH_SHORT).show();
            long id = Long.parseLong(data.getData().getId());
            PreferenceUtils.setPrefLong(this,"ID",id);
            PreferenceUtils.setPrefString(this,"sessionID",data.getData().getSessionId());
            PreferenceUtils.setPrefString(this,"realname",data.getData().getRealname());
            PreferenceUtils.setPrefString(this,"avatar",data.getData().getAvatar());
            PreferenceUtils.setPrefString(this,"token",data.getData().getRongCloudToken());
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
        //Toast.makeText(MainActivity.this,getString(R.string.reRoadFailed),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dialOutCompleted(BaseEntity data) {

    }

    public void onEventMainThread(OutGoingNotice obj){
        Log.i("RongCloud","呼出通知后台");
        mDialOutPresenterImpl.dialOut(obj.getUserId());

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(isExit){
            finish();
        }else{
            isExit = true;
            Toast.makeText(this,getString(R.string.clickAgaintoExit),Toast.LENGTH_SHORT).show();
            Observable.timer(2, TimeUnit.SECONDS).compose(TransformUtils.<Object>defaultSchedulers())
            .subscribe(new Observer<Object>() {
                @Override
                public void onCompleted() {
                    isExit = false;
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Object data) {

                }

            });
        }

    }
}