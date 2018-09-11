package com.jstech.gridregulation

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapView
import com.jstech.gridregulation.activity.SiteInspectionObjectMapActivity

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, SiteInspectionObjectMapActivity::class.java))
    }
}
