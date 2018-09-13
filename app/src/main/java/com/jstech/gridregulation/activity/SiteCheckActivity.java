package com.jstech.gridregulation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.adapter.CheckResultAdapter;
import com.jstech.gridregulation.base.BaseActivity;
import com.jstech.gridregulation.base.BaseRecyclerAdapter;
import com.jstech.gridregulation.bean.CheckItemBean;
import com.jstech.gridregulation.utils.SystemUtil;
import com.jstech.gridregulation.widget.MyPopupWindow;

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
        initPopupWindow();

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

    /**
     * 查看检查方法
     *
     * @param id
     */
    @Override
    public void showMethod(String id) {
        Intent intent = new Intent(this, CheckMethodActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    /**
     * 检查结果为不合格
     *
     * @param data
     * @param viewHolder
     */
    @Override
    public void showUnqualifiedReason(final CheckItemBean data, final BaseRecyclerAdapter.ViewHolder viewHolder) {
        data.setResult("3");
        final TextView tvReason = viewHolder.getView(R.id.tv_unqualified_reason);
        reasonWindow.showAtLocation(getLayoutId(), Gravity.CENTER, 0, 0);
        reasonWindow.getPassButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvReason.setVisibility(View.VISIBLE);
                String reason = data.getReason();
                if (null != reason && !"".equals(reason)) {
                    edtReason.setText(reason);
                }
                reasonWindow.setPassButtonOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setReason(edtReason.getText().toString());

                    }
                });
            }
        });
    }

    MyPopupWindow reasonWindow;
    EditText edtReason;

    private void initPopupWindow() {
        reasonWindow = new MyPopupWindow.Builder().setContext(this).
                setContentView(R.layout.layout_unqualified_reason_input).setTitle("请输入原因")
                .setwidth(SystemUtil.getWith(this) * 2 / 3)
                .setheight(SystemUtil.getHeight(this) / 2)
                .setFouse(true)
                .setAnimationStyle(R.style.Animation_CustomPopup)
                .setPass(getString(R.string.confrim))
                .setUnpass(getString(R.string.cancel))
                .setIsUnpassVisiable(false)
                .builder();
        edtReason = reasonWindow.getContentFrameLayout().findViewById(R.id.edit);
    }
}
