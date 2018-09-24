package com.jstech.gridregulation.adapter;

import android.content.Context;
import android.widget.TextView;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.base.BaseRecyclerAdapter;
import com.jstech.gridregulation.bean.CheckTableBean;
import com.jstech.gridregulation.bean.RegulateObjectBean;

import java.util.List;

/**
 * Created by hesm on 2018/9/24.
 */

public class SearchObjectAdapter extends BaseRecyclerAdapter<RegulateObjectBean> {
    public SearchObjectAdapter(List<RegulateObjectBean> mDatas, Context mContext, int layoutId) {
        super(mDatas, mContext, layoutId);
    }

    @Override
    protected void bindItemData(ViewHolder viewHolder, RegulateObjectBean data, int position) {

        TextView textView = viewHolder.getView(R.id.tv_object_name);
        textView.setText(data.getName());

    }
}
