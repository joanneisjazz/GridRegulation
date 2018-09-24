package com.jstech.gridregulation.activity;

import android.widget.TextView;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.base.BaseActivity;

import butterknife.BindView;

/**
 * 查看检查方法
 */
public class CheckMethodActivity extends BaseActivity {

    @BindView(R.id.tv_method)
    TextView tvMethod;

    String strMethod;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_method;
    }

    @Override
    public void initView() {
        setToolSubBarTitle("");
        strMethod = getIntent().getStringExtra("method");
        tvMethod.setText(strMethod);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
