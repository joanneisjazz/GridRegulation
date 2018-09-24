package com.jstech.gridregulation;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.jstech.gridregulation.bean.UserBean;
import com.jstech.gridregulation.db.DaoMaster;
import com.jstech.gridregulation.db.DaoSession;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyApplication extends Application {
    //服务器 url
    private static String url;
    private UserBean userBean;
    public static MyApplication instance;

    public static MyApplication getInstance(){
        return instance;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        MyApplication.url = url;
    }

    private DaoSession session;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        initDb();
        initUserBean();
        CrashReport.initCrashReport(getApplicationContext(), "3b9dd2596f", false);
    }

    private void initDb() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "grid_db");
        DaoMaster master = new DaoMaster(helper.getWritableDb());
        session = master.newSession();
    }

    public DaoSession getSession() {
        return session;
    }

    private void initUserBean() {
        userBean = new UserBean();
        userBean.setUserId("241671aee7cc4ead88b2532b7686183c");
        userBean.setUserName("测试");
        userBean.setEntregion("140110001");
    }

    private void initBugly() {
        CrashReport.initCrashReport(this, "注册时申请的APPID", true);
    }

}
