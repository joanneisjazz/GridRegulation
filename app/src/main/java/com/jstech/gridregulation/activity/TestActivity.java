package com.jstech.gridregulation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jstech.gridregulation.MyApplication;

public class TestActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication application = (MyApplication) getApplication();
    }
}
