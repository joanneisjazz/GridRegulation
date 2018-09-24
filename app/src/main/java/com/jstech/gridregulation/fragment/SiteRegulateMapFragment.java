package com.jstech.gridregulation.fragment;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jstech.gridregulation.ConstantValue;
import com.jstech.gridregulation.MyApplication;
import com.jstech.gridregulation.R;
import com.jstech.gridregulation.activity.CheckItemSelectActivity;
import com.jstech.gridregulation.activity.CheckTableSelect2Activity;
import com.jstech.gridregulation.activity.SearchRegulateObjectActivity;
import com.jstech.gridregulation.api.GetObjectApi;
import com.jstech.gridregulation.api.MyUrl;
import com.jstech.gridregulation.base.BaseFragment;
import com.jstech.gridregulation.bean.RegulateObjectBean;
import com.jstech.gridregulation.db.RegulateObjectBeanDao;
import com.jstech.gridregulation.utils.LogUtils;
import com.jstech.gridregulation.utils.SystemUtil;
import com.jstech.gridregulation.widget.MapBottomWindow;
import com.jstech.gridregulation.widget.MyPopupWindow;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class SiteRegulateMapFragment extends BaseFragment implements
        SensorEventListener, BaiduMap.OnMarkerClickListener, MapBottomWindow.TaskInterface, HttpOnNextListener {

    public static SiteRegulateMapFragment newInstance() {

        Bundle args = new Bundle();

        SiteRegulateMapFragment fragment = new SiteRegulateMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void LazyLoad() {

    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_site_regulate_map;
    }

    @BindView(R.id.layout_search)
    RelativeLayout layoutSearch;
    @BindView(R.id.mapview)
    MapView mMapView;

    BaiduMap mBaiduMap;

    public static MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

    LocationClient mLocClient;
    MyLocationListener myLocationListener = new MyLocationListener();
    private SensorManager sensorManager;
    private double lastX = 0.0;
    //112.787601,38.068948 测试的
    private double mCurrentLat = 37.760252;
    private double mCurrentLon = 112.48668;
    private int mCurrentDirection = 0;
    private float mCurrentAccracy;

    boolean isFisrtLoc = true;
    private MyLocationData mLocData;

    ArrayList<RegulateObjectBean> mRegulateObjectArrayList = new ArrayList<>();//监管对象list

    MapBottomWindow window;
    MyPopupWindow newTaskWindow;//是否开启新的检查的窗口
    HttpManager manager;
    GetObjectApi getObjectApi;

    boolean isSaveData = false;

    @Override
    public void initView() {
        setMap();
        initWindow();

        MyLocationData locData = new MyLocationData.Builder().accuracy(mCurrentAccracy).direction(mCurrentDirection)
                .latitude(mCurrentLat).longitude(mCurrentLon).build();
        mBaiduMap.setMyLocationData(locData);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true, null));
        mMapView.setLogoPosition(LogoPosition.logoPostionCenterBottom);
        manager = new HttpManager(this, (RxAppCompatActivity) getActivity());
        getObjectApi = new GetObjectApi();
        getObjectApi.setId("140100");
        manager.doHttpDeal(getObjectApi);
        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), SearchRegulateObjectActivity.class), 1);
            }
        });
    }

    private void setMap() {
        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);//获取传感器服务
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        //开启定位监听
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setScanSpan(100);
        option.setCoorType(ConstantValue.COOR_TYPE_BD0911);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mBaiduMap.setOnMarkerClickListener(this);
    }

    private void initWindow() {
        window = new MapBottomWindow.Builder().setContext(getActivity())
                .setOutSideCancel(true)
                .setFouse(true)
                .setwidth(SystemUtil.getWith(getActivity()))
                .setheight(SystemUtil.getHeight(getActivity()) * 2 / 7)
                .setListener(this)
                .builder();
        newTaskWindow = new MyPopupWindow.Builder().setContext(getActivity())
                .setContentView(R.layout.layout_new_task_window)
                .setOutSideCancel(true)
                .setFouse(true)
                .setwidth(SystemUtil.getWith(getActivity()) * 2 / 3)
                .setheight(SystemUtil.getHeight(getActivity()) * 2 / 5).builder();
    }

    //测试的监管对象
    private void addOverLay() {
//        LatLng llA = new LatLng(38.071902, 112.79026);//112.79026,38.071902
//        LatLng llB = new LatLng(38.067244, 112.792416);//112.792416,38.067244
//        LatLng llC = new LatLng(38.07338, 112.76712);//112.76712,38.07338
//        LatLng llD = new LatLng(38.065255, 112.781636);//112.781636,38.065255

        int markerIcon = -1;
        for (RegulateObjectBean o : mRegulateObjectArrayList) {
            LatLng ll = new LatLng(o.getLatitude(), o.getLongitude());
            if ("0".equals(o.getNature())) {//生产经营主体
                markerIcon = R.mipmap.ic_operating_entity_marker;
            } else {//农资门店
                markerIcon = R.mipmap.ic_farm_capital_store_marker;
            }
            MarkerOptions markerOption = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(markerIcon)).
                    zIndex(ConstantValue.Z_INDEX).animateType(MarkerOptions.MarkerAnimateType.drop);
            o.setMarker((Marker) (mBaiduMap.addOverlay(markerOption)));

        }
    }


    @Override
    public void onDestroy() {
        mLocClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        //为系统的方向传感器注册监听器
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        window.dismiss();
        newTaskWindow.dismiss();
        super.onPause();
    }

    //传感器
    @Override
    public void onSensorChanged(SensorEvent event) {

        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) < 1.0) {
            mCurrentDirection = (int) x;
            mLocData = new MyLocationData.Builder().accuracy(mCurrentAccracy).direction(mCurrentDirection)
                    .longitude(mCurrentLon).latitude(mCurrentLat).build();
            mBaiduMap.setMyLocationData(mLocData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (RegulateObjectBean o : mRegulateObjectArrayList) {
            if (marker == o.getMarker()) {
                showWindow(o);
            }
        }
        return true;
    }

    /**
     * 显示企业信息的弹框
     *
     * @param o
     */
    private void showWindow(RegulateObjectBean o) {
        String staus;
        window.setObj(o);
        window.getTvAddress().setText("详细地址：" + o.getAddress());
        window.getTvDetails().setText("检查次数：" + o.getInspcount() + " | " + "检查合格率：" + o.getPassrate());
        window.getTvDistance().setText("300m");
        window.getTvObjectName().setText(o.getName());
        window.getTvTel().setText("联系电话：" + o.getContactPhone());
        if (o.getStatus() == ConstantValue.OBJ_CHECK_STATUS_NEW) {
            staus = "开启新的检查";
        } else {
            staus = "继续检查";
        }
        window.getBtnCheck().setText(staus);

        window.showAtLocation(getLayoutResource(), Gravity.BOTTOM, 0, 0);
//                Toast.makeText(SiteInspectionObjectMapActivity.this, o.getAddress(), Toast.LENGTH_LONG).show();
    }


    /**
     * 跳转的方法
     * 如果是开启新的检查，跳转到选择检查表的页面
     * 如果是继续检查，跳转到判定检查结果的页面
     */
    @Override
    public void task(final RegulateObjectBean objectBean) {
        if (objectBean.getStatus() == ConstantValue.OBJ_CHECK_STATUS_NEW) {

            newTaskWindow.showAtLocation(getLayoutResource(), Gravity.CENTER, 0, 0);
            newTaskWindow.setPassButtonOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra(ConstantValue.KEY_OBJECT_ID, objectBean.getId());
                    intent.setClass(getActivity(), CheckTableSelect2Activity.class);
                    startActivity(intent);
                }
            });

        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), CheckItemSelectActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        LogUtils.d(resulte);
        LogUtils.d(method);
        JSONObject o = JSON.parseObject(resulte);
        String code = o.getString(ConstantValue.CODE);
        if (method.equals(MyUrl.GET_ENTERPRISE)) {
            if ("200".equals(code)) {
                //保存数据
                Observable.just(o.getJSONArray(ConstantValue.RESULT).toJavaList(RegulateObjectBean.class)).observeOn(Schedulers.newThread())
                        .map(new Func1<List<RegulateObjectBean>, List<RegulateObjectBean>>() {
                            @Override
                            public List<RegulateObjectBean> call(List<RegulateObjectBean> regulateObjectBeans) {
                                isSaveData = true;
                                //更新数据库
                                RegulateObjectBeanDao dao = MyApplication.getInstance().getSession().getRegulateObjectBeanDao();
                                for (RegulateObjectBean regulateObjectBean : regulateObjectBeans) {
                                    RegulateObjectBean exist = dao.loadByRowId(regulateObjectBean.getId());
                                    if (null != exist) {
                                        regulateObjectBean.setIsSearched(exist.getIsSearched());
                                        regulateObjectBean.setStatus(exist.getStatus());
                                        dao.update(regulateObjectBean);
                                    }
                                }
                                mRegulateObjectArrayList.clear();
                                mRegulateObjectArrayList.addAll(dao.queryBuilder().where(RegulateObjectBeanDao.Properties.UserId.eq(MyApplication.getInstance().getUserBean().getUserId())).list());
                                isSaveData = false;
                                return mRegulateObjectArrayList;
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<RegulateObjectBean>>() {
                            @Override
                            public void call(List<RegulateObjectBean> o) {

                                if (mRegulateObjectArrayList.size() == 0) {
                                    Toast.makeText(getActivity(), "未获取到企业信息，请重新获取数据", Toast.LENGTH_LONG).show();
                                }
                                addOverLay();
                            }
                        });
            }
        }

    }

    @Override
    public void onError(ApiException e, String method) {

        LogUtils.d(e.getMessage());
        Toast.makeText(getActivity(), "使用手机本地数据", Toast.LENGTH_LONG).show();
    }

    /**
     * 定位监听Listener
     */

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (null == mBaiduMap && null == bdLocation) {
                return;
            }
//            mCurrentLat = bdLocation.getLatitude();
//            mCurrentLon = bdLocation.getLongitude();
            mCurrentAccracy = bdLocation.getRadius();
            mLocData = new MyLocationData.Builder().
                    accuracy(mCurrentAccracy).
                    direction(mCurrentDirection).
                    latitude(mCurrentLat).
                    longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(mLocData);
            if (isFisrtLoc) {//如果是第一次定位
                isFisrtLoc = false;
//                LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                LatLng ll = new LatLng(mCurrentLat, mCurrentLon);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    String id = data.getStringExtra("id");
                    for (RegulateObjectBean bean : mRegulateObjectArrayList) {
                        if (bean.getId().equals(id)) {
                            showWindow(bean);
                        }
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
