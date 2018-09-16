package com.jstech.gridregulation;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.jstech.gridregulation.bean.UserBean;
import com.jstech.gridregulation.db.DaoMaster;
import com.jstech.gridregulation.db.DaoSession;

public class MyApplication extends Application {
    //服务器 url
    private static String url;
    private UserBean userBean;

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
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        initDb();
        initUserBean();
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
}
