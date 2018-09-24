package com.jstech.gridregulation.fragment;

import android.os.Bundle;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.base.BaseFragment;

public class PersonalCenterFragment extends BaseFragment {

    public static PersonalCenterFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PersonalCenterFragment fragment = new PersonalCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void LazyLoad() {
        
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void initView() {

    }
}
