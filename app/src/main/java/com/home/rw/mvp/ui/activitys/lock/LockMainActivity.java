package com.home.rw.mvp.ui.activitys.lock;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.carduoblue.api.CarduoBlueOpen2Interface;
import com.carduoblue.api.CarduoBlueScanInterface;
import com.carduoblue.api.OpenCallback;
import com.carduoblue.api.ScanCallback;
import com.carduoblue.bean.BlueKey;
import com.home.rw.R;
import com.home.rw.listener.OnKeyClickListener;
import com.home.rw.mvp.entity.message.DoorKeyEntity;
import com.home.rw.mvp.presenter.impl.DoorKeyPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.message.ContactsActivity;
import com.home.rw.mvp.ui.activitys.message.MyTeamActivity;
import com.home.rw.mvp.ui.activitys.message.OriganizationActivity;
import com.home.rw.mvp.view.DoorKeyView;
import com.home.rw.utils.BtUtils;
import com.home.rw.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.home.rw.common.Const.TYPE_ADD;

public class LockMainActivity extends BaseActivity implements DoorKeyView{

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Inject
    DoorKeyPresenterImpl mDoorKeyPresenterImpl;

    private SensorManager sensorManager;

    private Vibrator vibrator;

    private ArrayList<DoorKeyEntity.Value.EmployeeKeysInfo> emInfo = new ArrayList<>();

    private ArrayList<String> nameList = new ArrayList<>();

    private List<BlueKey> keyList = new ArrayList<>();
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            Log.i("gravity", "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" + z);
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {

                Observable.create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        subscriber.onCompleted();
                    }
                }).observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<Object>() {
                            @Override
                            public void onCompleted() {
                                vibrator.vibrate(500);
                                mDoorKeyPresenterImpl.beforeRequest();
                                mDoorKeyPresenterImpl.getDoorKey();
                                //Toast.makeText(LockMainActivity.this,getString(R.string.doorOpened),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Object aLong) {

                            }
                        });

            }
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    @OnClick({R.id.back,
            R.id.rightText,
            R.id.iv_help,
            R.id.iv_shake,


    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rightText:
                break;
            case R.id.iv_help:
                intent = new Intent(this,LockHelpActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_shake:
                vibrator.vibrate(500);
                mDoorKeyPresenterImpl.beforeRequest();
                mDoorKeyPresenterImpl.getDoorKey();
                //Toast.makeText(LockMainActivity.this,getString(R.string.doorOpened),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lock_main;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            // 注册监听器
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            // 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }
    }
    @Override
    public void initViews() {
        midText.setText(getString(R.string.openDoor));
        rightText.setText(getString(R.string.incrementService));
        mBack.setImageResource(R.drawable.btn_back);
        mToolBar.setBackgroundResource(R.color.transparent);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mDoorKeyPresenterImpl.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        if(!BtUtils.isBluetoothEnabled()){
            BtUtils.turnOnBluetooth();
            Toast.makeText(this,getString(R.string.turnOnBt),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getDoorKeyCompleted(DoorKeyEntity data) {
        if(data.getCode() == 1){
            ArrayList<BlueKey> listToScan = new ArrayList<BlueKey>();
            emInfo = data.getValue().getEmployeeKeysInfo();
            for(int i = 0;i<data.getValue().getBlueKeys().size();i++){
                DoorKeyEntity.Value.BlueKeys blueKeys = data.getValue().getBlueKeys().get(i);
                listToScan.add(new BlueKey(blueKeys.getMac(),
                            blueKeys.getCompanyCode(),
                            blueKeys.getBluePwd(),
                            blueKeys.getEndTime(),
                            blueKeys.getCode(),
                            blueKeys.getRssiMin(),
                            blueKeys.getDeviceId()));
            }
            CarduoBlueScanInterface.scan(LockMainActivity.this, listToScan, new ScanCallback() {
                @Override
                public void onResult(List<BlueKey> listScanned) {
                    if(listScanned == null || listScanned.size()==0){
                        if(mLoadDialog != null){
                            mLoadDialog.dismiss();
                            mLoadDialog = null;
                        }
                        Toast.makeText(LockMainActivity.this,getString(R.string.doorNotFound),Toast.LENGTH_SHORT).show();
                    }else if(listScanned.size()>1){
                        if(mLoadDialog != null){
                            mLoadDialog.dismiss();
                            mLoadDialog = null;
                        }
                        showList(listScanned);
                    }else{
                        sendKey(listScanned.get(0));
                    }

                }
            });

        }else{
            if(mLoadDialog != null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }
        }
    }
    private void showList(List<BlueKey> listScanned){
        if(listScanned == null||
                emInfo == null||
                listScanned.size() == 0 ||
                emInfo.size() == 0)
            return;

        nameList = new ArrayList<>();
        keyList = listScanned;
        for(int i = 0;i<keyList.size();i++){
            int deviceId = Integer.parseInt(listScanned.get(i).getDeviceId());
            for(int j=0;j<emInfo.size();j++){
                DoorKeyEntity.Value.EmployeeKeysInfo info = emInfo.get(j);
                if(deviceId == info.getDeviceId()){
                    nameList.add(info.getName());
                    break;
                }
            }

        }
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this,DialogUtils.TYPE_KEY_LIST);
            mLoadDialog.show(nameList,new OnKeyClickListener(){
                @Override
                public void onKeyClick(int position) {
                    if(mLoadDialog!=null){
                        mLoadDialog.dismiss();
                        mLoadDialog = null;
                    }
                    if(position>=0)
                        sendKey(keyList.get(position));
                }
            });
        }

    }
    private void sendKey(BlueKey BlueKeyToOpen){
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this,DialogUtils.TYPE_DOOR_OPEN);
            mLoadDialog.show();
        }else{
            mLoadDialog.setText(R.string.opening);
        }
        CarduoBlueOpen2Interface.open(LockMainActivity.this, BlueKeyToOpen, new OpenCallback() {
            @Override
            public void onResult(int i) {
                if(mLoadDialog != null){
                    mLoadDialog.dismiss();
                    mLoadDialog = null;
                }

                showResult(i);
            }
        });
    }
    private void showResult(int resultCode) {
        String msg = "";
        switch (resultCode) {
            case 1:
                msg += getString(R.string.openDoorOk);
                break;
            case 3:
                msg += getString(R.string.DoorConnectFailed);
                break;
            case 4:
                msg += getString(R.string.DoorPswError);
                break;
            case 5:
                msg += getString(R.string.Doortimeout);
                break;
            default:
                break;
        }
        Toast.makeText(LockMainActivity.this,msg,Toast.LENGTH_SHORT);
    }
    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this,DialogUtils.TYPE_DOOR_SEARCH);
            mLoadDialog.show();
        }
    }

    @Override
    public void hideProgress(int reqType) {

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {

    }
}
