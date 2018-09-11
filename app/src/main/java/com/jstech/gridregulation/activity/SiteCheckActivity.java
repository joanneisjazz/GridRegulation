package com.jstech.gridregulation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.adapter.CheckResultAdapter;
import com.jstech.gridregulation.base.BaseActivity;
import com.jstech.gridregulation.bean.CheckItemBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 选完检查项目后开始进行检查
 */
public class SiteCheckActivity extends BaseActivity implements CheckResultAdapter.MethodInterface {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_subtitle)
    TextView tvNext;

    CheckResultAdapter mResultAdapter;
    ArrayList<CheckItemBean> mItemList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_site_check;
    }

    @Override
    public void initView() {
        tvNext.setText(R.string.next);
        tvNext.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        mItemList = (ArrayList<CheckItemBean>) bundle.getSerializable("list");
        if (null == mItemList) {
            mItemList = new ArrayList<>();
        }
        mResultAdapter = new CheckResultAdapter(mItemList, this, R.layout.item_site_check, this);
        recyclerView.setAdapter(mResultAdapter);
    }

    @Override
    public void show(String id) {

    }

    /**
     * 检查是否所有的项目都已经有结果
     */
    private boolean isAllChecked() {
        for (CheckItemBean b : mItemList) {
            if (null == b.getResult() || "".equals(b.getResult())) {
                return false;
            }
        }
        return true;
    }

}
