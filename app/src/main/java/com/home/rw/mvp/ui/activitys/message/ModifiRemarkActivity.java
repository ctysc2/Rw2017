package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifiRemarkActivity extends BaseActivity {
    private String mName;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.et1)
    EditText mEt1;

    @BindView(R.id.iv_del)
    ImageView mDelete;

    @OnClick({R.id.back,
            R.id.iv_del,
            R.id.rightText
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rightText:
                Intent intent = new Intent();
                intent.putExtra("name",mEt1.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.iv_del:
                mEt1.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modifi_remark;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.modifiremark));
        rightText.setText(getString(R.string.completed));
        mback.setImageResource(R.drawable.btn_back);
        mName = getIntent().getStringExtra("name");
        mEt1.setText(mName);
        mEt1.setSelection(mEt1.length());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
