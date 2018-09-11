package com.jstech.gridregulation.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ToggleButton;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.adapter.CheckTableSelectAdapter;
import com.jstech.gridregulation.base.BaseActivity;
import com.jstech.gridregulation.bean.CheckTableBean;

import java.util.ArrayList;

import butterknife.BindView;

public class CheckTableSelectActivity extends BaseActivity implements CheckTableSelectAdapter.SelectInterface, View.OnClickListener {

    @BindView(R.id.recyclerview_table)
    RecyclerView rvTable;
    @BindView(R.id.ckb_all_select)
    CheckBox ckbAllSelect;
    @BindView(R.id.btn_save)
    Button btnSave;

    ArrayList<CheckTableBean> mCheckTableBeanList = new ArrayList<>();
    CheckTableSelectAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_table_select;
    }

    @Override
    public void initView() {
        initList();
        mAdapter = new CheckTableSelectAdapter(mCheckTableBeanList, this, R.layout.item_check_table_select, this);
        rvTable.setLayoutManager(new LinearLayoutManager(this));
        rvTable.setAdapter(mAdapter);
        ckbAllSelect.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    /**
     * 检查表单项选择
     *
     * @param position   检查表的位置
     * @param isSelected 检查表是否选中
     */
    @Override
    public void select(int position, boolean isSelected) {
        mCheckTableBeanList.get(position).setSelected(isSelected);
        if (isAllSelected()) {
            ckbAllSelect.setChecked(true);
        } else {
            ckbAllSelect.setChecked(false);
        }
        mAdapter.notifyDataSetChanged();

    }

    /**
     * 判断是否全部选中
     *
     * @return
     */
    private boolean isAllSelected() {
        for (CheckTableBean b : mCheckTableBeanList) {
            if (!b.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //保存
            case R.id.btn_save:
                break;
            //全部选中
            case R.id.ckb_all_select:
                selectAll();
                break;
            default:
                break;
        }
    }

    private void selectAll() {
        if (isAllSelected()) {
            //如果已经宣布选中，再点击时应该全部取消
            for (CheckTableBean b : mCheckTableBeanList) {
                b.setSelected(false);
            }
            ckbAllSelect.setChecked(false);
        } else {
            for (CheckTableBean b : mCheckTableBeanList) {
                b.setSelected(true);
            }
            ckbAllSelect.setChecked(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initList() {
        for (int i = 0; i < 6; i++) {
            CheckTableBean b = new CheckTableBean();
            b.setSelected(false);
            b.setId(i + "");
            b.setTableName("检查表" + i);
        }
    }
}
