package com.home.rw.mvp.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.increment.MeetingAppointmentListActivity;
import com.home.rw.mvp.ui.activitys.increment.TempActivity;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cty on 2016/12/13.
 */

public class IncrementFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @Inject
    Activity mActivity;

    @OnClick({R.id.rl_1,
            R.id.rl_baoxiu,
            R.id.ll_print,
            R.id.consumeMateria,
            R.id.clear,
            R.id.express,
            R.id.rentcar,
            R.id.writeMateria,
            R.id.incrementAdd,


    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.rl_1:
                intent = new Intent(mActivity, MeetingAppointmentListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_baoxiu:
            case R.id.ll_print:
            case R.id.consumeMateria:
            case R.id.clear:
            case R.id.express:
            case R.id.rentcar:
            case R.id.writeMateria:
            case R.id.incrementAdd:
                intent = new Intent(mActivity, TempActivity.class);
                startActivity(intent);
                break;
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
        midText.setText(R.string.incrementService);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_increment;
    }
}
