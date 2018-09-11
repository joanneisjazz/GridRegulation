package com.jstech.gridregulation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.adapter.CheckItemSelectAdapter;
import com.jstech.gridregulation.base.BaseActivity;
import com.jstech.gridregulation.bean.CheckItemBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 选择检查项目
 */
public class CheckItemSelectActivity extends BaseActivity implements
        CheckItemSelectAdapter.SelectInterface, View.OnClickListener {

    @BindView(R.id.recyclerview_table)
    RecyclerView rvTable;
    @BindView(R.id.ckb_all_select)
    CheckBox ckbAllSelect;
    @BindView(R.id.btn_save)
    Button btnSave;

    ArrayList<CheckItemBean> mCheckItemBeanList = new ArrayList<>();
    ArrayList<CheckItemBean> mSelectedList = new ArrayList<>();

    CheckItemSelectAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        /**
         * 跟选择检查表用同一个页面
         */
        return R.layout.activity_check_table_select;
    }

    @Override
    public void initView() {
        initList();
        mAdapter = new CheckItemSelectAdapter(mCheckItemBeanList, this, R.layout.item_check_table_select, this);
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
        if (mCheckItemBeanList.get(position).isSelected()) {
            mCheckItemBeanList.get(position).setSelected(false);
        } else {
            mCheckItemBeanList.get(position).setSelected(true);
        }
        int isExist = mSelectedList.indexOf(mCheckItemBeanList.get(position));
        //如果该检查项已经在选中列表里，需要在选中列表中删除该检查表
        if (isExist == -1) {
            mSelectedList.add(mCheckItemBeanList.get(position));
        } else {
            mSelectedList.remove(mCheckItemBeanList.get(position));
        }
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
        for (CheckItemBean b : mCheckItemBeanList) {
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
                if (mSelectedList.size() == 0) {
                    Toast.makeText(this, "请选择检查项目", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(this, SiteCheckActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", mSelectedList);
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
            for (CheckItemBean b : mCheckItemBeanList) {
                b.setSelected(false);
            }
            mSelectedList.clear();
            ckbAllSelect.setChecked(false);
        } else {
            for (CheckItemBean b : mCheckItemBeanList) {
                b.setSelected(true);
            }
            mSelectedList.clear();
            mSelectedList.addAll(mCheckItemBeanList);
            ckbAllSelect.setChecked(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initList() {
        for (int i = 0; i < 9; i++) {
            CheckItemBean b = new CheckItemBean();
            b.setSelected(false);
            b.setId(i + "");
            b.setContent("检查项目" + i);
            mCheckItemBeanList.add(b);
        }
    }
}
