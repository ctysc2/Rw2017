package com.home.rw.mvp.ui.fragments.social;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.annotation.BindValues;
import com.home.rw.common.HostType;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.MixFocusEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.impl.MixFocusInteractorImpl;
import com.home.rw.mvp.presenter.impl.FocusPresenterImpl;
import com.home.rw.mvp.presenter.impl.MixFocusPresenterImpl;
import com.home.rw.mvp.presenter.impl.ZanPresenterImpl;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.activitys.social.CommListActivity;
import com.home.rw.mvp.ui.activitys.social.FocusListActivity;
import com.home.rw.mvp.ui.activitys.social.OthersDetailActivity;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.FacusListAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewDivider;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.mvp.view.FocusView;
import com.home.rw.mvp.view.MixFocusView;
import com.home.rw.mvp.view.ZanView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cty on 2017/1/5.
 */

public class FocusFragment extends BaseFragment implements MixFocusView,FocusView,ZanView{

    private ArrayList<FacusListEntity.DataEntity.ResLst> datasource1 = new ArrayList<>();
    private ArrayList<CommunicationEntity.DataEntity.ResLst> datasource2 = new ArrayList<>();
    private CommunicationAdapter mAdapterComm;
    private FacusListAdapter mAdapterFacus;

    private final int DETAIL = 1;

    private int requestPos = 0;
    private int focusPos = 0;
    private int zanPos = 0;
    @Inject
    Activity mActivity;

    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    @Inject
    MixFocusPresenterImpl mMixFocusPresenterImpl;

    @Inject
    FocusPresenterImpl mFocusPresenterImpl;

    @Inject
    ZanPresenterImpl mZanPresenterImpl;

    @Inject
    public FocusFragment(){

    }
    @OnClick({
            R.id.tv_all
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_all:
                startActivity(new Intent(mActivity, FocusListActivity.class));
            default:
                break;


        }

    }
    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        mMixFocusPresenterImpl.attachView(this);
        mFocusPresenterImpl.attachView(this);
        mZanPresenterImpl.attachView(this);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mMixFocusPresenterImpl.getMixFocus();
            }
        });

        //内容列表
        mAdapterComm = new CommunicationAdapter(datasource2,mActivity);

        mAdapterComm.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity,CommDetailActivity.class);
                intent.putExtra("commData",datasource2.get(position));
                startActivityForResult(intent,DETAIL);
                requestPos = position;
            }
        });
        mAdapterComm.setOnItemFocusClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                focusPos = position;
                mFocusPresenterImpl.setReqType(HostType.FOCUS);
                mFocusPresenterImpl.doFocus(datasource2.get(position).getCreatedBy());

            }
        });
        mAdapterComm.setOnItemZanClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                zanPos = position;
                mZanPresenterImpl.zan(datasource2.get(position).getId());

            }
        });

        mRvContent.setHasFixedSize(true);
        mRvContent.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRvContent.addItemDecoration(new RecycleViewSperate());
        mRvContent.setItemAnimator(new DefaultItemAnimator());
        mRvContent.setAdapter(mAdapterComm);
        mRvContent.setNestedScrollingEnabled(false);


        //关注的人列表
        mAdapterFacus = new FacusListAdapter(datasource1,mActivity);

        mAdapterFacus.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity,OthersDetailActivity.class);
                intent.putExtra("data",datasource1.get(position));
                startActivity(intent);
            }
        });
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRvList.addItemDecoration(new RecycleViewDivider(mActivity,RecycleViewDivider.VERTICAL_LIST,RecycleViewDivider.COMMON));
        mRvList.setItemAnimator(new DefaultItemAnimator());
        mRvList.setAdapter(mAdapterFacus);
        mRvList.setNestedScrollingEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMixFocusPresenterImpl.beforeRequest();
        mMixFocusPresenterImpl.getMixFocus();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_focus;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case DETAIL:
                if(resultCode == RESULT_OK){
                    CommunicationEntity.DataEntity.ResLst entity = (CommunicationEntity.DataEntity.ResLst)data.getSerializableExtra("newData");
                    datasource2.set(requestPos,entity);
                    mAdapterComm.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getMixFocusCompleted(MixFocusEntity data) {
        if(data.getCode().equals("ok")){
            datasource1 = data.getData().getFocusLst();
            datasource2 = data.getData().getTopicLst();
            mAdapterFacus.setList(datasource1);
            mAdapterComm.setList(datasource2);
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
        Toast.makeText(mActivity,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doFocusCompleted(BaseEntity data) {
        if(data.equals("ok")){
            datasource2.get(focusPos).setFocus("1");
            mAdapterComm.notifyDataSetChanged();
            Toast.makeText(mActivity,getString(R.string.focusSucceed),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMixFocusPresenterImpl !=null)
            mMixFocusPresenterImpl.onDestroy();

        if(mFocusPresenterImpl !=null)
            mFocusPresenterImpl.onDestroy();

        if(mZanPresenterImpl != null){
            mZanPresenterImpl.onDestroy();
        }
    }

    @Override
    public void zanCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            datasource2.get(zanPos).setSupport("1");
            int supportNum = Integer.parseInt(datasource2.get(zanPos).getSupportNum());
            String newSupport = String.valueOf(supportNum+1);
            datasource2.get(zanPos).setSupport("1");
            datasource2.get(zanPos).setSupportNum(newSupport);
            mAdapterComm.notifyDataSetChanged();

        }
    }
}
