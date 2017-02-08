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

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.message.ContactsActivity;
import com.home.rw.mvp.ui.activitys.message.MyTeamActivity;
import com.home.rw.mvp.ui.activitys.message.OriganizationActivity;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.home.rw.common.Const.TYPE_ADD;

public class LockMainActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    private SensorManager sensorManager;

    private Vibrator vibrator;

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
                                Toast.makeText(LockMainActivity.this,getString(R.string.doorOpened),Toast.LENGTH_SHORT).show();
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
            R.id.iv_temp_key


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
                Toast.makeText(LockMainActivity.this,getString(R.string.doorOpened),Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_temp_key:
                intent = new Intent(this,LockTempKeyActivity.class);
                startActivity(intent);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
