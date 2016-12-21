package com.home.rw.mvp.ui.activitys;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.home.rw.R;
import com.home.rw.listener.OnAddressReceive;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.GoogleMapUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import rx.Subscription;

public class SignInActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private MapFragment mMapFragment;

    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;

    private GoogleMap mgoogleMap;

    private String mAddress;

    private Subscription mSubscription;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initGoogleMap();
    }

    private LocationRequest mLocationRequest;

    //设置定位信息
    private void checkLocationSetting() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(100);
        mLocationRequest.setNumUpdates(3);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }
    //初始化GoogleMap
    private void initGoogleMap(){

        Log.i("GoogleMap", "initViews");

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


        mMapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mMapFragment.getMapAsync(this);



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
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap = googleMap;
        Log.i("GoogleMap", "onMapReady");
        mgoogleMap.setMyLocationEnabled(true);
        mgoogleMap.getUiSettings().setCompassEnabled(false);
        //首次显示迪拜
        LatLng myLoc = new LatLng(24.461780, 54.317147);
        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc,17));
    }

    @Override
    protected void onDestroy() {
        Log.i("GoogleMap", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("GoogleMap", "onConnected");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        //如果googleMap初始化完成并且成功获取定位
        //mgoogleMap异步初始化此时可能尚未完成
        if ((mLastLocation != null)&&(mgoogleMap != null)) {
            Log.i("GoogleMap", "首次获得地址");
            LatLng myLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc,17));

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

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("GoogleMap","onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("GoogleMap","onLocationChanged");
        if ((location != null)&&(mgoogleMap!=null)) {
            mLastLocation = location;
            LatLng myLoc = new LatLng(location.getLatitude(), location.getLongitude());

            mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc,17));

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
}
