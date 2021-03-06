package com.home.rw.mvp.ui.activitys.work;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.listener.OnAddressReceive;
import com.home.rw.mvp.entity.CardQueryEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.CardPresenterImpl;
import com.home.rw.mvp.presenter.impl.CardQueryPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.CardQueryView;
import com.home.rw.mvp.view.CardView;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.GoogleMapUtils;
import com.home.rw.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CardActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,CardView,CardQueryView {

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;

    private Location mLastLocation;

    private String mAddress;

    private int CheckNum = 0;

    private int mCardType = 0;
    @BindView(R.id.iv_header)
    SimpleDraweeView mHeader;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.bt_card1)
    Button mBt1;

    @BindView(R.id.bt_card2)
    Button mBt2;

    @BindView(R.id.ll_address1)
    LinearLayout mllAddress1;

    @BindView(R.id.ll_address2)
    LinearLayout mllAddress2;

    @BindView(R.id.tv_card_hint1)
    TextView mCard1;

    @BindView(R.id.tv_card_hint2)
    TextView mCard2;

    @BindView(R.id.tv_address1)
    TextView mTvadd1;

    @BindView(R.id.tv_address2)
    TextView mTvadd2;

    @BindView(R.id.iv1)
    ImageView mIv1;

    @BindView(R.id.iv2)
    ImageView mIv2;

    @BindView(R.id.tv_checked_time1)
    TextView mChecked1;

    @BindView(R.id.tv_checked_time2)
    TextView mChecked2;

    @BindView(R.id.tv_date1)
    TextView mDate1;

    @BindView(R.id.tv_date2)
    TextView mDate2;

    @BindView(R.id.tv_name)
    TextView mName;


    @Inject
    CardPresenterImpl mCardPresenterImpl;

    @Inject
    CardQueryPresenterImpl mCardQueryPresenterImpl;

    @OnClick({
            R.id.back,
            R.id.bt_card1,
            R.id.bt_card2,
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_card1:
                if(mLastLocation != null && mAddress!=null && !mAddress.equals("")){
                    mCardType = 0;
                    mCardPresenterImpl.beforeRequest();
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("type",mCardType);
                    map.put("longitude",String.valueOf(mLastLocation.getLongitude()));
                    map.put("latitude",String.valueOf(mLastLocation.getLatitude()));
                    map.put("remark",mAddress);
                    mCardPresenterImpl.card(map);
                }else{
                    Toast.makeText(this,getString(R.string.gettingLocation),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_card2:
                if(mLastLocation != null && mAddress!=null && !mAddress.equals("")){
                    mCardType = 1;
                    mCardPresenterImpl.beforeRequest();
                    HashMap<String,Object> map1 = new HashMap<>();
                    map1.put("type",mCardType);
                    map1.put("longitude",String.valueOf(mLastLocation.getLongitude()));
                    map1.put("latitude",String.valueOf(mLastLocation.getLatitude()));
                    map1.put("remark",mAddress);
                    mCardPresenterImpl.card(map1);
                    break;
                }else{
                    Toast.makeText(this,getString(R.string.gettingLocation),Toast.LENGTH_SHORT).show();
                }

            default:
                break;


        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_card;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.cardLabel);
        mName.setText(PreferenceUtils.getPrefString(this,"realname",""));
        mback.setImageResource(R.drawable.btn_back);
        mHeader.setImageURI(PreferenceUtils.getPrefString(this,"avatar",""));
        mCardPresenterImpl.attachView(this);
        mCardQueryPresenterImpl.attachView(this);
        mCardQueryPresenterImpl.setAddApplyType(HostType.CARD_QUERY);
        mCardQueryPresenterImpl.beforeRequest();
        mCardQueryPresenterImpl.cardQuery();
        mBt1.setEnabled(false);
        mBt2.setEnabled(false);
        mDate1.setText(DateUtils.getCardTime(new Date()));
        mDate2.setText(DateUtils.getCardTime(new Date()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initGoogleMap();
    }

    private void initGoogleMap() {
        //定位Setting初始化
        checkLocationSetting();

        //初始化GoogleApi平台
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.i("GoogleMap", "mGoogleApiClient create");
        }

    }

    //设置定位信息
    private void checkLocationSetting() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(100);
        mLocationRequest.setNumUpdates(10);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }
    protected void onStart() {
        Log.i("GoogleMap", "onStart");
        if((mGoogleApiClient !=null) && (!mGoogleApiClient.isConnected())){
            mGoogleApiClient.connect();
        }

        super.onStart();
    }

    protected void onStop() {
        if((mGoogleApiClient !=null) && mGoogleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();

            if((mSubscription !=null) && (!mSubscription.isUnsubscribed()))
                mSubscription.unsubscribe();

        }

        super.onStop();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("GoogleMap", "onConnected");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        //如果googleMap初始化完成并且成功获取定位
        //mgoogleMap异步初始化此时可能尚未完成
        if (mLastLocation != null) {
            Log.i("GoogleMap", "首次获得地址");

            //获取地址信息
            mSubscription = GoogleMapUtils.getInstance().getAddressByLocationAsyn(this,
                    new OnAddressReceive() {
                        @Override
                        public void sendAddress(String add) {
                            mAddress = add;
                            Log.i("GoogleMap","获取地址成功:"+mAddress);
                        }

                        @Override
                        public void error(String errorMsg) {
                            Log.i("GoogleMap","获取地址失败:"+errorMsg);
                        }
                    },mLastLocation);
        }

        //开启定位回调
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("GoogleMap","onConnectionSuspended:"+i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("GoogleMap","onConnectionFailed:"+connectionResult.getErrorMessage());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("GoogleMap","onLocationChanged");
        if ((location != null)) {
            mLastLocation = location;

            //获取地址信息
            mSubscription = GoogleMapUtils.getInstance().getAddressByLocationAsyn(this,
                    new OnAddressReceive() {
                        @Override
                        public void sendAddress(String add) {
                            mAddress = add;
                            Log.i("GoogleMap","获取地址成功:"+mAddress);



                        }

                        @Override
                        public void error(String errorMsg) {
                            Log.i("GoogleMap","获取地址失败:"+errorMsg);
                        }
                    },mLastLocation);

        }
    }

    @Override
    public void getCardQueryCompleted(CardQueryEntity data) {
        if(data!=null && data.getCode().equals("ok")){
            ArrayList<CardQueryEntity.DataEntity> list = data.getData();
            if(list!=null){
                if(list.size() == 0){
                    mBt1.setEnabled(true);
                    mBt2.setEnabled(false);
                }else if(list.size() == 1){
                    CardQueryEntity.DataEntity entity = list.get(0);
                    mllAddress1.setVisibility(View.VISIBLE);
                    mTvadd1.setText(entity.getRemark());
                    mIv1.setImageResource(R.drawable.icon_card_check);
                    mChecked1.setVisibility(View.VISIBLE);
                    if(entity.getSignInTime().split(" ").length>1)
                        mChecked1.setText(entity.getSignInTime().split(" ")[1]+getString(R.string.shangbanCard));
                    else
                        mChecked1.setText(entity.getSignInTime()+getString(R.string.shangbanCard));
                    mCard1.setVisibility(View.GONE);
                    mBt1.setText(getString(R.string.carded));
                    mBt2.setEnabled(true);
                }else if(list.size() == 2){
                    CardQueryEntity.DataEntity entity1 = list.get(0);
                    CardQueryEntity.DataEntity entity2 = list.get(1);
                    mllAddress1.setVisibility(View.VISIBLE);
                    mTvadd1.setText(entity1.getRemark());
                    mIv1.setImageResource(R.drawable.icon_card_check);
                    mChecked1.setVisibility(View.VISIBLE);
                    if(entity1.getSignInTime().split(" ").length>1)
                        mChecked1.setText(entity1.getSignInTime().split(" ")[1]+getString(R.string.shangbanCard));
                    else
                        mChecked1.setText(entity1.getSignInTime()+getString(R.string.shangbanCard));
                    mCard1.setVisibility(View.GONE);
                    mBt1.setText(getString(R.string.carded));


                    mllAddress2.setVisibility(View.VISIBLE);
                    mTvadd2.setText(entity2.getRemark());
                    mIv2.setImageResource(R.drawable.icon_card_check);
                    mChecked2.setVisibility(View.VISIBLE);
                    if(entity2.getSignInTime().split(" ").length>1)
                        mChecked2.setText(entity2.getSignInTime().split(" ")[1]+getString(R.string.xiabanCard));
                    else
                        mChecked2.setText(entity2.getSignInTime()+getString(R.string.xiabanCard));
                    mCard2.setVisibility(View.GONE);
                    mBt2.setText(getString(R.string.carded));


                }
            }


        }
    }

    @Override
    public void cardCompleted(BaseEntity data) {
        if(data!=null && data.getCode().equals("ok")){
            if(mCardType  == 0){
                mllAddress1.setVisibility(View.VISIBLE);
                mCard1.setVisibility(View.GONE);
                mIv1.setImageResource(R.drawable.icon_card_check);
                mChecked1.setVisibility(View.VISIBLE);
                mChecked1.setText(DateUtils.getTimeHHmmss(new Date())+getString(R.string.shangbanCard));
                mTvadd1.setText(mAddress);
                mBt1.setEnabled(false);
                mBt2.setEnabled(true);
            }else{
                mllAddress2.setVisibility(View.VISIBLE);
                mCard2.setVisibility(View.GONE);
                mIv2.setImageResource(R.drawable.icon_card_check);
                mChecked2.setVisibility(View.VISIBLE);
                mChecked2.setText(DateUtils.getTimeHHmmss(new Date())+getString(R.string.xiabanCard));
                mTvadd2.setText(mAddress);
                mBt2.setEnabled(false);
            }
        }
    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog == null){
            switch (reqType){
                case HostType.CARD:
                    mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_UPDATE);
                    mLoadDialog.show();
                    break;
                case HostType.CARD_QUERY:
                    mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_LOADING);
                    mLoadDialog.show();
                    break;
            }

        }
    }

    @Override
    public void hideProgress(int reqType) {
        if(mLoadDialog!=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        if(reqType == HostType.CARD){
            Toast.makeText(this,getString(R.string.cardInfoFailed),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,getString(R.string.cardFailed),Toast.LENGTH_SHORT).show();
        }

    }
}
