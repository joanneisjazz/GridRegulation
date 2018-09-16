package com.jstech.gridregulation

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapView
import com.jstech.gridregulation.activity.SiteInspectionObjectMapActivity
import com.jstech.gridregulation.db.DaoSession

class MainActivity : Activity() {

    lateinit var daoSession: DaoSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        startActivity(Intent(this, SiteInspectionObjectMapActivity::class.java))
        var app: MyApplication = application as MyApplication
        daoSession = app.session
        var userBeanDao = daoSession.userBeanDao
        var userBean = app.userBean
        userBeanDao.insertOrReplace(userBean)
        startActivity(Intent(this, SiteInspectionObjectMapActivity::class.java))

    }
}
