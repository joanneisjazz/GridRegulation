package com.jstech.gridregulation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.adapter.CheckTableSelectAdapter;
import com.jstech.gridregulation.base.BaseActivity;
import com.jstech.gridregulation.bean.CheckTableBean;
import com.jstech.gridregulation.utils.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 选择检查表
 */
public class CheckTableSelectActivity extends BaseActivity implements CheckTableSelectAdapter.SelectInterface, View.OnClickListener {

    @BindView(R.id.recyclerview_table)
    RecyclerView rvTable;
    @BindView(R.id.ckb_all_select)
    CheckBox ckbAllSelect;
    @BindView(R.id.btn_save)
    Button btnSave;

    ArrayList<CheckTableBean> mCheckTableBeanList = new ArrayList<>();
    ArrayList<CheckTableBean> mSelectedList = new ArrayList<>();//选中的检查表

    CheckTableSelectAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_table_select;
    }

    @Override
    public void initView() {
        initList();
        LogUtils.d("" + mCheckTableBeanList.size());
        mAdapter = new CheckTableSelectAdapter(mCheckTableBeanList, this, R.layout.item_check_table_select, this);
        rvTable.setLayoutManager(new LinearLayoutManager(this));
        rvTable.setAdapter(mAdapter);
        ckbAllSelect.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    /**
     * 检查表单项选择
     *
     * @param position 检查表的位置
     * @param
     */
    @Override
    public void select(int position) {
        if (mCheckTableBeanList.get(position).isSelected()) {
            mCheckTableBeanList.get(position).setSelected(false);
        } else {
            mCheckTableBeanList.get(position).setSelected(true);
        }
        int isExist = mSelectedList.indexOf(mCheckTableBeanList.get(position));
        //如果该检查表已经在选中列表里，需要在选中列表中删除该检查表
        if (isExist == -1) {
            mSelectedList.add(mCheckTableBeanList.get(position));
        } else {
            mSelectedList.remove(mCheckTableBeanList.get(position));
        }
//        mSelectedList.equals(mCheckTableBeanList)
        if (isAllSelected()) {
            ckbAllSelect.setChecked(true);
        } else {
            ckbAllSelect.setChecked(false);
        }
        LogUtils.d(mSelectedList.size() + "");
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
                Intent intent = new Intent(this, CheckItemSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mSelectedList);
                intent.putExtras(bundle);
                startActivity(intent);
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
            mSelectedList.clear();
            ckbAllSelect.setChecked(false);
        } else {
            for (CheckTableBean b : mCheckTableBeanList) {
                b.setSelected(true);
            }
            mSelectedList.clear();
            mSelectedList.addAll(mCheckTableBeanList);
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
            mCheckTableBeanList.add(b);
        }
    }
}
