package com.jstech.gridregulation.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jstech.gridregulation.ConstantValue;
import com.jstech.gridregulation.R;
import com.jstech.gridregulation.base.BaseRecyclerAdapter;
import com.jstech.gridregulation.bean.CheckItemBean;

import java.util.List;

public class CheckResultAdapter extends BaseRecyclerAdapter<CheckItemBean> {
    MethodInterface listener;

    public CheckResultAdapter(List<CheckItemBean> mDatas, Context mContext, int layoutId) {
        super(mDatas, mContext, layoutId);
    }

    public CheckResultAdapter(List<CheckItemBean> mDatas, Context mContext, int layoutId, MethodInterface listener) {
        super(mDatas, mContext, layoutId);
        this.listener = listener;
    }

    @Override
    protected void bindItemData(ViewHolder viewHolder, final CheckItemBean data, final int position) {
        TextView tvContent = viewHolder.getView(R.id.tv_content);
        TextView tvCheckMethod = viewHolder.getView(R.id.tv_method);
        RadioGroup rgResult = viewHolder.getView(R.id.rg_result);
        TextView tvTitle = viewHolder.getView(R.id.tv_title);

        tvContent.setText(data.getContent());
        rgResult.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_qualified:
                        data.setResult(ConstantValue.RESULT_QUALIFIED);
                        break;
                    case R.id.rbtn_basic_qualified:
                        data.setResult(ConstantValue.RESULT_BASIC_QUALIFIED);
                        break;
                    case R.id.rbtn_unqualified:
                        data.setResult(ConstantValue.RESULT_UNQUALIFIED);
                        break;
                }
            }
        });
        tvCheckMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.show(data.getId());
                }
            }
        });
    }

    public interface MethodInterface {
        void show(String id);
    }

}
