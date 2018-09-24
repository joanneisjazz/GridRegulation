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
    protected void bindItemData(final ViewHolder viewHolder, final CheckItemBean data, final int position) {
        TextView tvContent = viewHolder.getView(R.id.tv_content);
        TextView tvCheckMethod = viewHolder.getView(R.id.tv_method);
        TextView tvTitle = viewHolder.getView(R.id.tv_title);
        final TextView tvQualified = viewHolder.getView(R.id.tv_qualified);
        final TextView tvBasicQualified = viewHolder.getView(R.id.tv_basic_qualified);
        final TextView tvUnqualified = viewHolder.getView(R.id.tv_unqualified);
        final TextView tvUnqualifiedReason = viewHolder.getView(R.id.tv_unqualified_reason);

        tvContent.setText(data.getContent());

        //检查方法
        tvCheckMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.showMethod(data.getMethod());
                }
            }
        });

        //检查结果--合格
        tvQualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectStyle(tvQualified, tvBasicQualified, tvUnqualified);
                tvUnqualifiedReason.setVisibility(View.GONE);
                listener.selectResult(0, data, viewHolder);

            }
        });

        //检查结果--基本合格
        tvBasicQualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectStyle(tvBasicQualified, tvQualified, tvUnqualified);
                tvUnqualifiedReason.setVisibility(View.GONE);
                listener.selectResult(1, data, viewHolder);

            }
        });

        //检查结果--不合格
        tvUnqualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setResult("3");
                setSelectStyle(tvUnqualified, tvQualified, tvBasicQualified);
//                tvUnqualifiedReason.setVisibility(View.VISIBLE);
                listener.selectResult(2, data, viewHolder);
            }
        });
    }

    public interface MethodInterface {
        void showMethod(String id);

        void selectResult(int result, CheckItemBean data, ViewHolder viewHolder);
    }

    /**
     * 设置选中和未选中的样式
     */
    private void setSelectStyle(TextView tvSeleted, TextView tvUnSeleted1, TextView tvSeleted2) {
//        tvSeleted.setTextColor(mContext.getResources().getColor(R.color.white));
//        tvSeleted.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        tvSeleted.setBackground(mContext.getResources().getDrawable(R.drawable.bg_check_item_result_selected));
        tvUnSeleted1.setBackground(mContext.getResources().getDrawable(R.drawable.bg_check_item_result_unselect));
        tvSeleted2.setBackground(mContext.getResources().getDrawable(R.drawable.bg_check_item_result_unselect));

//        tvUnSeleted1.setTextColor(mContext.getResources().getColor(R.color.appTextGray));
//        tvUnSeleted1.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//        tvSeleted2.setTextColor(mContext.getResources().getColor(R.color.appTextGray));
//        tvSeleted2.setBackgroundColor(mContext.getResources().getColor(R.color.white));
    }
}
