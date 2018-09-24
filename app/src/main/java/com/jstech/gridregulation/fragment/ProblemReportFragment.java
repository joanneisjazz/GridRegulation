package com.jstech.gridregulation.fragment;

import android.os.Bundle;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.base.BaseFragment;

public class ProblemReportFragment extends BaseFragment {
    public static ProblemReportFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProblemReportFragment fragment = new ProblemReportFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void LazyLoad() {
        
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_problem_report;
    }

    @Override
    public void initView() {

    }
}
